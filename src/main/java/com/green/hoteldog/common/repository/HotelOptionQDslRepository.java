package com.green.hoteldog.common.repository;

import com.green.hoteldog.business_user.model.HotelOptionInfoVo;
import com.green.hoteldog.common.entity.HotelOptionInfoEntity;


import java.util.List;
import java.util.Set;

public interface HotelOptionQDslRepository {
    List<HotelOptionInfoVo> getHotelOptionInfoList(Set<HotelOptionInfoEntity> set);

}
