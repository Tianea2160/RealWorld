package com.jhj.realworld.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.global.dto.MemberLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException
    {
        Member member = (Member) authentication.getPrincipal();

        // response setting
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//post 방식

        MemberLoginDto dto = MemberLoginDto.of(member);
        objectMapper.writeValue(response.getWriter(), dto);
    }
}
