package com.study.realworld.domain.articletag;


import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.tag.Tag;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity(name = "article_tag")
public class ArticleTag {
    @Id @GeneratedValue
    @Column(name = "article_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static ArticleTag create(Article article, Tag tag){
        ArticleTag articleTag = new ArticleTag();
        articleTag.article = article;
        articleTag.tag = tag;
        return articleTag;
    }

}
