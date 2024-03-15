package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelResRoomEntity;
import com.green.hoteldog.common.entity.HotelRoomInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelResRoomRepository extends JpaRepository<HotelResRoomEntity, Long> , HotelResRoomQDslRepository{

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<HotelResRoomEntity> findAllByHotelRoomInfoEntity(HotelRoomInfoEntity hotelRoomInfoEntity);
    //재웅


}
