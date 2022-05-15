package com.jhj.realworld.domain.like;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "member_article")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Like(Article article, Member member) {
        this.member = member;
        this.article = article;
    }
}
