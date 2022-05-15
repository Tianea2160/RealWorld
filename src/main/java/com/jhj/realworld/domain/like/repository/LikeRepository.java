package com.jhj.realworld.domain.like.repository;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.like.Like;
import com.jhj.realworld.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // 현재 로그인 사용자가 좋아요를 했는지 확인하는 메소드
    boolean existsLikeByArticleAndMember(Article article, Member member);
    boolean existsLikeByArticleEqualsAndMemberEquals(Article article, Member member);

    Optional<Like> findLikeByArticleAndMember(Article article, Member member);
}
