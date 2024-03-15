package com.green.hoteldog.common.repository;

import com.green.hoteldog.board.models.GetBoardListDto;
import com.green.hoteldog.common.entity.BoardEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.green.hoteldog.common.entity.QBoardEntity.boardEntity;

@RequiredArgsConstructor
public class BoardQDslRepositoryImpl implements BoardQDslRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardEntity> boardListFilter(GetBoardListDto dto) {
        List<BoardEntity> boardEntityList = queryFactory
                .selectFrom(boardEntity)
                .where(searchFilter(dto), categoryTypeFilter(dto))
                .offset(dto.getPageable().getOffset())
                .limit(dto.getPageable().getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(boardEntity.count())
                .from(boardEntity)
                .where(searchFilter(dto), categoryTypeFilter(dto));

        return PageableExecutionUtils.getPage(boardEntityList,dto.getPageable(),countQuery::fetchCount);
    }

    public BooleanExpression searchFilter(GetBoardListDto dto){
        if(dto.getSearch() != null && !dto.getSearch().isEmpty()){
            switch((int) dto.getSearchType()){
                case 0:
                    return boardEntity.title.contains(dto.getSearch());
                case 1:
                    return boardEntity.contents.contains(dto.getSearch());
                case 2:
                    return boardEntity.userEntity.nickname.contains(dto.getSearch());
            }
        }
        return null;
    }
    public BooleanExpression categoryTypeFilter(GetBoardListDto dto){
        if(dto.getBoardCategoryPk() == 0) {
            return null;
        }
        else {
            return boardEntity.boardCategoryEntity.boardCategoryPk.eq(dto.getBoardCategoryPk());
        }
    }
}
