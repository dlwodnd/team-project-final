package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.ReviewEntity;

import java.util.List;

public interface ReviewQDslRepository {
    List<ReviewEntity> findByHotelEntity(HotelEntity hotelEntity);
}
