package com.green.hoteldog.board.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostBoardDto {
    @Min(value = 1,message = "boardCategoryPk 값은 최소 1 이어야 합니다.")
    @Schema(description = "게시글 카테고리 pk",defaultValue = "1")
    private long boardCategoryPk;
    @NotEmpty(message = "title은 필수 입력값 입니다.")
    @Schema(description = "게시글 제목")
    private String title;
    @NotEmpty(message = "contetns값은 필수 입력값 입니다.")
    @Schema(description = "게시글 내용")
    private String contents;
    @JsonIgnore
    private List<MultipartFile> pics;
}
