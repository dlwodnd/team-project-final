package com.green.hoteldog.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.hoteldog.email.models.EmailResponseVo;
import com.green.hoteldog.user.models.UserAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BusinessUserSignupDto {
    @JsonIgnore
    private int userPk;

    @JsonIgnore
    private String userEmail;

    private EmailResponseVo emailResponseVo;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "비밀번호는 영문,숫자 조합으로 8글자 이상입니다.")
    @NotEmpty(message = "비밀번호를 입력해 주세요")
    @Schema(description = "유저 패스워드",defaultValue = "password")
    private String upw;

    @Pattern(regexp = "^01[0-1|6-9]{1}[\\d]{3,4}[\\d]{4}",message = "올바른 전화번호 입력이 아닙니다.")
    private String phoneNum;


    private String businessName;

    /*private String accountNumber;

    private String bankNm;*/

}
