package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.BoardEntity;
import com.green.hoteldog.common.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> , BoardQDslRepository{
   Page<BoardEntity> findAllByUserEntityOrderByBoardPk(UserEntity userEntity, Pageable pageable);

}
