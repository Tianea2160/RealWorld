package com.jhj.realworld.domain.comment.service;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.repository.ArticleRepository;
import com.jhj.realworld.domain.comment.Comment;
import com.jhj.realworld.domain.comment.dto.CommentCreateDto;
import com.jhj.realworld.domain.comment.repository.CommentRepository;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.repository.MemberRepository;
import com.jhj.realworld.global.exception.NotExistArticleException;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public void create(CommentCreateDto dto, String slug, String loginMemberName) {
        Member login = memberRepository
                .findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당하는 사용자가 없습니다."));

        Article article = articleRepository
                .findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당하는 게시글이 없습니다."));

        Comment comment = Comment.from(dto, login, article);
        commentRepository.save(comment);// 저장
        article.getComments().add(comment);// 주인인 아닌 곳에 추가
    }
}
