package com.jhj.realworld.domain.comment.controller;


import com.jhj.realworld.domain.comment.Comment;
import com.jhj.realworld.domain.comment.dto.CommentCreateDto;
import com.jhj.realworld.domain.comment.dto.CommentResponseDto;
import com.jhj.realworld.domain.comment.service.CommentService;
import com.jhj.realworld.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/articles/{slug}/comments")
    public ResponseEntity<String> create(
            @PathVariable("slug") String slug,
            @RequestBody Map<String, CommentCreateDto> req) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        CommentCreateDto dto = req.get("comment");

        commentService.create(dto, slug, loginMember.getName());
        return ResponseEntity.ok("comment create success");
    }

    @GetMapping("/api/articles/{slug}/comments")
    public ResponseEntity<Map<String, List<CommentResponseDto>>> findAll(
            @PathVariable("slug") String slug
    ) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, List<CommentResponseDto>> map = new HashMap<>();

        List<CommentResponseDto> list = commentService.findAll(slug, loginMember.getName());
        map.put("comments", list);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/api/articles/{slug}/comments/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("slug") String slug,
            @PathVariable("id")Long commentId
    ){
        commentService.delete(slug, commentId);
        return ResponseEntity.ok("delete success");
    }
}
