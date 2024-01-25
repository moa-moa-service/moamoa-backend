package site.moamoa.backend.global.oauth2;

import lombok.Builder;
import lombok.Getter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.DeletionStatus;
import site.moamoa.backend.domain.enums.RoleType;
import site.moamoa.backend.global.oauth2.userinfo.NaverOAuth2UserInfo;
import site.moamoa.backend.global.oauth2.userinfo.OAuth2UserInfo;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값
    private final OAuth2UserInfo oauth2UserInfo; //로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * Naver에서 넘겨주는 OAuthAttributes 객체 반환
     */
    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 주입된 상태
     * 우리가 사용하는 Member로 앤타타에 맞게 변환
     */
    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .socialId(oauth2UserInfo.getId())
                .roleType(RoleType.GUEST)
                .deletionStatus(DeletionStatus.NOT_DELETE)
                .build();
    }
}
