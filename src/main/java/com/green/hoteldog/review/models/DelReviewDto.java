package com.green.hoteldog.review.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DelReviewDto {
    @Schema(description = "리뷰 pk")
    private long reviewPk;
    @Schema(description = "예약 pk")
    private long resPk;
}
