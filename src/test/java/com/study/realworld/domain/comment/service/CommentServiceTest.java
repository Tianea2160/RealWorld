package com.study.realworld.domain.comment.service;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.comment.dto.CommentCreateDto;
import com.study.realworld.domain.comment.dto.CommentResponseDto;
import com.study.realworld.domain.comment.repository.CommentRepository;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EntityManager em;


    @BeforeEach
    public void initArticleAndMember() {
        String temp = "jhjh";
        Member member = Member.builder()
                .name(temp)
                .password(passwordEncoder.encode(temp))
                .email(temp + "@example.com")
                .role(Role.USER)
                .build();
        em.persist(member);

        Article article = Article.builder()
                .slug(temp)
                .body("hello world")
                .description("test1")
                .member(member)
                .build();
        em.persist(article);
    }


    @Test
    void Comment생성() throws Exception {
        String slug = "jhjh";
        String name = "jhjh";

        CommentCreateDto createDto = new CommentCreateDto();
        createDto.setBody("helle world");

        commentService.create(createDto, slug, name);// 한개 저장

        List<CommentResponseDto> list = commentService.findAll(slug, name);// 한개 찾기

        assertThat(list.get(0).getBody()).isEqualTo(createDto.getBody());// body 내용이 같아야한다.

        long commentId = list.get(0).getId();

        commentService.delete(slug, commentId);// 삭제

        list = commentService.findAll(slug, name);
        assertThat(list.size()).isEqualTo(0);// 아무것도 없어야한다.

        assertThat(commentRepository.findCommentById(commentId).isEmpty()).isEqualTo(true);

    }
}