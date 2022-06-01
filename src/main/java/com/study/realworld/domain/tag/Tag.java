package com.study.realworld.domain.tag;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity(name = "tag")
@NoArgsConstructor
public class Tag {
    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String content;

    public Tag(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
