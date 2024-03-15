package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.composite.ReviewFavComposite;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_review_fav")
public class ReviewFavEntity {
    @EmbeddedId
    private ReviewFavComposite composite;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("reviewPk")
    @JoinColumn(name = "review_pk",columnDefinition = "BIGINT UNSIGNED")
    private ReviewEntity reviewEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("userPk")
    @JoinColumn(name = "user_pk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
    
}
