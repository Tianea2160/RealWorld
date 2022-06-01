package com.study.realworld.domain.article.service;

import com.study.realworld.domain.TimeExtend;
import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.article.controller.ArticleSearch;
import com.study.realworld.domain.article.dto.ArticleUpdateDto;
import com.study.realworld.domain.article.repository.ArticleRepository;
import com.study.realworld.domain.article.repository.ArticleRepositoryImpl;
import com.study.realworld.domain.articletag.ArticleTag;
import com.study.realworld.domain.articletag.repository.ArticleTagRepository;
import com.study.realworld.domain.like.Like;
import com.study.realworld.domain.like.repository.LikeRepository;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.repository.MemberRepository;
import com.study.realworld.domain.article.dto.ArticleCreateDto;
import com.study.realworld.domain.article.dto.ArticleDto;
import com.study.realworld.domain.tag.Tag;
import com.study.realworld.domain.tag.repository.TagRepository;
import com.study.realworld.global.exception.NotExistArticleException;
import com.study.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public List<ArticleDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .stream()
                .map(ArticleDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> findFeeds(Member loginMember) {
        Member login = memberRepository.findMemberByName(loginMember.getName())
                .orElseThrow(() -> new NotExistMemberException("해당 사용자는 존재하지 않습니다."));
        // 팔로워들의 피드를 전부 가지고 와야하기 때문에
        // 시간 순으로 정렬해서 10개의 데이터만 가지고 온다.
        return login.getFollowers().stream()
                .flatMap(member -> member.getArticles().stream())
                .sorted(Comparator.comparing(TimeExtend::getCreatedAt))
                .limit(10)
                .map(article -> {
                    ArticleDto dto = ArticleDto.of(article);
                    // 팔로우한 사용자의 게시글을 가져온것->무조건 true
                    dto.getAuthor().setFollowing(true);
                    // 로그인한 사용자가 좋아요를 눌렀는가
                    log.info(article.toString());
                    boolean isFavorite = likeRepository.existsLikeByArticleAndMember(article, login);
                    dto.setFavorited(isFavorite);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleDto findBySlug(String slug, String loginMemberName) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));
        Member login = memberRepository.findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자가 없습니다."));

        ArticleDto dto = ArticleDto.of(article);

        boolean isFavorite = likeRepository.existsLikeByArticleAndMember(article, login);
        dto.setFavorited(isFavorite);
        return dto;
    }

    public Long create(String loginMemberName, @RequestBody ArticleCreateDto createDto) {
        Member login = memberRepository.findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자가 없습니다."));

        Article article = ArticleCreateDto.from(createDto);
        article.setMember(login);

        Long articleId = articleRepository.create(article);

        //tag 지정
        for (String tagName : createDto.getTagList()) {
            log.info("tag : " + tagName);
            Tag savedTag = tagRepository
                    .findTagByContent(tagName)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName)));

            ArticleTag articleTag = ArticleTag.create(article, savedTag);
            articleTagRepository.save(articleTag);
            article.getArticleTags().add(articleTag);
        }
        return articleId;
    }

    public Long update(Long loginId, String slug, ArticleUpdateDto dto) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));

        if (!article.getMember().getId().equals(loginId))
            throw new IllegalStateException("해당 게시글에 대한 수정 권한이 없습니다.");

        article.update(dto.getTitle(), article.getBody(), article.getDescription());
        return article.getId();
    }

    public void delete(Long loginId, String slug) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));
        if (!article.getMember().getId().equals(loginId))
            throw new IllegalStateException("해당 게시글에 대한 수정 권한이 없습니다.");

        articleRepository.delete(article);
    }

    public void favorite(String slug, String loginMemberName) {
        Member login = memberRepository.findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자가 없습니다."));
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));

        boolean isPresent = likeRepository
                .findLikeByArticleAndMember(article, login).isPresent();
        if (isPresent) return;// 이미 좋아요 상태라면 종료
        Like like = new Like(article, login);
        likeRepository.save(like);
        article.getLikes().add(like);
    }

    public void unfavorite(String slug, String loginMemberName) {
        Member login = memberRepository.findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자가 없습니다."));
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));

        likeRepository.findLikeByArticleAndMember(article, login).ifPresent((like) -> {
            article.getLikes().remove(like);
            likeRepository.delete(like);
        });
    }
}
