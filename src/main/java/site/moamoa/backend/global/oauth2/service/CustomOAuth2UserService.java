package site.moamoa.backend.global.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.global.oauth2.OAuthAttributes;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    @Value("${default.profileImage.url}")
    private String defaultImageUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");
        log.info("CustomOAuth2UserService.loadUser() userRequest : {} - OAuth2 로그인 요청 진입", userRequest.getAccessToken());

        /**
         * loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청 전송
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환
         */
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값

        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        // Naver OAuthAttributes 객체 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        Member createdUser = getUser(extractAttributes); // getUser() 메소드로 User 객체 생성 후 반환

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoleType().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getId(),
                createdUser.getRoleType()
        );
    }

    private Member getUser(OAuthAttributes attributes) {
        Member findUser = memberRepository.findBySocialId(attributes.getOauth2UserInfo().getId()).orElse(null);
        if (findUser == null) {
            return saveUser(attributes);
        }
        return findUser;
    }

    private Member saveUser(OAuthAttributes attributes) {
        Member createdUser = attributes.toEntity(attributes.getOauth2UserInfo());
        createdUser.addProfileImage(defaultImageUrl);
        return memberRepository.save(createdUser);
    }

    @Transactional
    public MemberResponseDTO.AddMemberInfoResult addMemberInfo(Long memberId, MemberRequestDTO.AddMemberInfo memberInfo) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        if (member.getNickname() != null) {
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXISTS);
        }
        member.addInfo(memberInfo.nickname(), memberInfo.town(), memberInfo.location());
        return MemberConverter.addMemberInfoResult(member);
    }

}
