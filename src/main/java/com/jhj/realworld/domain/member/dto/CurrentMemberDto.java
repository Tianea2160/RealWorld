package com.jhj.realworld.domain.member.dto;

import com.jhj.realworld.domain.member.Member;
import lombok.Data;

@Data
public class CurrentMemberDto {
    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;


    public static CurrentMemberDto of(Member member) {
        CurrentMemberDto dto = new CurrentMemberDto();
        dto.setBio(member.getBio());
        dto.setPassword(member.getPassword());
        dto.setEmail(member.getEmail());
        dto.setUsername(member.getName());
        dto.setImage(member.getImg());
        return dto;
    }
}
