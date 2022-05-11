package com.jhj.realworld.domain.article.service;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.dto.ArticleDto;
import com.jhj.realworld.domain.article.repository.ArticleRepository;
import com.jhj.realworld.domain.articletag.ArticleTag;
import com.jhj.realworld.domain.articletag.repository.ArticleTagRepository;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.Role;
import com.jhj.realworld.domain.member.repository.MemberRepository;
import com.jhj.realworld.domain.member.service.MemberService;
import com.jhj.realworld.domain.tag.Tag;
import com.jhj.realworld.domain.tag.repository.TagRepository;
import com.jhj.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void makeMember() {
        for (char i = 'A'; i < 'A' + 5; i++) {
            String temp = "" + i + i + i;
            Member member = Member.builder()
                    .name(temp)
                    .password(passwordEncoder.encode(temp))
                    .email("" + i + i + i + "@example.com")
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }
    }


    @Test
    void 게시물_하나_만들기() throws Exception {
        //given
        Member member = memberRepository
                .findMemberByName("AAA")
                .orElseThrow(() -> new NotExistMemberException("해당하는 유저가 없습니다"));

        String temp = "1111";
        Article article = Article.builder()
                .slug(temp)
                .body("hello world")
                .description("test1")
                .member(member)
                .build();

        articleRepository.create(article);

        Article savedArticle = articleRepository
                .findArticleBySlug(temp)
                .orElseThrow(() -> new RuntimeException("게시글이 없다."));

        for (int i = 0; i < 5; i++) {
            String tagName = "tag" + i;
            Tag tag = new Tag(tagName);
            Tag savedTag = tagRepository.save(tag);

            ArticleTag articleTag = ArticleTag.create(article, tag);
            ArticleTag savedArticleTag = articleTagRepository.save(articleTag);
            savedArticle.getArticleTags().add(savedArticleTag);
        }

        //when
        //방금 저장한 게시글 불러오기
        savedArticle
                .getArticleTags()
                .forEach((articleTag -> System.out.println(articleTag.getTag().getContent())));

        Tag tag0 = tagRepository
                .findTagByContent("tag0")
                .orElseThrow(()->new RuntimeException("nothing tag"));

        assertThat(tag0.getContent()).isEqualTo("tag0");
        assertThat(tag0.getId()).isGreaterThan(0L);

        tag0.setContent("new tag0");

        savedArticle
                .getArticleTags()
                .forEach((articleTag -> System.out.println(articleTag.getTag().getContent())));
    }


}