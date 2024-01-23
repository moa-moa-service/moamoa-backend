package site.moamoa.backend.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.QPost;

import java.util.List;

@RequiredArgsConstructor
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllByNear(Double latitude, Double longitude) {
        QPost post = QPost.post;

        // latitude 를 radians 로 계산
        NumberExpression<Double> radiansLatitude =
                Expressions.numberTemplate(Double.class, "radians({0})", latitude);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
        NumberExpression<Double> cosSubwayLatitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", post.dealLocation.latitude);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
        NumberExpression<Double> sinSubWayLatitude =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))", post.dealLocation.latitude);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", post.dealLocation.longitude, longitude);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));

        // 최종 계산
        NumberExpression<Double> distanceExpression =
                Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        return jpaQueryFactory
                .selectFrom(post)
                .orderBy(distanceExpression.asc())
                .fetch();
    }

    @Override
    public List<Post> findAllByRanking(String town) {
        QPost post = QPost.post;

        BooleanExpression townCondition = town != null ? post.dealTown.eq(town) : null;

        return jpaQueryFactory
                .selectFrom(post)
                .where(townCondition)
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

        BooleanExpression keywordCondition = keyword != null ? post.productName.contains(keyword) : null;

        return jpaQueryFactory
                .selectFrom(post)
                .where(keywordCondition)
                .fetch();
    }

    @Override
    public Long updateView(Long postId) {
        QPost post = QPost.post;

        return jpaQueryFactory
                .update(post)
                .set(post.viewCount, post.viewCount.add(1))
                .where(post.id.eq(postId))
                .execute();
    }
}
