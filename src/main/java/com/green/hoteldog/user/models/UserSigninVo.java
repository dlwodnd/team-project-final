package com.green.hoteldog.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.hoteldog.hotel.model.DogSizeEa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserSigninVo {
    private long userPk;
    private String userRole;
    private String nickname;
    private String accessToken;
    private String depthName; //유저 주소
    @JsonProperty(value = "dog_info")
    @Schema(title = "강아지 정보")
    private List<DogSizeEa> dogInfo;
}
