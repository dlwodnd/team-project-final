package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelOptionRepository extends JpaRepository<HotelOptionEntity, Long> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    HotelOptionEntity findHotelOptionEntitiesByOptionPk(Long optionPk);

    //재웅
}
