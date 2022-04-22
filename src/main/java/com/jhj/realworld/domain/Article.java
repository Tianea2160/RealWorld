package com.jhj.realworld.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Article extends TimeExtend{
    @Id @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    private String slug;

    private String description;

    private String body;

    @NotNull
    private int view;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")//단방향 연결
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Article(String slug, String description, String body, Member member) {
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.view = 0;
        this.member = member;
    }
}
