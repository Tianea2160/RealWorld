package com.jhj.realworld.repository;

import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Logger;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    private final Logger log = Logger.getAnonymousLogger();

    @Test
    void member생성_및_추가() throws Exception {
        //given
        String name = "jhj";
        String password = "qwer1234";
        String email = "qwer1234@example.com";

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();
        //when
        em.persist(member);

        //then
        Member findOne = em.find(Member.class, member.getId());

        log.info("id: " + member.getId());
        log.info("created: " + member.getCreatedAt());
        log.info("modified: " + member.getModifiedAt());
        Assertions.assertThat(findOne).isEqualTo(member);
        Assertions.assertThat(findOne.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findOne.getRole()).isEqualTo(member.getRole());
    }

    @Test
    void follow기능_구현하기위해서_자기자신에_대해서_연관관계_매핑시도() throws Exception {
        //given
        String name = "jhj";
        String password = "qwer1234";
        String email = "@example.com";

        Member member1 = Member.builder()
                .name("111")
                .email("111" + email)
                .password(password)
                .role(Role.USER)
                .build();

        Member member2 = Member.builder()
                .name("222")
                .email("222" + email)
                .password(password)
                .role(Role.USER)
                .build();

        Member member3 = Member.builder()
                .name("333")
                .email("333" + email)
                .password(password)
                .role(Role.USER)
                .build();

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        //when
        member1.getFollowers().add(member2);
        member1.getFollowers().add(member3);
        em.flush();

        //then
        Member saved = em.find(Member.class, member1.getId());

        Assertions.assertThat(saved.getFollowers().size()).isEqualTo(2);
        Member updateOne = saved.getFollowers().get(0);

        String newEmail = "new" + email;
        String bio = "bio";

        updateOne.update(newEmail, bio, null);

        Member res = em.find(Member.class, member2.getId());
        Assertions.assertThat(res.getEmail()).isEqualTo(newEmail);
        Assertions.assertThat(res.getBio()).isEqualTo(bio);
        Assertions.assertThat(res.getImg()).isEqualTo(null);

    }

}