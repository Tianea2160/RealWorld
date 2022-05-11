package com.jhj.realworld.domain.article.dto;

import com.jhj.realworld.domain.article.Article;
import lombok.Builder;
import lombok.Data;

@Data
public class ArticleDto {
    private String slug;
    private String description;
    private String body;
    private Long view;

    @Builder
    public ArticleDto(String slug, String description, String body, Long view) {
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.view = view;
    }

    public static ArticleDto of(Article article){
        return ArticleDto.builder()
                .slug(article.getSlug())
                .body(article.getBody())
                .description(article.getDescription())
                .view(article.getView())
                .build();
    }
}
