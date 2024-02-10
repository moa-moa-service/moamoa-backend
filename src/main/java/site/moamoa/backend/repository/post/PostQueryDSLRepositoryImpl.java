package site.moamoa.backend.repository.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.*;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.QMemberPost;
import site.moamoa.backend.domain.mapping.QPostImage;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllByNear(Double latitude, Double longitude) {
        QPost post = QPost.post;

        // latitude 를 radians 로 계산
        NumberExpression<Double> radiansLatitude = Expressions.numberTemplate(Double.class, "radians({0})", latitude);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude = Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
        NumberExpression<Double> cosSubwayLatitude = Expressions.numberTemplate(Double.class, "cos(radians({0}))", post.dealLocation.latitude);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude = Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
        NumberExpression<Double> sinSubWayLatitude = Expressions.numberTemplate(Double.class, "sin(radians({0}))", post.dealLocation.latitude);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude = Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", post.dealLocation.longitude, longitude);

        NumberExpression<Double> acosExpression = Expressions.numberTemplate(Double.class, "acos({0})", cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));

        // 최종 계산
        NumberExpression<Double> distanceExpression = Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        return jpaQueryFactory
                .selectFrom(post)
                .orderBy(distanceExpression.asc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Post> findAllByRanking(String town) {
        QPost post = QPost.post;

        BooleanBuilder conditions = new BooleanBuilder();
        if (town != null) {
            addCondition(conditions, post.dealTown.eq(town));
        }

        return jpaQueryFactory
                .selectFrom(post)
                .where(conditions)
                .orderBy(post.available.asc(), post.viewCount.desc())
                .fetch();
    }

    @Override
    public List<Post> findAllByRecent() {
        QPost post = QPost.post;

        return jpaQueryFactory
                .selectFrom(post)
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Post> findAllByKeyword(String keyword) {
        QPost post = QPost.post;

        BooleanBuilder conditions = new BooleanBuilder();
        if (keyword != null) {
            addCondition(conditions, post.productName.contains(keyword));
        }

        return jpaQueryFactory
                .selectFrom(post)
                .where(conditions)
                .fetch();
    }

    @Override
    public List<Post> findAllByKeywordAndCondition(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice) {
        QPost post = QPost.post;

        BooleanBuilder conditions = new BooleanBuilder();

        if (keyword != null) {
            addCondition(conditions, post.productName.contains(keyword));
        }
        if (categoryId != null) {
            addCondition(conditions, post.category.id.eq(categoryId));
        }
        if (dDay != null) {
            addCondition(conditions, post.deadline.between(LocalDateTime.now(), LocalDate.now().plusDays(dDay).atStartOfDay()));
        }
        if (total != null) {
            addCondition(conditions, post.personnel.goe(total));
        }

        NumberExpression<Integer> priceForOne = post.totalPrice.divide(post.personnel);
        if (minPrice != null) {
            addCondition(conditions, priceForOne.goe(minPrice));
        }
        if (maxPrice != null) {
            addCondition(conditions, priceForOne.loe(maxPrice));
        }

        return jpaQueryFactory
                .selectFrom(post)
                .where(conditions)
                .fetch();
    }

    private void addCondition(BooleanBuilder builder, BooleanExpression condition) {
        builder.and(condition);
    }
}
