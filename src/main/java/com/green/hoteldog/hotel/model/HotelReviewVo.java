package com.green.hoteldog.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(name = "유저별 호텔 리뷰")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelReviewVo {
    @NotNull
    private long reviewPk;
    @NotNull
    @JsonProperty(value = "nick_name")
    @Schema(title = "닉네임")
    private String nickName;
    @Schema(title = "코멘트")
    private String comment;
    @NotNull
    @Schema(title = "별점 (1~10)")
    private long score;
    @JsonProperty(value = "updated_at")
    @Schema(title = "리뷰 작성일")
    private String updatedAt;
    @JsonProperty(value = "fav_count")
    @Schema(title = "좋아요 수")
    private long favCount;
    @Schema(name = "리뷰 사진들",type = "List<String>")
    private List<String> pics;
}

