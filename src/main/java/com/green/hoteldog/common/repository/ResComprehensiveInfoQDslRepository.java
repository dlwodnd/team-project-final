package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ResComprehensiveInfoEntity;
import com.green.hoteldog.common.entity.ReservationEntity;

import java.util.List;

public interface ResComprehensiveInfoQDslRepository {
    List<ResComprehensiveInfoEntity> getByReservationEntityIn(List<ReservationEntity> reservationEntityList);
}
