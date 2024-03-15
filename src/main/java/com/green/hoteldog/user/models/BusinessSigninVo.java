package com.green.hoteldog.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessSigninVo {

    private long businessPk;
    private String userRole;
    private String businessName;
    private String accessToken;

}
