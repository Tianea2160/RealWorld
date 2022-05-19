package com.jhj.realworld.domain.article.service;

import com.jhj.realworld.domain.article.Article;
import com.jhj.realworld.domain.article.dto.ArticleDto;
import com.jhj.realworld.domain.article.repository.ArticleRepository;
import com.jhj.realworld.domain.articletag.ArticleTag;
import com.jhj.realworld.domain.articletag.repository.ArticleTagRepository;
import com.jhj.realworld.domain.like.Like;
import com.jhj.realworld.domain.like.repository.LikeRepository;
import com.jhj.realworld.domain.member.Member;
import com.jhj.realworld.domain.member.Role;
import com.jhj.realworld.domain.member.repository.MemberRepository;
import com.jhj.realworld.domain.member.service.MemberService;
import com.jhj.realworld.domain.tag.Tag;
import com.jhj.realworld.domain.tag.repository.TagRepository;
import com.jhj.realworld.global.exception.NotExistMemberException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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

        //savedArticle.getArticleTags().forEach((articleTag -> System.out.println(articleTag.getTag().getContent())));
    }


    @Test
    void 팔로우한_사용자_feed_가져오기_좋아요도_기능확인하기() throws Exception{

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
    void 한사용자가_한게시글을_좋아요하고_게시글정보불러오기() throws Exception{
        Member login = memberRepository.findMemberByName("AAA").orElseThrow();

        String slug = "BBB2";
        articleService.favorite(slug, login.getName());

        ArticleDto dto = articleService.findBySlug(slug, login.getName());
        System.out.println(dto);
    }

    @Test
    void 스트림에서_맵핑을_할때_생기는_문제를_확인하자() throws Exception{
        //given
        Member login = memberRepository.findMemberByName("AAA").orElseThrow();

        String slug = "BBB2";
        //Article saved = articleRepository.findArticleBySlug(slug).orElseThrow();
        articleService.favorite(slug, login.getName());

        List<ArticleDto> feeds = articleService.findFeeds(login);
        feeds.forEach(System.out::println);
    }
}