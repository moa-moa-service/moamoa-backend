package site.moamoa.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.DeletionStatus;
import site.moamoa.backend.domain.enums.RoleType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname; //닉네임

    private String profileImage; // 프로필 이미지

    @Embedded
    private Address address; // 거주 주소

    private String town; // 거주 동네

    @Enumerated(EnumType.STRING)
    private DeletionStatus deletionStatus;  //회원 정보 삭제 여부

    @Enumerated(EnumType.STRING)
    private RoleType roleType;   //유저 권한

    private String refreshToken; //리프레시 토큰

    private String socialId;

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void addInfo(String nickname, Address address) {
        this.nickname = nickname;
        this.address = address;
        this.roleType = RoleType.MEMBER;
    }

    public void addRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
