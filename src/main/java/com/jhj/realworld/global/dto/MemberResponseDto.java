package com.jhj.realworld.global.dto;

import com.jhj.realworld.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberResponseDto {

    private String name;
    private String email;
    private String nickname;
    private LocalDate birthDay;

    @Builder
    public MemberResponseDto(String name, String email, String nickname, LocalDate birthDay) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.birthDay = birthDay;
    }


    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
