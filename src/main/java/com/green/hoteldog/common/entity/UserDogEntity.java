package com.green.hoteldog.common.entity;

import com.green.hoteldog.common.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_dog")
public class UserDogEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDogPk;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk",referencedColumnName = "userPk",columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "size_pk",referencedColumnName = "sizePk",columnDefinition = "BIGINT UNSIGNED")
    private DogSizeEntity dogSizeEntity;

    @Column(nullable = false)
    private String dogNm;

    @Column(nullable = false)
    private String dogAge;

    @Column(name ="dog_etc" ,nullable = false)
    private String etc;

    private String dogPic;

    @Column(nullable = false)
    private String userDogNum;

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅
}
