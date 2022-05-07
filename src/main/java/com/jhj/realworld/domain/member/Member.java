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

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeExtend {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(name = "member_name")
    private String name;

    @NotNull
    @Column(unique = true)
    private String email;//이메일을 통해서 사용자를 검색할 것이다.

    @NotNull
    private String token;

    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY) // 단방향
    private List<Member> followers = new ArrayList<>();

    //빌더 패턴으로만 객체 생성을 유도(생성)
    @Builder
    public Member(String email, String token, String name, Role role) {
        this.email = email;
        this.token = token;
        this.name = name;
        this.role = role;
    }

    // update
    public void update(String name, String token, String email) {
        this.name = name;
        this.email = email;
        this.token = token;
    }

    @Repository
    public static interface MemberRepository extends JpaRepository<Member, Long> {
        List<Member> findAll();
        Optional<Member> findByEmail(String email);
        Optional<Member> findById(Long memberId);
    }
}
