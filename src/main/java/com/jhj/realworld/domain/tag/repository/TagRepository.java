package com.jhj.realworld.domain.tag.repository;

import com.jhj.realworld.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
