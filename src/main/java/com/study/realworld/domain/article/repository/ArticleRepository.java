package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepoCustom {
    Page<Article> findAll(Pageable pageable);
}


/*
interface 로 EntityRepositoCustom 을 만드시고 EntityRepository 에서
EntityRepositoCustom 를 상속받으시고 EntityRepositoCustom 를
구현하는 EntityRepositoCustomImpl class 를 하나 만드시면됩니다
 */