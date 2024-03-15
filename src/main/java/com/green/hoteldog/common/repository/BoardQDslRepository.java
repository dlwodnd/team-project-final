package com.green.hoteldog.common.repository;

import com.green.hoteldog.board.models.GetBoardListDto;
import com.green.hoteldog.common.entity.BoardEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardQDslRepository {
    Page<BoardEntity> boardListFilter(GetBoardListDto dto);
}
