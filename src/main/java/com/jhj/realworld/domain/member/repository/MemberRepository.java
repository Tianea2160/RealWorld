package com.jhj.realworld.domain.member.repository;

import com.jhj.realworld.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository{
    @PersistenceContext
    private EntityManager em;

    public List<Member> findAll() {
        try{
            return em.createQuery("select m from Member m", Member.class)
                    .getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    public Optional<Member> findByEmail(String email){
        try{
            Member re = em.createQuery("select m from Member m where m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(re);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    public Optional<Member> findMemberByName(String username){
        try{
            Member re = em.createQuery("select m from Member m where m.name = :username", Member.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(re);
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Optional<Member> findMemberById(Long loginId){
        try{
            Member re = em.createQuery("select m from Member m where m.id = :loginId", Member.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult();
            return Optional.of(re);
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void save(Member member) {
        em.persist(member);
    }
}