package com.jhj.realworld.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private String name;
    private String password;
    private String email;
}
