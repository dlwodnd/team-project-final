package com.green.hoteldog.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "t_refund")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk",referencedColumnName = "userPk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;


    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "res_pk",referencedColumnName = "resPk",columnDefinition = "BIGINT UNSIGNED")
    private ReservationEntity reservationEntity;

    @Column(nullable = false)
    private String refundNum;


    @ColumnDefault("'0'")
    private Long refundStatus;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime refundDate;

    @Column(nullable = false)
    private long refundAmount;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅

}
