package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.composite.ResComprehensiveInfoComposite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_res_comprehensive_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResComprehensiveInfoEntity {
    @EmbeddedId
    private ResComprehensiveInfoComposite composite;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("resPk")
    @JoinColumn(name = "res_pk",columnDefinition = "BIGINT UNSIGNED")
    private ReservationEntity reservationEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("hotelRoomPk")
    @JoinColumn(name = "hotel_room_pk",columnDefinition = "BIGINT UNSIGNED")
    private HotelRoomInfoEntity hotelRoomInfoEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @MapsId("resDogPk")
    @JoinColumn(name = "res_dog_pk",columnDefinition = "BIGINT UNSIGNED")
    private ResDogInfoEntity resDogInfoEntity;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
