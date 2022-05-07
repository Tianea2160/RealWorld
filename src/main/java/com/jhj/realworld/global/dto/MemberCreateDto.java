package com.jhj.realworld.global.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MemberCreateDto {
    private String password;
    private String username;
    private String email;
}
