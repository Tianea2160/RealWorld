package com.jhj.realworld.domain.article.repository;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.controller.ArticleSearch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll(ArticleSearch articleSearch) {
        return em.createQuery("select m from Article m", Article.class)
                .getResultList();
    }

    public Optional<Article> findArticleBySlug(String slug) {
        Article article = em.createQuery("select m from Article m where m.slug = :slug", Article.class)
                .setParameter("slug", slug)
                .getSingleResult();
        return Optional.ofNullable(article);
    }

    public Long create(Article article) {
        em.persist(article);
        return article.getId();
    }

    public Optional<Article> findArticleById(Long loginId) {
        return Optional.ofNullable(em.find(Article.class, loginId));
    }

    public void delete(Article article) {
        em.remove(article);
    }
}
