package com.study.realworld.domain.member;


import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.TimeExtend;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeExtend implements Comparator<Member> {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(name = "member_name", unique = true)
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Column
    private String email;

    private String bio;

    private String img;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY) // 단방향
    private List<Member> followers = new ArrayList<>();

    //빌더 패턴으로만 객체 생성을 유도(생성)
    @Builder
    public Member(String name, String password, String email, String bio, String img, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.img = img;
        this.role = role;
    }

    // update
    public void update(String email, String bio, String img){
        this.email = email;
        this.bio = bio;
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compare(Member o1, Member o2) {
        if(Objects.equals(o1.id, o2.id)) return 0;
        return o1.id > o2.id ? 1 :-1;
    }
}
