package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_res_payment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResPaymentEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resPaymentPk;


    @OneToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "res_pk",referencedColumnName = "resPk",columnDefinition = "BIGINT UNSIGNED")
    private ReservationEntity reservationEntity;


    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk",referencedColumnName = "userPk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;


    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "business_pk",referencedColumnName = "businessPk",columnDefinition = "BIGINT UNSIGNED")
    private BusinessEntity businessEntity;

    @Column(nullable = false)
    private Long paymentStatus;

    @Column(nullable = false)
    private long paymentAmount;

    @Column(nullable = false)
    private String resPaymentNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
