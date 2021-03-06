package com.study.realworld.domain.article.service;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.article.dto.ArticleDto;
import com.study.realworld.domain.article.repository.ArticleRepository;
import com.study.realworld.domain.articletag.ArticleTag;
import com.study.realworld.domain.articletag.repository.ArticleTagRepository;
import com.study.realworld.domain.like.repository.LikeRepository;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
import com.study.realworld.domain.member.repository.MemberRepository;
import com.study.realworld.domain.member.service.MemberService;
import com.study.realworld.domain.tag.Tag;
import com.study.realworld.domain.tag.repository.TagRepository;
import com.study.realworld.global.exception.NotExistMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;
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
    @Autowired
    private LikeRepository likeRepository ;
    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    public void makeMemberAndArticle() {
        for (char i = 'A'; i < 'A' + 5; i++) {
            String temp = "" + i + i + i;
            Member member = Member.builder()
                    .name(temp)
                    .password(passwordEncoder.encode(temp))
                    .email("" + i + i + i + "@example.com")
                    .role(Role.USER)
                    .build();
            em.persist(member);

            for(int j  = 0; j<5; j++){
                String content = temp + j;
                Article article = Article.builder()
                        .slug(content)
                        .title(content)
                        .body(content)
                        .member(member)
                        .description(content)
                        .build();
                em.persist(article);
                member.getArticles().add(article);
            }
        }
    }

    @Test
    void ?????????_??????_?????????() throws Exception {
        //given
        Member member = memberRepository
                .findMemberByName("AAA")
                .orElseThrow(() -> new NotExistMemberException("???????????? ????????? ????????????"));

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
                .orElseThrow(() -> new RuntimeException("???????????? ??????."));

        for (int i = 0; i < 5; i++) {
            String tagName = "tag" + i;
            Tag tag = new Tag(tagName);
            Tag savedTag = tagRepository.save(tag);

            ArticleTag articleTag = ArticleTag.create(article, tag);
            ArticleTag savedArticleTag = articleTagRepository.save(articleTag);
            savedArticle.getArticleTags().add(savedArticleTag);
        }

        //when
        //?????? ????????? ????????? ????????????
        savedArticle
                .getArticleTags()
                .forEach((articleTag -> System.out.println(articleTag.getTag().getContent())));

        Tag tag0 = tagRepository
                .findTagByContent("tag0")
                .orElseThrow(()->new RuntimeException("nothing tag"));

        assertThat(tag0.getContent()).isEqualTo("tag0");
        assertThat(tag0.getId()).isGreaterThan(0L);

        tag0.setContent("new tag0");

        //savedArticle.getArticleTags().forEach((articleTag -> System.out.println(articleTag.getTag().getContent())));
    }


    @Test
    void ????????????_?????????_feed_????????????_????????????_??????????????????() throws Exception{

        Member login = memberRepository.findMemberByName("AAA").orElseThrow();

        memberService.follow(login, "BBB");
        memberService.follow(login, "CCC");


        assertThat(login.getFollowers().size()).isEqualTo(2);
        assertThat(login.getFollowers().get(0).getName()).isEqualTo("BBB");
        assertThat(login.getFollowers().get(1).getName()).isEqualTo("CCC");
        em.flush();

        List<ArticleDto> feeds = articleService.findFeeds(login);
        assertThat(feeds.size()).isEqualTo(10);
        //feeds.forEach(System.out::println);

        articleService.favorite("BBB2", login.getName());
        assertThat(login.getFollowers().get(1).getName()).isEqualTo("CCC");
        //assertThat(login.getFollowers().get(2)()).isEqualTo("BBB2");
        
        articleService.unfavorite("BBB2", login.getName());

    }

    @Test
    void ???????????????_???????????????_???????????????_???????????????????????????() throws Exception{
        Member login = memberRepository.findMemberByName("AAA").orElseThrow();

        String slug = "BBB2";
        articleService.favorite(slug, login.getName());

        ArticleDto dto = articleService.findBySlug(slug, login.getName());
        System.out.println(dto);
    }

    @Test
    void ???????????????_?????????_??????_?????????_?????????_????????????() throws Exception{
        //given
        Member login = memberRepository.findMemberByName("AAA").orElseThrow();

        String slug = "BBB2";
        //Article saved = articleRepository.findArticleBySlug(slug).orElseThrow();
        articleService.favorite(slug, login.getName());

        List<ArticleDto> feeds = articleService.findFeeds(login);
        feeds.forEach(System.out::println);
    }
}