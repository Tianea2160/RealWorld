package com.study.realworld.domain.article.dto;

import com.study.realworld.domain.article.Article;
import com.study.realworld.global.dto.Profile;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDto {
    private String slug;
    private String description;
    private String body;
    private Long view;
    private List<String> tagList = new ArrayList<>();
    private boolean favorited = false;
    private int favoritesCount = 0;
    private Profile author;

    @Builder
    public ArticleDto(
            String slug,
            String description,
            String body,
            Long view,
            List<String> tagList,
            boolean favorited,
            int favoritesCount,
            Profile author
    ) {
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.view = view;
        this.tagList = tagList;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.author = author;
    }

    public static ArticleDto of(Article article) {
        return ArticleDto.builder()
                .slug(article.getSlug())
                .body(article.getBody())
                .description(article.getDescription())
                .view(article.getView())
                .favoritesCount(article.getLikes().size())
                .author(Profile.of(article.getMember()))
                .build();
    }
}
