package com.study.realworld.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
import com.study.realworld.domain.member.repository.MemberRepository;
import com.study.realworld.domain.member.dto.MemberCreateDto;
import com.study.realworld.domain.member.dto.MemberLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String x_request_with = "X-Requested-With";
    private static final String xmlHttpRequest = "XMLHttpRequest";
    private static final String localhost = "http://localhost:8080";

    @BeforeEach
    public void beforeDBSet() {

        String domain = "@example.com";
        Member member1 = Member.builder()
                .name("jhj")
                .password(passwordEncoder.encode("1111"))
                .email("kkk" + domain)
                .bio("hello")
                .role(Role.USER)
                .build();
        Member member2 = Member.builder()
                .name("kkk")
                .password(passwordEncoder.encode("1111"))
                .email("jjj" + domain)
                .role(Role.USER)
                .bio("hello")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
    }


    @Test
    void 회원가입_하고_로그인() throws Exception {
        //given
        String url = "http://localhost:8080/api/signin";
        String username = "lll";
        MemberCreateDto memberCreateDto = new MemberCreateDto();
        memberCreateDto.setUsername(username);
        memberCreateDto.setPassword("1111");
        memberCreateDto.setEmail("jhj@example.com");
        String content = objectMapper.writeValueAsString(memberCreateDto);

        //when

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(x_request_with, xmlHttpRequest)
                        .content(content))
                .andExpect((res) -> {

                    System.out.println(res.getResponse().getStatus());
                    System.out.println(res.getResponse().getContentAsString());

                });

        //login
        url = "http://localhost:8080/api/members/login";

        MemberLoginDto memberLoginDto = new MemberLoginDto();

        memberLoginDto.setUsername(username);
        memberLoginDto.setPassword("1111");

        content = objectMapper.writeValueAsString(memberLoginDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(x_request_with, xmlHttpRequest)
                        .content(content))
                .andExpect((res) -> {

                    System.out.println(res.getResponse().getStatus());
                    System.out.println(res.getResponse().getContentAsString());

                });
    }

    public void login(String username, String password) throws Exception {

        String url = "http://localhost:8080/api/members/login";

        MemberLoginDto memberLoginDto = new MemberLoginDto();

        memberLoginDto.setUsername(username);
        memberLoginDto.setPassword(password);

        String content = objectMapper.writeValueAsString(memberLoginDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(x_request_with, xmlHttpRequest)
                        .content(content))
                .andExpect((res) -> {

                    System.out.println(res.getResponse().getStatus());
                    System.out.println(res.getResponse().getContentAsString());

                });
    }


    @Test
    void 다른사람_프로필_가져오기() throws Exception {


        String url = localhost + "/api/profiles/jhj";


        login("kkk", "1111");

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect((res)->{
            System.out.println(res.getResponse().getStatus());
            System.out.println(res.getResponse().getContentAsString());
        });
    }


}