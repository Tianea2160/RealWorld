package com.jhj.realworld.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhj.realworld.global.dto.MemberCreateDto;
import com.jhj.realworld.global.dto.MemberLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String x_request_with = "X-Requested-With";
    private static final String xmlHttpRequest = "XMLHttpRequest";
    @Test
    void 회원가입_하고_로그인하기() throws Exception {
        //given
        String url = "http://localhost:8080/api/signin";
        MemberCreateDto memberCreateDto = new MemberCreateDto();
        memberCreateDto.setUsername("jhj");
        memberCreateDto.setPassword("1111");
        memberCreateDto.setEmail("jhj@example.com");
        String content = objectMapper.writeValueAsString(memberCreateDto);

        //when

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(x_request_with, xmlHttpRequest)
                        .content(content))
                .andExpect((res)->{

                    System.out.println(res.getResponse().getStatus());
                    System.out.println(res.getResponse().getContentAsString());

                });

        url = "http://localhost:8080/api/login";

        MemberLoginDto memberLoginDto = new MemberLoginDto();

        memberLoginDto.setUsername("jhj");
        memberLoginDto.setPassword("1111");

        content = objectMapper.writeValueAsString(memberLoginDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(x_request_with, xmlHttpRequest)
                        .content(content))
                .andExpect((res)->{

                    System.out.println(res.getResponse().getStatus());
                    System.out.println(res.getResponse().getContentAsString());

                });





    }
}