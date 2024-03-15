package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.HotelFavoritesEntity;
import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.composite.HotelFavoritesComposite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelFavoritesRepository extends JpaRepository<HotelFavoritesEntity, HotelFavoritesComposite> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    boolean existsByUserEntityAndHotelEntity(UserEntity userEntity, HotelEntity hotelEntity);
    List<HotelFavoritesEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);
    //재웅

}
