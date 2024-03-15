package com.green.hoteldog.dog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdUserDogDto {

    @Schema(description = "강아지 pk")
    private long userDogPk;
    @Schema(description = "강아지 사이즈 pk")
    private long sizePk;
    @Schema(description = "강아지 이름")
    private String dogNm;
    @Schema(description = "강아지 나이")
    private long dogAge;
    @JsonIgnore
    private MultipartFile dogPic;
    @Schema(description = "강아지 상세정보")
    private String dogEtc;
}
