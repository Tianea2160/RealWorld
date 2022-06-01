package com.study.realworld.domain.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateDto {
    private String password;
    private String username;
    private String email;
    private String bio;
    private String img;
}
