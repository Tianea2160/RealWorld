package com.jhj.realworld.repository;

import com.jhj.realworld.domain.Article;
import com.jhj.realworld.domain.Member;
import com.jhj.realworld.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ArticleRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    private final Logger log = Logger.getAnonymousLogger();

    @Test
    void 게시물을_작성하고_잘_추가되는지_확인하기() throws Exception {
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

        //when
        em.persist(article);
        Article findArticle = em.find(Article.class, article.getId());

        //then
        Assertions.assertThat(member).isEqualTo(findArticle.getMember());
        Assertions.assertThat(article.getId()).isEqualTo(findArticle.getId());
    }
}