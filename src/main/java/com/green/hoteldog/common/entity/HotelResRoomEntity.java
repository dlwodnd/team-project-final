package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "t_hotel_res_room")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResRoomEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelRoomByDatePk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_room_pk",referencedColumnName = "hotelRoomPk",columnDefinition = "BIGINT UNSIGNED")
    private HotelRoomInfoEntity hotelRoomInfoEntity;

    @Column(nullable = false,columnDefinition = "BIGINT UNSIGNED")
    private Long hotelLeftEa;

    @Column(nullable = false)
    private LocalDate roomDate;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
