package com.study.realworld.repository;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.comment.Comment;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
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
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    private final Logger log = Logger.getAnonymousLogger();

    @Test
    void 게시글을_작성하고_댓글을_여러개_작성하여_저장하고_검증한다() throws Exception{
        //given
        String name = "jhj";
        String token = "qwer1234";
        String email = "qwer1234@example.com";

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(token)
                .role(Role.USER)
                .build();
        em.persist(member); //member save

        String slug = "hello world!";
        String description = "this is testing description";
        String body = "Life is short, and python is good";
        Article article = Article.builder()
                .slug(slug)
                .description(description)
                .body(body)
                .member(member)
                .build();

        em.persist(article);// article save

        String commentBody = "test comment";

        for(int i = 0; i<5; i++){
            Comment comment = new Comment(commentBody, member, null);
            em.persist(comment);
            article.getComments().add(comment);
        }
        String newBody = "new body";
        //when

        //then
        Assertions.assertThat(article.getComments().size()).isEqualTo(5);//
        Comment saved = article.getComments().get(0);
        saved.update(newBody);

        Assertions.assertThat(article.getComments().get(0).getBody()).isEqualTo(newBody);//
        Assertions.assertThat(member).isEqualTo(article.getComments().get(0).getAuthor());//
    }
}