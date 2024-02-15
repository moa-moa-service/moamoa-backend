package site.moamoa.backend.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPost is a Querydsl query type for MemberPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPost extends EntityPathBase<MemberPost> {

    private static final long serialVersionUID = 2030905891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPost memberPost = new QMemberPost("memberPost");

    public final site.moamoa.backend.domain.common.QBaseEntity _super = new site.moamoa.backend.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<site.moamoa.backend.domain.enums.IsAuthorStatus> isAuthorStatus = createEnum("isAuthorStatus", site.moamoa.backend.domain.enums.IsAuthorStatus.class);

    public final site.moamoa.backend.domain.QMember member;

    public final site.moamoa.backend.domain.QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberPost(String variable) {
        this(MemberPost.class, forVariable(variable), INITS);
    }

    public QMemberPost(Path<? extends MemberPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPost(PathMetadata metadata, PathInits inits) {
        this(MemberPost.class, metadata, inits);
    }

    public QMemberPost(Class<? extends MemberPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new site.moamoa.backend.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.post = inits.isInitialized("post") ? new site.moamoa.backend.domain.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

