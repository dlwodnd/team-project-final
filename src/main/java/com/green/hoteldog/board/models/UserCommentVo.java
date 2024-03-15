package com.green.hoteldog.board.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentVo {
    @Schema(description = "게시글 pk")
    private long boardPk;
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "게시글 카테고리pk")
    private long boardCategoryPk;
    @Schema(description = "게시글 카테고리 이름")
    private String categoryNm;
    @Schema(description = "댓글 pk")
    private long commentPk;
    @Schema(description = "유저가 작성한 댓글")
    private String comment;
    @Schema(description = "게시글 작성 유저pk")
    private long userPk;
    @Schema(description = "게시글 작성 유저 닉네임")
    private String nickname;
    @Schema(description = "게시글 작성 시간")
    private String createdAt;
    @Schema(description = "게시글 조회수")
    private long boardViewCount;
}
