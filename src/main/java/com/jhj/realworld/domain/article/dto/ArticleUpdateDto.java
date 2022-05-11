package com.jhj.realworld.domain.article.dto;

import lombok.Data;

@Data
public class ArticleUpdateDto {
    private String title;
    private String body;
    private String description;
}
