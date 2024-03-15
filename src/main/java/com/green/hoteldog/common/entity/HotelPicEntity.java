package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_hotel_pic")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelPicEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelPicPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "hotel_pk",referencedColumnName = "hotelPk",columnDefinition = "BIGINT UNSIGNED")
    private HotelEntity hotelEntity;

    @Column(columnDefinition = "VARCHAR(2100)", nullable = false)
    private String pic;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
