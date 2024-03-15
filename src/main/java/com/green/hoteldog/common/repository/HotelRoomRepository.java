package com.green.hoteldog.common.repository;

import com.green.hoteldog.business_user.model.HotelRoomInfo;
import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.HotelRoomInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRoomRepository extends JpaRepository<HotelRoomInfoEntity, Long> {
    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<HotelRoomInfoEntity> findHotelRoomInfoEntitiesByHotelEntity(HotelEntity hotelEntity);
    List<HotelRoomInfoEntity> findAllByHotelEntityOrderByHotelResRoomEntitiesAsc(HotelEntity hotelEntity);
    List<HotelRoomInfoEntity> findByHotelEntity(HotelEntity hotelEntity);
    //재웅

}
