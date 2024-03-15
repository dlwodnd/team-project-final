package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.WithdrawalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface WithdrawalUserRepository extends JpaRepository<WithdrawalUserEntity, Long> {

    //승민
    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    WithdrawalUserEntity findByUserEntity(UserEntity userEntity);
    //재웅

}
