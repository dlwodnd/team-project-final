package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelSuspendedEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HotelSuspendedRepository extends JpaRepository<HotelSuspendedEntity, Long> {
    //승민
    //승민
    // 호텔 중지 신청 승인
    @Modifying
    @Query("UPDATE HotelEntity h set h.signStatus = :signStatus ,h.approval=:approval where h.hotelPk = :hotelPk")
    void updateHotelSignStatus1(@Param("signStatus") Long signStatus,@Param("approval") Long approval, @Param("hotelPk") Long hotelPk);


    @Modifying
    @Query("UPDATE HotelEntity h set h.signStatus = :signStatus  where h.hotelPk = :hotelPk")
    void updateHotelSignStatus(@Param("signStatus") Long signStatus, @Param("hotelPk") Long hotelPk);


    // 호텔 중지 신청 거절
    @Modifying
    @Query("UPDATE HotelSuspendedEntity h set h.suspendedReason = :suspendedReason where h.hotelEntity.hotelPk = :hotelPk")
    void updateHotelSuspendedEntityBySuspendedReason(@Param("suspendedReason") String suspendedReason, @Param("hotelPk") Long hotelPk);



//     호텔 중지 신청 거절
//    @Modifying
//    @Query("UPDATE HotelSuspendedEntity h set h.hotelEntity.signStatus = :signStatus , h.suspendedReason = :suspendedReason where h.hotelEntity.hotelPk = :hotelPk")
//    void updateHotelSuspendedEntityBySignStatusAndSuspendedReason(@Param("signStatus") Long signStatus, String suspendedReason, @Param("hotelPk") Long hotelPk);


    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅

}
