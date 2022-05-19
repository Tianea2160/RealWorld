package com.jhj.realworld.domain.article;

import com.jhj.realworld.domain.article.dto.ArticleUpdateDto;
import com.jhj.realworld.domain.articletag.ArticleTag;
import com.jhj.realworld.domain.comment.Comment;
import com.jhj.realworld.domain.like.Like;
import com.jhj.realworld.domain.tag.Tag;
import com.jhj.realworld.domain.TimeExtend;
import com.jhj.realworld.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Entity(name = "article")
@NoArgsConstructor
public class Article extends TimeExtend implements Comparator<Article> {
    @Id @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @Column(unique = true)
    private String slug;

    private String title;

    private String description;

    private String body;

    @NotNull
    private Long view;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<ArticleTag> articleTags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    //@JoinColumn(name = "comment_id")//단방향 연결
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Article(String slug, String title, String description, String body, Member member) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.view = 0L;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void update(ArticleUpdateDto dto) {
        if(dto.getTitle() != null){
            this.slug = dto.getTitle();
        }

        if(dto.getTitle() != null){
            this.title = dto.getTitle();
        }
        if(dto.getBody() != null){
            this.body = dto.getBody();
        }
        if(dto.getDescription()!=null){
            this.description = dto.getDescription();
        }
    }

    @Override
    public int compare(Article a1, Article a2) {
        return a1.slug.compareTo(a2.slug);
    }
}
