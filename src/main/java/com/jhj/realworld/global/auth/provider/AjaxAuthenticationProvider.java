package com.jhj.realworld.global.auth.provider;

import com.jhj.realworld.global.auth.dto.MemberContext;
import com.jhj.realworld.global.auth.token.AjaxAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext content = (MemberContext) userDetailsService.loadUserByUsername(name);

        if (content == null || content.getUsername() == null || content.getPassword() == null) {
            throw new BadCredentialsException("there is no username or password");
        }

        log.info(password + ":" + content.getPassword());

        if (!passwordEncoder.matches(password, content.getPassword())) {
            throw new BadCredentialsException("password is not correct");
        }

        return new AjaxAuthenticationToken(content.getMember(), null, content.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.equals(authentication);
    }
}
