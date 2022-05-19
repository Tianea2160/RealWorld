package com.jhj.realworld.domain.comment.repository;

import com.jhj.realworld.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CommentRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Comment> findCommentById(Long id){
        try{
            Comment res = em.createQuery("select m from Comment m where m.id = :id", Comment.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(res);
        }catch(NoResultException e){
            return Optional.empty();
        }
    }

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }
}
