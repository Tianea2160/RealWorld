package com.jhj.realworld.repository;

import com.jhj.realworld.domain.Article;
import com.jhj.realworld.domain.Member;
import com.jhj.realworld.domain.Role;
import com.jhj.realworld.domain.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class TagRepositoryTest {
    @PersistenceContext
    private EntityManager em;

    private Logger log = Logger.getAnonymousLogger();

    @Test
    void 게시글을_생성하고_태크를_만들어서_넣어보자() throws Exception{
        //given
        String name = "jhj";
        String nickname = "jhj";
        String token = "qwer1234";
        String email = "qwer1234@example.com";

        Member member = Member.builder()
                .name(name)
                .email(email)
                .token(token)
                .role(Role.USER)
                .build();
        em.persist(member); //member create

        String slug = "hello world!";
        String description = "this is testing description";
        String body = "Life is short, and python is good";
        Article article = Article.builder()
                .slug(slug)
                .description(description)
                .body(body)
                .member(member)
                .build();

        em.persist(article);

        //when
        Tag tag1 = new Tag("hello");
        Tag tag2 = new Tag("world");
        em.persist(tag1);// tag에 대해서 저장을 해야함
        em.persist(tag2);

        article.getTags().add(tag1);
        article.getTags().add(tag2);
        //then
        Article findArticle = em.find(Article.class, article.getId());
        assertThat(findArticle.getTags().size()).isEqualTo(2);

        Tag findTag1 = em.find(Tag.class, tag1.getId());
        Tag findTag2 = em.find(Tag.class, tag2.getId());

        assertThat(findTag1).isEqualTo(tag1);
        assertThat(findTag2).isEqualTo(tag2);
    }
}