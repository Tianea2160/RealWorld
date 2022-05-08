package com.jhj.realworld.global.auth.dto;

import com.jhj.realworld.domain.member.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class MemberContext extends User {

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getName(), member.getPassword(), authorities);
        this.member = member;
    }

    private Member member;
}
