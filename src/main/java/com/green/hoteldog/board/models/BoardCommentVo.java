package com.green.hoteldog.board.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class BoardCommentVo {
    @Schema(description = "최대 페이지 수")
    private long commentMaxPage;
    @Schema(description = "댓글 개수")
    private long commentCount;
    @Schema(description = "댓글 정보")
    private List<CommentInfoVo> commentInfoVoList;
}
