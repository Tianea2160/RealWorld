package com.jhj.realworld.global.dto;

import lombok.Data;

@Data
public class MemberSignInDto {
    private String username;
    private String password;
    private String email;
}
