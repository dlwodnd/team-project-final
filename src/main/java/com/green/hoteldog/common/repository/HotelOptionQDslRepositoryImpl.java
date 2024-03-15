package com.green.hoteldog.common.repository;

import com.green.hoteldog.business_user.model.HotelOptionInfoVo;
import com.green.hoteldog.common.entity.HotelOptionEntity;
import com.green.hoteldog.common.entity.HotelOptionInfoEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.green.hoteldog.common.entity.QHotelOptionEntity.hotelOptionEntity;
import static com.green.hoteldog.common.entity.QHotelOptionInfoEntity.hotelOptionInfoEntity;

@RequiredArgsConstructor
public class HotelOptionQDslRepositoryImpl implements HotelOptionQDslRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<HotelOptionInfoVo> getHotelOptionInfoList(Set<HotelOptionInfoEntity> set) {
        JPAQuery<HotelOptionEntity> jpaQuery = queryFactory
                .selectFrom(hotelOptionEntity)
                .join(hotelOptionInfoEntity).fetchJoin()
                .where(hotelOptionInfoEntity.in(set))
                .where();

        List<HotelOptionInfoVo> hotelOptionInfoVoList = new ArrayList<>();
        for(HotelOptionEntity hotelOption : jpaQuery.fetch()){
                HotelOptionInfoVo vo = HotelOptionInfoVo.builder()
                        .optionPk(hotelOption.getOptionPk())
                        .optionNm(hotelOption.getOptionNm())
                        .build();
                hotelOptionInfoVoList.add(vo);
            }
        return hotelOptionInfoVoList;
    }
}
