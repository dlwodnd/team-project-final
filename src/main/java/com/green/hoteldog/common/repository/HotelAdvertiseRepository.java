package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelAdvertiseEntity;
import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.PaymentAdEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelAdvertiseRepository extends JpaRepository<HotelAdvertiseEntity, Long> {

    //승민
    //승민

//    @Modifying
//    @Query("UPDATE HotelAdvertiseEntity h set h.signStatus = :signStatus where h.hotelEntity.hotelPk = :hotelPk")
//    void updateHotelAdvertiseEntityBySignStatus(int signStatus, long hotelPk);
//
//    @Modifying
//    @Query("UPDATE HotelAdvertiseEntity h set h.signStatus = :signStatus ,h.cancelReason = :cancelReason where h.hotelEntity.hotelPk = :hotelPk")
//    void updateHotelAdvertiseEntityBySignStatusAndCancelReason(int signStatus, String cancelReason, long hotelPk);


    //승준
    //승준

    //영웅
    //영웅

    //재웅
    Optional<HotelAdvertiseEntity> findByHotelEntity(HotelEntity hotelEntity);

    //재웅

}
