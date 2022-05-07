package com.jhj.realworld.domain.article.repository;

import com.jhj.realworld.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
