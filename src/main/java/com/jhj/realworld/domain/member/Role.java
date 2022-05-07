package com.jhj.realworld.domain.member;

public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
    private String key;

    Role(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
