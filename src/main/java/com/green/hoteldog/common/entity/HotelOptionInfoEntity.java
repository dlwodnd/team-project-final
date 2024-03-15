package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.composite.HotelOptionComposite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_hotel_option")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelOptionInfoEntity {
    @EmbeddedId
    private HotelOptionComposite composite;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("hotelPk")
    @JoinColumn(name = "hotel_pk",columnDefinition = "BIGINT UNSIGNED")
    private HotelEntity hotelEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("optionPk")
    @JoinColumn(name = "option_pk",columnDefinition = "BIGINT UNSIGNED")
    private HotelOptionEntity hotelOptionEntity;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
