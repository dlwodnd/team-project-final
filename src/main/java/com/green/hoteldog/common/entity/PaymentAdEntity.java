package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.CreatedAtBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_payment_ad")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAdEntity extends CreatedAtBaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentPk;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_pk",referencedColumnName = "hotelPk",columnDefinition = "BIGINT UNSIGNED")
    private HotelEntity hotelEntity;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_advertise_pk",referencedColumnName = "hotelAdvertisePk",columnDefinition = "BIGINT UNSIGNED")
    private HotelAdvertiseEntity hotelAdvertiseEntity;


    @ColumnDefault("'0'")
    private Long paymentStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Long paymentAmount;

    @Column(nullable = false)
    private String paymentAdNum;

    @Column(nullable = false)
    private String cardNum;

    @Column(nullable = false)
    private LocalDate cardValidThru;

    @Column(nullable = false)
    private String cardUserName;

    @Column(nullable = false)
    private LocalDate userBirth;



    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅

}
