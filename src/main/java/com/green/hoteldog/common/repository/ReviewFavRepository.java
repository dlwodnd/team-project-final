package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ReviewEntity;
import com.green.hoteldog.common.entity.ReviewFavEntity;
import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.composite.ReviewFavComposite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFavRepository extends JpaRepository<ReviewFavEntity, ReviewFavComposite> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<ReviewFavEntity> findAllByReviewEntity(ReviewEntity reviewPk);
    ReviewFavEntity findByUserEntityAndReviewEntity(UserEntity userEntity, ReviewEntity reviewEntity);
    //재웅

}
