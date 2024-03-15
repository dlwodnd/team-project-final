package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "t_hotel_advertise")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelAdvertiseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelAdvertisePk;

    @OneToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_pk",referencedColumnName = "hotelPk",columnDefinition = "BIGINT UNSIGNED")
    private HotelEntity hotelEntity;

    @Column(nullable = false)
    private LocalDateTime hotelAdvertiseToDate;

    @Column(nullable = false)
    private LocalDateTime hotelAdvertiseEndDate;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private Long paymentStatus;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private Long adStatus;

    @Column(nullable = false)
    private String hotelAdvertiseNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToMany(mappedBy = "hotelAdvertiseEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<PaymentAdEntity> paymentAdEntityList;
    //재웅
}
