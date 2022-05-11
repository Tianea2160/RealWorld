package com.jhj.realworld.domain.member.controller;


import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.service.MemberService;
import com.jhj.realworld.global.auth.dto.SessionMember;
import com.jhj.realworld.domain.member.dto.MemberCreateDto;
import com.jhj.realworld.domain.member.dto.MemberUpdateDto;
import com.jhj.realworld.global.dto.Profile;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController
{
    public final MemberService memberService;

    //로그인
    @PostMapping("/api/signin")
    public ResponseEntity<Long> signIn(@RequestBody MemberCreateDto dto){
        Long savedId = memberService.join(dto);
        return ResponseEntity.ok(savedId);
    }

    @GetMapping("/api/member")
    public ResponseEntity<SessionMember> currentUser(){
        //로그인 사용자 정보를
        Member loginMember = getCurrentLoginMember();
        SessionMember sessionMember = SessionMember.of(loginMember);
        return ResponseEntity.ok(sessionMember);
    }

    @PostMapping("/api/update")
    public ResponseEntity<Long> update(@RequestBody MemberUpdateDto memberUpdateDto){
        //현재 로그인한 사용자의 email, bio, img를 수정한다.
        Member loginMember = getCurrentLoginMember();
        Long id = memberService.update(loginMember, memberUpdateDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/api/profiles/{username}")
    public ResponseEntity<Profile> profile(
            @PathVariable String username
    ){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = memberService.findProfile(loginMember, username);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/api/profiles/{username}/follow")
    public ResponseEntity<String> follow(
            @PathVariable String username){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        memberService.follow(loginMember, username);

        return ResponseEntity.ok("follow");
    }

    @PostMapping("/api/profiles/{username}/unfollow")
    public ResponseEntity<String> unfollow(
            @PathVariable String username){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        memberService.unfollow(loginMember, username);
        return ResponseEntity.ok("unfollow");
    }

    private Member getCurrentLoginMember(){
        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loginMember == null){
            throw new NotExistMemberException("로그인된 멤버가 없습니다.");
        }
        return loginMember;
    }
}
