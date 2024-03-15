package com.green.hoteldog.common.repository;

import com.green.hoteldog.hotel.model.HotelDetailInfoDto;
import com.green.hoteldog.hotel.model.HotelRoomInfoVo;
import com.green.hoteldog.hotel.model.QHotelRoomInfoVo;
import com.green.hoteldog.user.models.HotelRoomDateProcDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.green.hoteldog.common.entity.QHotelResRoomEntity.hotelResRoomEntity;


import java.util.List;

@RequiredArgsConstructor
public class HotelResRoomQDslRepositoryImpl implements HotelResRoomQDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateAllHotelResRoomRefundCount(List<HotelRoomDateProcDto> hotelRoomDateProcDtoList) {
        for (HotelRoomDateProcDto hotelRoomDateProcDto : hotelRoomDateProcDtoList) {
            jpaQueryFactory
                    .update(hotelResRoomEntity)
                    .set(hotelResRoomEntity.hotelLeftEa, hotelResRoomEntity.hotelLeftEa.add(1))
                    .where(hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomPk.eq(hotelRoomDateProcDto.getHotelRoomInfoEntity().getHotelRoomPk())
                            , hotelResRoomEntity.roomDate.between(hotelRoomDateProcDto.getFromDate(), hotelRoomDateProcDto.getToDate()))
                    .execute();
        }

    }

    @Override
    public void updateHotelResRoomRefundCount(HotelRoomDateProcDto hotelRoomDateProcDto) {
        jpaQueryFactory
                .update(hotelResRoomEntity)
                .set(hotelResRoomEntity.hotelLeftEa, hotelResRoomEntity.hotelLeftEa.add(1))
                .where(hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomPk.eq(hotelRoomDateProcDto.getHotelRoomInfoEntity().getHotelRoomPk())
                        , hotelResRoomEntity.roomDate.between(hotelRoomDateProcDto.getFromDate(), hotelRoomDateProcDto.getToDate()))
                .execute();

    }

    @Override
    public void updateHotelResRoomReservationCount(HotelRoomDateProcDto hotelRoomDateProcDto) {
        jpaQueryFactory
                .update(hotelResRoomEntity)
                .set(hotelResRoomEntity.hotelLeftEa, hotelResRoomEntity.hotelLeftEa.subtract(1))
                .where(hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomPk.eq(hotelRoomDateProcDto.getHotelRoomInfoEntity().getHotelRoomPk())
                        , hotelResRoomEntity.roomDate.between(hotelRoomDateProcDto.getFromDate(), hotelRoomDateProcDto.getToDate()))
                .execute();
    }

    @Override
    public List<HotelRoomInfoVo> findResAbleHotelRoomInfo(long dogCount, HotelDetailInfoDto dto){
        List<HotelRoomInfoVo> hotelRoomInfoVoList = jpaQueryFactory
                .select(new QHotelRoomInfoVo(
                        hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomPk
                        , hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomNm
                        , hotelResRoomEntity.hotelLeftEa
                        , hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomCost
                        , hotelResRoomEntity.hotelRoomInfoEntity.roomPic
                        , hotelResRoomEntity.hotelRoomInfoEntity.maximum
                        , new CaseBuilder()
                        .when(hotelResRoomEntity.hotelRoomInfoEntity.dogSizeEntity.sizePk.notIn(dto.getDogSizePkList()))
                        .then(0L)
                        .when(hotelResRoomEntity.hotelLeftEa.loe(0))
                        .then(0L)
                        .when(hotelResRoomEntity.hotelRoomInfoEntity.roomAble.eq(0L))
                        .then(0L)
                        .when(hotelResRoomEntity.hotelRoomInfoEntity.hotelEntity.approval.notIn(1L))
                        .then(0L)
                        .otherwise(1L).as("roomAble")
                ))
                .from(hotelResRoomEntity)
                .where(hotelResRoomEntity.roomDate.between(dto.getStartDate(), dto.getEndDate().plusDays(1))
                        ,hotelResRoomEntity.hotelRoomInfoEntity.hotelEntity.hotelPk.eq(dto.getHotelPk()))
                .groupBy(hotelResRoomEntity.hotelRoomInfoEntity.hotelRoomPk)
                .orderBy(hotelResRoomEntity.hotelLeftEa.asc())
                .fetch();
        return hotelRoomInfoVoList;
    }


}
