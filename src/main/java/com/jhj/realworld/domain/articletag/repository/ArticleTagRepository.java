package com.jhj.realworld.domain.articletag.repository;

import com.jhj.realworld.domain.articletag.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
}
