package com.jhj.realworld.domain.member.service;


import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.Role;
import com.jhj.realworld.domain.member.dto.MemberCreateDto;
import com.jhj.realworld.domain.member.dto.MemberUpdateDto;
import com.jhj.realworld.domain.member.repository.MemberRepository;
import com.jhj.realworld.global.auth.dto.SessionMember;
import com.jhj.realworld.global.dto.*;
import com.jhj.realworld.global.exception.AlreadyMemberExistException;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    //create
    public Long join(MemberCreateDto dto){
        // 이미 같은 이름의 사람이 있는 지 확인
        memberRepository
                .findMemberByName(dto.getUsername())
                .ifPresent((member)->{
                    throw new AlreadyMemberExistException(member.getName() + " is already exist");
                });

        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getUsername())
                .bio(dto.getBio())
                .img(dto.getImg())
                .role(Role.USER)
                .build();

        member.setPassword(passwordEncoder.encode(dto.getPassword()));// encode
        memberRepository.save(member);
        return member.getId();
    }

    // update
    public Long update(Member loginMember, MemberUpdateDto dto){
        // 시큐리티 홀더로 가져온 멤버는 영속적이지 않기 때문에 update메소드를 사용해도 의미가 없다.
        Member member = memberRepository
                .findMemberByName(loginMember.getName())
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));

        member.update(dto.getEmail(), dto.getBio(), dto.getImg());
        loginMember.update(dto.getEmail(), dto.getBio(), dto.getImg());//시큐리티 홀더의 값도 수정을 해야한다.
        return member.getId();
    }

    @Transactional(readOnly = true)
    public Profile findProfile(Member loginMember, String username) {
        Member target = memberRepository.findMemberByName(username)
                .orElseThrow(() -> new NotExistMemberException("해당 회원을 찾을 수 없습니다."));
        Member login = memberRepository.findMemberByName(loginMember.getName())
                .orElseThrow(()->new NotExistMemberException("해당 회원을 찾을 수 없습니다."));

        Profile profile = Profile.of(target);
        profile.setFollowing(login.getFollowers().contains(target));
        return profile;
    }

    public void follow(Member loginMember, String username) {
        Member followed = memberRepository.findMemberByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));

        Member login = memberRepository.findMemberByName(loginMember.getName())
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));

        if(!login.getFollowers().contains(followed)){// 팔로우가 안되어있으면
            login.getFollowers().add(followed);
        }
    }

    public void unfollow(Member loginMember, String username) {
        Member followed = memberRepository.findMemberByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Member login = memberRepository.findMemberByName(loginMember.getName())
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));

        // 팔로우되어 있으면
        login.getFollowers().remove(followed);
    }

    public SessionMember findCurrentMember(String name) {
        Member member = memberRepository
                .findMemberByName(name)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자를 찾을 수 없습니다."));
        return SessionMember.of(member);
    }
}
