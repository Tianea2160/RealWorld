package com.jhj.realworld.domain.member.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MemberCreateDto {
    private String password;
    private String username;
    private String email;
    private String bio;
    private String img;
}
