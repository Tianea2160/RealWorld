package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleRepoCustom {


    Optional<Article> findArticleBySlug(String slug);

    Long create(Article article);

    Optional<Article> findArticleById(Long loginId);

}
