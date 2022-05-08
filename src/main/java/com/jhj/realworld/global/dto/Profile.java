package com.jhj.realworld.global.dto;

import com.jhj.realworld.domain.member.Member;
import lombok.Data;

@Data
public class Profile {
    private String username;
    private String bio;
    private String img;
    private boolean following;

    public static Profile of(Member member) {
        Profile profile = new Profile();
        profile.setUsername(member.getName());
        profile.setBio(member.getBio());
        profile.setImg(member.getImg());
        return profile;
    }
}
