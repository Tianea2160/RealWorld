package com.study.realworld.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.global.auth.token.AjaxAuthenticationToken;
import com.study.realworld.domain.member.dto.MemberLoginDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/api/members/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // ajax 인지 확인
        if (!isAjax(request)) {
            throw new IllegalStateException("not ajax request");
        }

        MemberLoginDto dto = objectMapper.readValue(request.getReader(), MemberLoginDto.class);
        // username and password exist confirm
        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new IllegalStateException("username or password is empty");
        }
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(dto.getUsername(), dto.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {
        return request.getHeader("X-Requested-With").equals("XMLHttpRequest");
    }
}
