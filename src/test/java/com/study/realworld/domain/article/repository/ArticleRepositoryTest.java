package com.study.realworld.domain.article.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ArticleRepositoryTest {
    @PersistenceContext
    private EntityManager em;


    @Test
    void QueryDsl사용해보기() throws Exception{
    }

}