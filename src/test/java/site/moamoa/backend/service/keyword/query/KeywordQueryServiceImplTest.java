package site.moamoa.backend.service.keyword.query;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.service.member.command.MemberCommandServiceImpl;
import site.moamoa.backend.service.post.query.PostQueryServiceImpl;
import site.moamoa.backend.web.dto.base.KeywordDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class KeywordQueryServiceImplTest {
    @Autowired KeywordQueryServiceImpl keywordService;
    @Autowired PostQueryServiceImpl postService;
    @Autowired MemberCommandServiceImpl memberService;


    @BeforeEach
    public void beforeEach() throws InterruptedException {
        Member member = Member.builder().
                nickname("Kelly").
                town("상도동").
                build();

        //상품8 5번
        //상품5 2번
        //나머지 1번
        memberService.add(member);
        postService.findByKeyword(1L, "상품");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품2");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품3");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품4");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품5");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품5");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품6");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품7");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품8");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품8");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품8");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품8");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품8");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품9");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품10");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품11");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품12");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품13");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품14");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품15");
        Thread.sleep(1000);
        postService.findByKeyword(1L, "상품16");
        Thread.sleep(1000);
    }
    
    @Test
    @Rollback(false)
    public void townKeywordTest() throws Exception{
        //given

        //when
        List<KeywordDTO> keywordDTOS = keywordService.popularSearchRankList("상도동");
        for (KeywordDTO keyword : keywordDTOS) {
            log.info("keyword : " + keyword);
        }
        //then
    }

    @Test
    @Rollback(false)
    public void RankingTest() throws Exception{
        //given

        //when
        List<KeywordDTO> keywordDTOS = keywordService.recentSearchRankList(1L);
        for (KeywordDTO keyword : keywordDTOS) {
            log.info("keyword : " + keyword);
        }
        //then
    }



}