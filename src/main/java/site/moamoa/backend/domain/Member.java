package site.moamoa.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.DeletionStatus;
import site.moamoa.backend.domain.enums.RoleType;

/**
 * @SQLRestriction -> 해당 엔티티를 조회할 때 마다
 * where절에 deletion_status = 'NOT_DELETE' 구문이 들어가게 된다.
 * 탈퇴한 회원은 조회가 안되게 막을 수 있지만 모든 쿼리에 해당 구문이 붙으므로 성능이 저하될 가능성이 있음
 * 해당 구문을 쓰거나 or 조회 쿼리에 jpql로 조건을 붙여주거나
 */

@Entity
@Getter
@Builder
//@SQLRestriction("deletion_status = 'NOT_DELETE'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void addProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void deactivate() {
        this.deletionStatus = DeletionStatus.DELETE;  // 계정 탈퇴하면 deletionStatus를 DELETE로 설정.
    }
}
