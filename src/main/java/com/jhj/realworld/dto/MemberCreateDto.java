package com.jhj.realworld.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MemberCreateDto {
    private String password;
    private String name;
    private String email;
}
