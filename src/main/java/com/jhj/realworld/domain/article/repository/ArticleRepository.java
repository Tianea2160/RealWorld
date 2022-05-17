package com.jhj.realworld.domain.article.repository;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.controller.ArticleSearch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll(ArticleSearch articleSearch) {
        try {
            return em.createQuery("select m from Article m", Article.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Optional<Article> findArticleBySlug(String slug) {
        try {
            Article article = em.createQuery("select m from Article m where m.slug = :slug", Article.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
            return Optional.ofNullable(article);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    public Long create(Article article) {
        em.persist(article);
        return article.getId();
    }

    public Optional<Article> findArticleById(Long loginId) {
        try {
            return Optional.ofNullable(em.find(Article.class, loginId));
        }catch(NoResultException e){
            return Optional.empty();
        }
    }

    public void delete(Article article) {
        em.remove(article);
    }
}
