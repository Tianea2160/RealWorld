package com.jhj.realworld.domain.member.repository;

import com.jhj.realworld.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long memberId);
    Optional<Member> findMemberByName(String username);
    Optional<Member> findMemberById(Long loginId);
}