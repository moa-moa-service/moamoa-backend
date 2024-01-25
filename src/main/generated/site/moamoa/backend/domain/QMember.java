package site.moamoa.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 637122243L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final site.moamoa.backend.domain.common.QBaseEntity _super = new site.moamoa.backend.domain.common.QBaseEntity(this);

    public final site.moamoa.backend.domain.embedded.QAddress address;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<site.moamoa.backend.domain.enums.DeletionStatus> deletionStatus = createEnum("deletionStatus", site.moamoa.backend.domain.enums.DeletionStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImage = createString("profileImage");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<site.moamoa.backend.domain.enums.RoleType> roleType = createEnum("roleType", site.moamoa.backend.domain.enums.RoleType.class);

    public final StringPath socialId = createString("socialId");

    public final StringPath town = createString("town");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new site.moamoa.backend.domain.embedded.QAddress(forProperty("address")) : null;
    }

}

