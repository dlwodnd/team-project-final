package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_hotel_where")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelWhereEntity {
    @Id
    @Column(name = "hotel_pk")
    private Long id;

    @MapsId
    @OneToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_pk",columnDefinition = "BIGINT UNSIGNED")
    private HotelEntity hotelEntity;
    @Column(nullable = false)
    private String addressName;

    @Column(name = "region_1depth_name",nullable = false)
    private String region1DepthName;

    @Column(name = "region_2depth_name",nullable = false)
    private String region2DepthName;

    @Column(name = "region_3depth_name",nullable = false)
    private String region3DepthName;

    @Column(nullable = false)
    private String zoneNum;

    @Column(name = "x_coordinate",nullable = false)
    private String x;

    @Column(name = "y_coordinate",nullable = false)
    private String y;

    @Column(nullable = false)
    private String detailAddress;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅

}
