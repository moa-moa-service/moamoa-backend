package site.moamoa.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1533721353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final site.moamoa.backend.domain.common.QBaseEntity _super = new site.moamoa.backend.domain.common.QBaseEntity(this);

    public final NumberPath<Integer> available = createNumber("available", Integer.class);

    public final EnumPath<site.moamoa.backend.domain.enums.CapacityStatus> capacityStatus = createEnum("capacityStatus", site.moamoa.backend.domain.enums.CapacityStatus.class);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final site.moamoa.backend.domain.embedded.QAddress dealLocation;

    public final StringPath dealTown = createString("dealTown");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> personnel = createNumber("personnel", Integer.class);

    public final ListPath<site.moamoa.backend.domain.mapping.PostImage, site.moamoa.backend.domain.mapping.QPostImage> postImages = this.<site.moamoa.backend.domain.mapping.PostImage, site.moamoa.backend.domain.mapping.QPostImage>createList("postImages", site.moamoa.backend.domain.mapping.PostImage.class, site.moamoa.backend.domain.mapping.QPostImage.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.dealLocation = inits.isInitialized("dealLocation") ? new site.moamoa.backend.domain.embedded.QAddress(forProperty("dealLocation")) : null;
    }

}

