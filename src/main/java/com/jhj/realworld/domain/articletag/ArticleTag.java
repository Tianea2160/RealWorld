package com.jhj.realworld.domain.articletag;


import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.tag.Tag;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;


@Getter
@Entity
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
