package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.QReviewEntity;
import com.green.hoteldog.common.entity.ReviewEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.green.hoteldog.common.entity.QReviewEntity.reviewEntity;

@RequiredArgsConstructor
public class ReviewQDslRepositoryImpl implements ReviewQDslRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<ReviewEntity> findByHotelEntity(HotelEntity hotelEntity) {

        return jpaQueryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.reservationEntity.hotelEntity.eq(hotelEntity))
                .limit(2)
                .fetch();
    }
}
