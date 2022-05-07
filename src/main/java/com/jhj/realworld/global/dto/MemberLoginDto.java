package com.jhj.realworld.global.dto;

import com.jhj.realworld.domain.member.Member;
import lombok.Data;

@Data
public class MemberLoginDto {
    private String username;
    private String password;



    public static MemberLoginDto of(Member member){
        MemberLoginDto dto = new MemberLoginDto();
        dto.setUsername(member.getName());
        dto.setPassword(member.getPassword());
        return dto;
    }
}
