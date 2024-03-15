package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //승민

    /*//모든 유저
    List<UserEntity> findAllByOrderByCreatedAtDesc();

    //사업자
    List<UserEntity> findAllByUserPkInOrderByCreatedAtDesc(List<Long> userPks);
    // 일반유저
    List<UserEntity> findAllByUserPkNotInOrderByCreatedAtDesc(List<Long> userPks);*/


    // 일반유저
    Optional<UserEntity> findByUserEmail(String email);
    UserEntity findByNickname(String nickname);


    //승민

    //승준
    //승준

    //영웅
    //영웅

    //재웅
    //재웅

}
