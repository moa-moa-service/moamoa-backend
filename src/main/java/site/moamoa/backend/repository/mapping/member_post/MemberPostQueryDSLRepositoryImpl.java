package site.moamoa.backend.repository.mapping.member_post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.domain.*;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.QMemberPost;
import site.moamoa.backend.domain.mapping.QPostImage;

import java.util.List;

@RequiredArgsConstructor
public class MemberPostQueryDSLRepositoryImpl implements MemberPostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member findPostAdminByPostId(Long postId) {
        QMemberPost memberPost = QMemberPost.memberPost;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberPost.post.id.eq(postId));
        addCondition(conditions, memberPost.isAuthorStatus.eq(IsAuthorStatus.AUTHOR));

        return jpaQueryFactory
                .select(memberPost.member)
                .from(memberPost)
                .where(conditions)
                .fetchOne();
    }

    @Override
    public List<Post> findPostsByRecruitingAndParticipating(Long memberId,
                                                            IsAuthorStatus isAuthorStatus,
                                                            CapacityStatus capacityStatus) {
        QMemberPost memberPost = QMemberPost.memberPost;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberPost.member.id.eq(memberId));
        addCondition(conditions, memberPost.isAuthorStatus.eq(isAuthorStatus));
        addCondition(conditions, memberPost.post.capacityStatus.eq(capacityStatus));

        return jpaQueryFactory
                .select(memberPost.post)
                .from(memberPost)
                .where(conditions)
                .fetch();
    }

    @Override
    public MemberPost findMemberPostAdminByPostId(Long postId) {
        QPost post = QPost.post;
        QMember member = QMember.member;
        QMemberPost memberPost = QMemberPost.memberPost;
        QCategory category = QCategory.category;
        QPostImage postImage = QPostImage.postImage;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberPost.post.id.eq(postId));
        addCondition(conditions, memberPost.isAuthorStatus.eq(IsAuthorStatus.AUTHOR));

        return jpaQueryFactory
                .selectFrom(memberPost)
                .innerJoin(memberPost.post, post).fetchJoin()
                .innerJoin(post.category, category).fetchJoin()
                .leftJoin(post.postImages, postImage).fetchJoin()
                .innerJoin(memberPost.member, member).fetchJoin()
                .where(conditions)
                .fetchOne();
    }

    @Override
    public List<Member> findParticipatingMembersByPostId(Long postId){
        QMemberPost memberPost = QMemberPost.memberPost;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberPost.post.id.eq(postId));
        addCondition(conditions, memberPost.isAuthorStatus.eq(IsAuthorStatus.PARTICIPATOR));

        return jpaQueryFactory
                .select(memberPost.member)
                .from(memberPost)
                .where(conditions)
                .fetch();
    }

    @Override
    public List<Member> findMembersByPostIdExcludingMember(Long postId, Long memberId) {
        QMemberPost memberPost = QMemberPost.memberPost;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberPost.post.id.eq(postId));
        addCondition(conditions, memberPost.member.id.ne(memberId)); // 매개변수의 memberId를 제외

        return jpaQueryFactory
            .select(memberPost.member)
            .from(memberPost)
            .where(conditions)
            .fetch();
    }

    private void addCondition(BooleanBuilder builder, BooleanExpression condition) {
        builder.and(condition);
    }
}
