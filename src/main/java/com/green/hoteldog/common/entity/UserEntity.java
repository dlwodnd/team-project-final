package com.green.hoteldog.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.green.hoteldog.common.entity.base.BaseEntity;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert

public class UserEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPk;

    @Column(unique = true,nullable = false)
    private String userEmail;

    @Column(length = 300,nullable = false)
    private String upw;

    @Column(unique = true,nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String userAddress;


    @ColumnDefault("'0'")
    private Long userStatus;



    @ColumnDefault("'USER'")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;


    @Column(nullable = false)
    private String userNum;


    //승민

    /*@ToString.Exclude
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<BusinessEntity> businessEntities;*/

    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    @ToString.Exclude
    @OneToOne(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private UserWhereEntity userWhereEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<UserDogEntity> userDogEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ResPaymentEntity> resPaymentEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ReservationEntity> reservationEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ReviewFavEntity> reviewFavEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<BoardEntity> boardEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<BoardCommentEntity> boardCommentEntities;

    @ToString.Exclude
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<RefundEntity> refundEntities;



    //재웅

}
