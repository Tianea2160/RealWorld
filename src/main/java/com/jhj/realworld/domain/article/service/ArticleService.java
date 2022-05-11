package com.jhj.realworld.domain.article.service;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.controller.ArticleSearch;
import com.jhj.realworld.domain.article.dto.ArticleUpdateDto;
import com.jhj.realworld.domain.article.repository.ArticleRepository;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.repository.MemberRepository;
import com.jhj.realworld.domain.article.dto.ArticleCreateDto;
import com.jhj.realworld.domain.article.dto.ArticleDto;
import com.jhj.realworld.global.exception.NotExistArticleException;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ArticleDto> findAll(ArticleSearch articleSearch) {
        return articleRepository.findAll(articleSearch)
                .stream()
                .map(ArticleDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> findFeeds(Member loginMember) {
        Member login = memberRepository.findMemberByName(loginMember.getName())
                .orElseThrow(() -> new NotExistMemberException("해당 사용자는 존재하지 않습니다."));
        // 팔로워들의 피드를 전부 가지고 와야하기 때문에
        return login.getFollowers().stream()
                .flatMap(member -> member.getArticles().stream())
                .map(ArticleDto::of)
                .collect(Collectors.toList());
    }

    public ArticleDto findBySlug(String slug) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));
        return ArticleDto.of(article);
    }

    public Long create(Long loginId, ArticleCreateDto createDto) {
        Member login = memberRepository
                .findMemberById(loginId)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자가 없습니다."));

        Article article = ArticleCreateDto.from(createDto);
        article.setMember(login);
        return articleRepository.create(article);
    }

    public Long update(Long loginId, String slug, ArticleUpdateDto updateDto) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));

        if (!article.getMember().getId().equals(loginId))
            throw new IllegalStateException("해당 게시글에 대한 수정 권한이 없습니다.");

            article.update(updateDto);
        return article.getId();
    }

    public void delete(Long loginId, String slug) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당 게시글은 없습니다."));
        if (!article.getMember().getId().equals(loginId))
            throw new IllegalStateException("해당 게시글에 대한 수정 권한이 없습니다.");

        articleRepository.delete(article);
    }
}
