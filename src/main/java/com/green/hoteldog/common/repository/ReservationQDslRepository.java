package com.green.hoteldog.common.repository;

import com.green.hoteldog.business_user.model.ReservationInfo;
import com.green.hoteldog.business_user.model.ReservationTodayInfo;
import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.ResPaymentEntity;
import com.green.hoteldog.common.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationQDslRepository {
    List<ResPaymentEntity> getResPaymentList(List<ReservationEntity> resPaymentEntityList);
    Page<ReservationTodayInfo> getReservationTodayInfoList2(Pageable pageable, List<ReservationEntity> reservationEntityList);
    List<ReservationEntity> getByHotelEntityNowBetweenFromToResList(HotelEntity hotelEntity);

    Page<ReservationInfo> getReservationInfoList(Pageable pageable, List<ReservationEntity> reservationEntityList);
}
