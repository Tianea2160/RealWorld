package com.jhj.realworld.domain.comment;

import com.jhj.realworld.domain.TimeExtend;
import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.comment.dto.CommentCreateDto;
import com.jhj.realworld.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeExtend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    private String body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")//단방향 연결
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Comment(String body, Member author, Article article) {
        this.body = body;
        this.author = author;
        this.article = article;
    }

    public static Comment from(CommentCreateDto dto, Member author, Article article) {
        return  Comment.builder()
                .body(dto.getBody())
                .author(author)
                .article(article)
                .build();
    }

    public void update(String body) {
        this.body = body;
    }
}
