package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.BoardCommentEntity;
import com.green.hoteldog.common.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
    Page<BoardCommentEntity> findAllByBoardEntity_BoardPkOrderByCreatedAtAsc(Long boardPk, Pageable pageable);
    Page<BoardCommentEntity> findAllByUserEntityOrderByCreatedAtDesc(UserEntity userEntity, Pageable pageable);

}
