package site.moamoa.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String imageUrl;

    @Column(nullable = false)
    private String content;

    // Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder.Default
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public void setPost(Post post) {
        this.post = post;
    }
    public void updateNotice(NoticeRequestDTO.UpdateNotice updateNotice) {
        this.title = updateNotice.title();
        this.content = updateNotice.content();
    }
    public void changeImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
