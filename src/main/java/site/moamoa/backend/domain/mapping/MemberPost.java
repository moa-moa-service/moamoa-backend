package site.moamoa.backend.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.enums.IsAuthorStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private IsAuthorStatus isAuthorStatus; //글쓴이 여부

    // Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}