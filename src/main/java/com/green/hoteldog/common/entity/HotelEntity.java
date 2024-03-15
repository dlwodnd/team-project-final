package com.green.hoteldog.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.hoteldog.common.entity.base.BaseEntity;
import com.green.hoteldog.hotel.model.HotelOptionInfoVo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_hotel")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelPk;

    @OneToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "business_pk",referencedColumnName = "businessPk",columnDefinition = "BIGINT UNSIGNED")
    private BusinessEntity businessEntity;

    @Column(nullable = false)
    private String hotelNm;

    @Column(nullable = false)
    private String hotelDetailInfo;

    @Column(nullable = false,unique = true)
    private String businessNum;

    @Column(nullable = false)
    private String hotelCall;

    @Column(nullable = false)
    private String hotelFullAddress;


    @ColumnDefault("'0'")
    private Long advertise;


    @ColumnDefault("'0'")
    private Long approval;


    @ColumnDefault("'0'")
    private Long signStatus;

    private String cancelReason;

    @Column(nullable = false)
    private String hotelNum;

    private String refuseReason;

    @Column(nullable = false)
    private String businessCertificate;


    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<HotelPicEntity> hotelPicEntity = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private HotelWhereEntity hotelWhereEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<HotelOptionInfoEntity> hotelOptionInfoEntity = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<HotelRoomInfoEntity> hotelRoomInfoEntity = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<HotelFavoritesEntity> hotelFavoritesEntity = new HashSet<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private HotelAdvertiseEntity hotelAdvertiseEntity;

    @ToString.Exclude
    @OneToOne(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private HotelSuspendedEntity hotelSuspendedEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<PaymentAdEntity> paymentAdEntitySet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "hotelEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ReservationEntity> reservationEntitySet = new HashSet<>();



    //재웅

}
