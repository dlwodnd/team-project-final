package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ResComprehensiveInfoEntity;
import com.green.hoteldog.common.entity.ReservationEntity;
import com.green.hoteldog.common.entity.composite.ResComprehensiveInfoComposite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResComprehensiveInfoRepository extends JpaRepository<ResComprehensiveInfoEntity, ResComprehensiveInfoComposite> , ResComprehensiveInfoQDslRepository{

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<ResComprehensiveInfoEntity> findAllByReservationEntityIn(List<ReservationEntity> reservationEntityList);
    List<ResComprehensiveInfoEntity> findAllByReservationEntity(ReservationEntity reservationEntity);
    ResComprehensiveInfoEntity findByReservationEntity(ReservationEntity reservationEntity);

    //재웅


}
