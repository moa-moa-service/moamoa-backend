package site.moamoa.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Post extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private LocalDateTime deadline;

    private Integer personnel; //총 모집 인원

    private Integer available; //모집 가능 인원

    private String productName; //상품명

    @Embedded
    private Address dealLocation; // 상품 거래 장소(상세 주소) ex. 동작구 상도동 CU

    private String dealTown; // 상품 거래 동네 ex. 상도동

    private Integer totalPrice; //상품 총 가격

    private String description; //설명

    @Enumerated(EnumType.STRING)
    private CapacityStatus capacityStatus; // 모집 상태

    @Column(columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private Integer viewCount;  // 조회수

    // Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Notice> noticeList = new ArrayList<>();

    public void updateInfo(UpdatePostInfo request, Category category, List<PostImage> images) {
        this.category = Optional.ofNullable(category).orElse(this.category);
        this.personnel = Optional.ofNullable(request.personnel()).orElse(this.personnel);
        this.deadline = Optional.ofNullable(request.deadline()).orElse(this.deadline);
        this.postImages = images;
        this.dealLocation = Optional.ofNullable(request.dealLocation()).orElse(this.dealLocation);
        this.productName = Optional.ofNullable(request.productName()).orElse(this.productName);
        this.totalPrice = Optional.ofNullable(request.price()).orElse(this.totalPrice);
        this.description = Optional.ofNullable(request.description()).orElse(this.description);
    }

    public void updateStatus() {
        if (this.capacityStatus == CapacityStatus.NOT_FULL)
            this.capacityStatus = CapacityStatus.FULL;
        else if (this.capacityStatus == CapacityStatus.FULL)
            this.capacityStatus = CapacityStatus.NOT_FULL;
    }

    public void updateViewCount() {
        this.viewCount++;
    }
}
