package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.*;
import com.green.hoteldog.hotel.model.DogSizeEa;
import com.green.hoteldog.hotel.model.HotelListSelDto;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.green.hoteldog.common.entity.QHotelEntity.hotelEntity;
import static com.green.hoteldog.common.entity.QHotelOptionInfoEntity.hotelOptionInfoEntity;
import static com.green.hoteldog.common.entity.QHotelResRoomEntity.hotelResRoomEntity;
import static com.green.hoteldog.common.entity.QHotelRoomInfoEntity.hotelRoomInfoEntity;

@RequiredArgsConstructor
@Slf4j
public class HotelQDslRepositoryImpl implements HotelQDslRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HotelEntity> getHotelAdvertiseList() {

        return jpaQueryFactory.selectFrom(hotelEntity)
                .where(hotelEntity.advertise.eq(1L),hotelEntity.approval.eq(1L))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<HotelEntity> getHotelFilterList(HotelListSelDto dto) {
        Pageable pageable = dto.getPageable();
        if(dto.getSearch() != null && !dto.getSearch().isEmpty()){
            return jpaQueryFactory.selectFrom(hotelEntity)
                    .where(containsHotelNm(dto.getSearch()))
                    .fetch();
        }
        List<Long> hotelPkList = new ArrayList<>();
        List<HotelEntity> hotelEntityByOptionList = new ArrayList<>();
        if(dto.getHotelOptionPk() != null && !dto.getHotelOptionPk().isEmpty()) {
            hotelPkList.addAll(jpaQueryFactory.select(hotelEntity.hotelPk)
                    .from(hotelOptionInfoEntity)
                    .where(hotelOptionInfoEntity.hotelOptionEntity.optionPk.in(dto.getHotelOptionPk()))
                    .groupBy(hotelOptionInfoEntity.hotelEntity)
                    .having(hotelOptionInfoEntity.hotelEntity.count().eq((long) dto.getHotelOptionPk().size()))
                    .fetch());
        }
        else {
            hotelPkList.addAll(jpaQueryFactory.select(hotelEntity.hotelPk)
                    .from(hotelEntity)
                    .fetch());
        }
        log.info("hotelPkList : {}",hotelPkList);

        List<Long> hotelRoomInfoEntityList = new ArrayList<>();
        if(dto.getDogInfo() != null && !dto.getDogInfo().isEmpty()){
            for (DogSizeEa dogInfo : dto.getDogInfo()) {
                hotelRoomInfoEntityList.addAll(jpaQueryFactory.select(hotelRoomInfoEntity.hotelRoomPk)
                                .from(hotelRoomInfoEntity)
                                .join(hotelResRoomEntity).on(hotelRoomInfoEntity.eq(hotelResRoomEntity.hotelRoomInfoEntity))
                                .where(hotelRoomInfoEntity.dogSizeEntity.sizePk.eq(dogInfo.getDogSize()),
                                        hotelResRoomEntity.roomDate.between(dto.getFrom(),dto.getTo()),
                                        hotelResRoomEntity.hotelLeftEa.goe(dogInfo.getDogCount()))
                                .groupBy(hotelRoomInfoEntity)
                                .having(hotelRoomInfoEntity.count().goe(dto.getDate().size()))
                                .fetch());
            }
        }else {
            hotelRoomInfoEntityList.addAll( jpaQueryFactory.select(hotelRoomInfoEntity.hotelRoomPk)
                    .from(hotelRoomInfoEntity)
                    .join(hotelResRoomEntity).on(hotelRoomInfoEntity.eq(hotelResRoomEntity.hotelRoomInfoEntity))
                    .where(hotelResRoomEntity.roomDate.between(dto.getFrom(),dto.getTo()),
                            hotelResRoomEntity.hotelLeftEa.goe(0))
                    .groupBy(hotelRoomInfoEntity)
                    .having(hotelRoomInfoEntity.count().goe(dto.getDate().size()))
                    .fetch());
        }
        log.info("hotelRoomInfoEntityList : {}",hotelRoomInfoEntityList);





        List<HotelEntity> hotelEntityList = jpaQueryFactory.selectFrom(hotelEntity)
                /*.join(hotelRoomInfoEntity).on(hotelEntity.hotelPk.eq(hotelRoomInfoEntity.hotelEntity.hotelPk))*/
                .where(hotelEntity.approval.eq(1L),
                        inHotelPkByOption(hotelPkList),
                        /*inHotelRoomPk(hotelRoomInfoEntityList),*/
                        hotelEntity.hotelRoomInfoEntity.any().hotelRoomPk.in(hotelRoomInfoEntityList),
                        likeHotelAddress(dto.getAddress()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(hotelEntity.hotelPk.desc())
                .fetch();
        log.info("hotelEntityList : {}",hotelEntityList.size());

        return hotelEntityList;
    }

    private BooleanExpression inDogSize(List<DogSizeEntity> dogSizeEntityList){
        if (dogSizeEntityList.isEmpty()){
            return null;
        }
        return hotelRoomInfoEntity.dogSizeEntity.in(dogSizeEntityList);
    }
    private BooleanExpression inHotelOption(List<HotelOptionEntity> hotelOptionEntityList){
        if (hotelOptionEntityList.isEmpty()){
            return null;
        }
        return hotelOptionInfoEntity.hotelOptionEntity.in(hotelOptionEntityList);
    }
    private BooleanExpression inHotelRoomPk(List<Long> hotelRoomPkList) {
        if (hotelRoomPkList == null ||hotelRoomPkList.isEmpty()) {
            return null;
        }
        return hotelRoomInfoEntity.hotelRoomPk.in(hotelRoomPkList);
    }
    private BooleanExpression containsHotelNm(String hotelNm){
        if (hotelNm.isEmpty()){
            return null;
        }
        return hotelEntity.hotelNm.contains(hotelNm);
    }
    private BooleanExpression likeHotelAddress(String hotelAddress){
        if (hotelAddress == null || hotelAddress.isEmpty()){
            return null;
        }
        return hotelEntity.hotelFullAddress.like(hotelAddress);
    }
    private BooleanExpression inHotelPkByOption(List<Long> hotelPkList){
        if (hotelPkList == null || hotelPkList.isEmpty()){
            return null;
        }
        return hotelEntity.hotelPk.in(hotelPkList);
    }
}
