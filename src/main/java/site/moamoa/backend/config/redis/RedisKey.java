package site.moamoa.backend.config.redis;

public class RedisKey {

    // Redis key
    public static final String POST_VIEW_KEY_PREFIX = "postView:";
    public static final String MEMBER_KEYWORD_KEY_PREFIX = "memberKeyword:";
    public static final String TOWN_KEYWORD_COUNT_KEY_PREFIX = "townKeywordCount:";

    // Redis Expire Time
    public static final Long EXPIRATION_VIEW_RECORD = 24 * 60 * 60L;  // 1 Day
}