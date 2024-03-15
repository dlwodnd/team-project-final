package com.green.hoteldog.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSigninVo {
    private String managerId;
    private String managerName;
    private String role;
    private String accessToken;
}
