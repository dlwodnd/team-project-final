package com.green.hoteldog.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.hoteldog.common.entity.base.CreatedAtBaseEntity;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "t_business")
public class BusinessEntity extends CreatedAtBaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessPk;


    @Column(nullable = false)
    private String businessName;

    @ColumnDefault("'BUSINESS_USER'")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false,unique = true)
    private String businessEmail;

    @Column(nullable = false)
    private String businessPw;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long businessStatus;

    @Column(nullable = false)
    private String businessPhoneNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToOne(mappedBy = "businessEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    private HotelEntity hotelEntity;

    /*@ToString.Exclude
    @OneToMany(mappedBy = "businessEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<PaymentAdEntity> paymentAdEntityList;*/


    //재웅
}
