package site.moamoa.backend.service.component.command.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.KeywordConverter;
import site.moamoa.backend.service.module.redis.RedisModuleService;
import site.moamoa.backend.web.dto.response.KeywordResponseDTO;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordCommandServiceImpl implements KeywordCommandService{

    private RedisModuleService redisModuleService;

    //개인 최근 검색어 중 삭제
    @Override
    public KeywordResponseDTO.DeleteKeywordResult deleteRecentKeyword(Long memberId, String keyword) {
        redisModuleService.deleteKeywordByMemberRecent(memberId, keyword);

        return KeywordConverter.toDeleteKeywordResult(memberId, LocalDateTime.now());
    }
}
