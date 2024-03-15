package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.PaymentAdEntity;
import com.green.hoteldog.common.entity.ResPaymentEntity;
import com.green.hoteldog.common.entity.ReservationEntity;
import com.green.hoteldog.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResPaymentRepository extends JpaRepository<ResPaymentEntity, Long> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<ResPaymentEntity> findAllByUserEntityAndPaymentStatus(UserEntity userEntity, long paymentStatus);
    ResPaymentEntity findByReservationEntity(ReservationEntity reservationEntity);
    //재웅

}
