package com.jhj.realworld.domain.comment;

import com.jhj.realworld.domain.TimeExtend;
import com.jhj.realworld.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Comment extends TimeExtend {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    private String body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")//단방향 연결
    private Member author;

    public Comment(String body, Member author) {
        this.body = body;
        this.author = author;
    }

    public void update(String body){
        this.body = body;
    }
}
