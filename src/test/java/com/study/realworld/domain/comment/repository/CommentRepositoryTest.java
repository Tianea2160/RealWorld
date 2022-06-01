package com.study.realworld.domain.comment.repository;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.article.repository.ArticleRepository;
import com.study.realworld.domain.comment.Comment;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void 댓글을_만들고_잘_저장되는지_확인하는_테스트() throws Exception{
        Member member = Member.builder()
                .name("jhj")
                .password("1111")
                .email("1111")
                .role(Role.USER)
                .build();
        em.persist(member);

        Article article = Article.builder()
                .slug("kkkkk")
                .title("kkkkk")
                .description("kkkkk")
                .body("kkkkk")
                .member(member)
                .build();
        em.persist(article);


        Comment comment = new Comment("test comment", member, article);

        em.persist(comment);
        assertThat(article.getComments().contains(comment)).isEqualTo(false);
        article.getComments().add(comment);

        Article savedArticle = articleRepository.findArticleBySlug("kkkkk").orElseThrow();
        assertThat(savedArticle.getComments().contains(comment)).isEqualTo(true);
    }

}