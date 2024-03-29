package com.green.hoteldog.board.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.hoteldog.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class GetBoardCommentDto {
    @Min(value = 1,message = "boardPk 값은 1 이상이어야 합니다.")
    @Schema(description = "게시글pk",defaultValue = "1")
    private long boardPk;
    @Schema(description = "게시글에 등록된 댓글 페이지 번호",defaultValue = "1")
    private int page;
    @JsonIgnore
    private int rowCount = Const.COMMENT_COUNT_PER_PAGE;
    @JsonIgnore
    private Pageable pageable;
}
