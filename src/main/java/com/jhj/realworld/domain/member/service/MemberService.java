package com.jhj.realworld.domain.member.service;


import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.global.dto.MemberCreateDto;
import com.jhj.realworld.global.dto.MemberResponseDto;
import com.jhj.realworld.global.dto.MemberUpdateDto;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final Member.MemberRepository memberRepository;

    //create
    public Long join(MemberCreateDto dto){
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        memberRepository.save(member);
        return member.getId();
    }

    //read
    @Transactional(readOnly = true)
    public MemberResponseDto findByEmail(String email){
        Member member = memberRepository
                .findByEmail(email)
                .orElseThrow(NotExistMemberException::new);
        return MemberResponseDto.of(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findByUserName(String email){
        Member member = memberRepository
                .findByEmail(email)
                .orElseThrow(NotExistMemberException::new);
        return MemberResponseDto.of(member);
    }

    // update
    public Long update(MemberUpdateDto dto){
        Member member = memberRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(NotExistMemberException::new);
        return member.getId();
    }

    //delete
    public void deleteById(Long memberId){
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(NotExistMemberException::new);
        memberRepository.delete(member);
    }
}
