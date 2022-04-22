package com.jhj.realworld.service;


import com.jhj.realworld.domain.Member;
import com.jhj.realworld.dto.MemberCreateDto;
import com.jhj.realworld.dto.MemberResponseDto;
import com.jhj.realworld.dto.MemberUpdateDto;
import com.jhj.realworld.exception.NotExistMemberException;
import com.jhj.realworld.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

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
