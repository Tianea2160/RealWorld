package com.jhj.realworld.domain.comment.dto;

import com.jhj.realworld.domain.comment.Comment;
import com.jhj.realworld.global.dto.Profile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private Profile author;

    @Builder
    public CommentResponseDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, Profile author) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.body = body;
        this.author = author;
    }

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getModifiedAt())
                .author(Profile.of(comment.getAuthor()))
                .build();
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}
