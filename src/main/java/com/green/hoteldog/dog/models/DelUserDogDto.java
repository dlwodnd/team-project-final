package com.green.hoteldog.dog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DelUserDogDto {

    @NotBlank(message = "값을 입력해 주세요")
    @Schema(description = "유저강아지 pk")
    private long userDogPk;
}
