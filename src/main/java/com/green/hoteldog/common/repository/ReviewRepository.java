package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ReservationEntity;
import com.green.hoteldog.common.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> , ReviewQDslRepository{

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<ReviewEntity> findAllByReservationEntityIn(List<ReservationEntity> reservationEntityList);
    List<ReviewEntity> findAllByReservationEntityInOrderByCreatedAtDesc(Set<ReservationEntity> reservationEntities);
    //재웅


}
