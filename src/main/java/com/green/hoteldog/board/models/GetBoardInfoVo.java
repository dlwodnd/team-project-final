package com.green.hoteldog.board.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBoardInfoVo {
    @Schema(description = "게시글 pk")
    private long boardPk;
    @Schema(description = "게시글 카테고리 pk")
    private long boardCategoryPk;
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "게시글 작성자 pk")
    private long userPk;
    @Schema(description = "게시글 내용")
    private String contents;
    @Schema(description = "게시글 작성자 닉네임")
    private String nickname;
    @Schema(description = "작성 시간")
    private String createdAt;
    @Schema(description = "조회수")
    private long boardViewCount;
    @Schema(description = "사진")
    private List<String> pics;


}
