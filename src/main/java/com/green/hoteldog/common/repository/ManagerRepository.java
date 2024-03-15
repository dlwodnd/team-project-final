package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
    Optional<ManagerEntity> findByManagerId(String managerId);
}
