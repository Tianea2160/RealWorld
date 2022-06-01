package com.study.realworld.global.auth.dto;

import com.study.realworld.domain.member.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter @Setter
public class MemberContext extends User {

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getName(), member.getPassword(), authorities);
        this.member = member;
    }

    private Member member;
}
