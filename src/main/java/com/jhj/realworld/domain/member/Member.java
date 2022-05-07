package com.jhj.realworld.domain.member;


import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.TimeExtend;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeExtend {
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
    @Column(unique = true)
    private String email;//이메일을 통해서 사용자를 검색할 것이다.

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY) // 단방향
    private List<Member> followers = new ArrayList<>();

    //빌더 패턴으로만 객체 생성을 유도(생성)
    @Builder
    public Member(String name, String password, String email, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // update
    public void update(String username, String password, String email) {
        this.name = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
