package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepoCustom {

    @PersistenceContext
    private EntityManager em;

    public Optional<Article> findArticleBySlug(String slug) {
        try {
            Article article = em.createQuery("select m from Article m where m.slug = :slug", Article.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
            return Optional.ofNullable(article);
        } catch (NoResultException e) {
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
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // testing
    public List<Member> findFeed(Long loginMemberId){
        return em.createQuery("select m from Member m join fetch m.followers where m.id = :id", Member.class)
                .setParameter("id", loginMemberId)
                .getResultList();
    }
}
