package com.jhj.realworld.domain.article.controller;

import com.jhj.realworld.domain.article.dto.ArticleUpdateDto;
import com.jhj.realworld.domain.article.service.ArticleService;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.article.dto.ArticleCreateDto;
import com.jhj.realworld.domain.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/api/article")
    public ResponseEntity<List<ArticleDto>> findAll(
            ArticleSearch articleSearch
    ) {
        List<ArticleDto> list = articleService.findAll(articleSearch);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/api/article/{slug}")
    public ResponseEntity<ArticleDto> findArticleBySlug(@PathVariable("slug") String slug) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArticleDto dto = articleService.findBySlug(slug, loginMember.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/article/feed")
    public ResponseEntity<List<ArticleDto>> feed() {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(articleService.findFeeds(loginMember));
    }

    @PostMapping("/api/articles")
    public ResponseEntity<String> create(@RequestBody ArticleCreateDto createDto) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> tag = createDto.getTagList();
        log.info(tag.toString());
        Long id = articleService.create(loginMember.getName(), createDto);
        return ResponseEntity.ok("create success: " + id);
    }

    @PutMapping("/api/articles/{slug}")
    public ResponseEntity<Long> update(@PathVariable("slug") String slug, ArticleUpdateDto updateDto) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long updateId = articleService.update(loginMember.getId(),slug, updateDto);
        return ResponseEntity.ok(updateId);
    }

    @DeleteMapping("/api/articles/{slug}")
    public ResponseEntity<String> delete(@PathVariable("slug") String slug) {
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        articleService.delete(loginMember.getId(), slug);
        return ResponseEntity.ok("delete success");
    }

    @PostMapping("/api/articles/{slug}/favorite")
    public ResponseEntity<String> favorite(
            @PathVariable("slug")String slug
    ){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        articleService.favorite(slug, loginMember.getName());
        return ResponseEntity.ok("favorite success");
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    public ResponseEntity<String> unfavorite(
            @PathVariable("slug")String slug
    ){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        articleService.unfavorite(slug, loginMember.getName());
        return ResponseEntity.ok("ㅕㅜfavorite success");
    }
}
