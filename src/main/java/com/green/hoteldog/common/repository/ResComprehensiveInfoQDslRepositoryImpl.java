package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ResComprehensiveInfoEntity;
import com.green.hoteldog.common.entity.ReservationEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.green.hoteldog.common.entity.QResComprehensiveInfoEntity.resComprehensiveInfoEntity;
import static com.green.hoteldog.common.entity.QReservationEntity.reservationEntity;

@RequiredArgsConstructor
public class ResComprehensiveInfoQDslRepositoryImpl implements ResComprehensiveInfoQDslRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<ResComprehensiveInfoEntity> getByReservationEntityIn(List<ReservationEntity> reservationEntityList) {

        return jpaQueryFactory.selectFrom(resComprehensiveInfoEntity)
                .join(resComprehensiveInfoEntity.reservationEntity, reservationEntity)
                .on(reservationEntity.in(reservationEntityList))
                .orderBy(reservationEntity.fromDate.desc())
                .fetch();
    }
}
