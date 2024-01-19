package site.moamoa.backend.domain;

import jakarta.persistence.*;
import java.util.ArrayList;

import lombok.*;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.PostImage;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    // Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();
  
}
