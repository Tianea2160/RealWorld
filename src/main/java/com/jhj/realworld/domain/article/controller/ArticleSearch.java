package com.jhj.realworld.domain.article.controller;


import lombok.Getter;

@Getter
public class ArticleSearch {
    private String author;
    private String tag;
    private String favorited;
    private Long limit;
    private Long offset;
}
