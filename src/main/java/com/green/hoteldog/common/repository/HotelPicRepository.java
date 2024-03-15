package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.HotelPicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelPicRepository extends JpaRepository<HotelPicEntity, Long> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<HotelPicEntity> findHotelPicEntitiesByHotelEntity(HotelEntity hotelEntity);
    void deleteAllByHotelEntity(HotelEntity hotelEntity);

    Optional<HotelPicEntity> findByPic(String pic);

    //재웅

}
