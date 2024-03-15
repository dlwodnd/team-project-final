package com.green.hoteldog.user.models;

import lombok.Data;

@Data
public class AdminSignupDto {
    private String managerId;

    private String managerPw;

    private String managerName;
}
