package com.green.hoteldog.review.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewFavDto {

    @Schema(description = "리뷰 pk")
    private long reviewPk;
}
