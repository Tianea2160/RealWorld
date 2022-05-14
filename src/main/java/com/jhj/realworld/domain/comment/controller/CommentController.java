package com.jhj.realworld.domain.comment.controller;


import com.jhj.realworld.domain.comment.Comment;
import com.jhj.realworld.domain.comment.dto.CommentCreateDto;
import com.jhj.realworld.domain.comment.service.CommentService;
import com.jhj.realworld.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/articles/{slug}/comments")
    public ResponseEntity<String> create(
            @PathVariable("slug")String slug,
            @RequestBody Map<String, CommentCreateDto> req){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        CommentCreateDto dto = req.get("comment");

        commentService.create(dto, slug, loginMember.getName());
        return ResponseEntity.ok("comment create success");
    }



}
