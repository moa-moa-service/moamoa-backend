package site.moamoa.backend.repository.mapping.member_post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.*;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.QMemberPost;
import site.moamoa.backend.domain.mapping.QPostImage;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class MemberPostQueryDSLRepositoryImpl implements MemberPostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PostResponseDTO.GetPost fetchDetailedPostByPostId(Long memberId, Long postId) {
        QMemberPost memberPost = QMemberPost.memberPost;

        MemberPost fetchedMemberPost = findPostAdminByPostId(postId);
        Member fetchedMember = fetchedMemberPost.getMember();
        Post fetchedPost = fetchedMemberPost.getPost();
        List<Notice> noticeList;

        MemberPost findMemberPostRecruiting = jpaQueryFactory
                .selectFrom(memberPost)
                .where(memberPost.post.id.eq(postId), memberPost.member.id.eq(memberId))
                .fetchOne();

        if (findMemberPostRecruiting != null) {
            noticeList = fetchedMemberPost.getPost().getNoticeList();
        } else {
            noticeList = Collections.emptyList();
        }

        return PostConverter.toGetPost(
                PostConverter.toPostDTO(fetchedPost, fetchedPost.getPostImages(), fetchedPost.getCategory()),
                MemberConverter.toMemberDTO(fetchedMember),
                NoticeConverter.toSimpleNoticeDtoList(noticeList)
        );
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

    private MemberPost findPostAdminByPostId(Long postId) {
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

    private void addCondition(BooleanBuilder builder, BooleanExpression condition) {
        builder.and(condition);
    }
}
