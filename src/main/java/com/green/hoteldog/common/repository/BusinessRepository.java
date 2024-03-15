package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.BusinessEntity;
import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

    //승민
    Page<BusinessEntity> findAllByRole(UserRoleEnum role, Pageable pageable);
    //재웅
    Optional<BusinessEntity> findByBusinessEmail(String businessEmail);

    //재웅
}
