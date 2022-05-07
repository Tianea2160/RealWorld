package com.jhj.realworld.domain.member.controller;


import com.jhj.realworld.domain.member.service.MemberService;
import com.jhj.realworld.global.dto.MemberCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController
{
    @Autowired
    public MemberService memberService;

    @PostMapping("/api/signin")
    public ResponseEntity<Long> signIn(@RequestBody MemberCreateDto dto){
        Long savedId = memberService.join(dto);
        return ResponseEntity.ok(savedId);
    }
}
