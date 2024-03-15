package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.BusinessEntity;
import com.green.hoteldog.common.entity.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> , HotelQDslRepository{


    //승민
    //승민
    //전체 호텔 모록 가져옴
    Page<HotelEntity>  findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<HotelEntity> findAllByApprovalIsOrderByCreatedAtDesc(long Approval, Pageable pageable);

    @Modifying
    @Query("UPDATE HotelEntity h SET h.approval = :approval WHERE h.hotelPk = :hotelPk")
    void updateHotelEntityByApproval(int approval, long hotelPk);

    //승준
    //승준

    //영웅
    HotelEntity findByHotelPk(long hotelPk);
    //영웅

    //재웅
    Optional<HotelEntity> findHotelEntityByHotelPk(long hotelPk);
    Optional<HotelEntity> findHotelEntityByBusinessEntity(BusinessEntity businessEntity);
    List<HotelEntity> findAllByAdvertiseAndApproval(Long advertise, Long approval);
    Optional<HotelEntity> findByBusinessNum(String businessNum);
    List<HotelEntity> findAllByApprovalIsNotOrderByCreatedAtDesc(long approval, Pageable pageable);
    //재웅
}
