package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ReviewEntity;
import com.green.hoteldog.common.entity.ReviewPicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPicRepository extends JpaRepository<ReviewPicEntity, Long>{

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    List<ReviewPicEntity> findAllByReviewEntity(ReviewEntity reviewEntity);
    //재웅


}
