package com.green.hoteldog.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessUserInfo {
    private long businessUserPk;
    private String userEmail;
    private String businessName;
    private String phoneNum;
    private String hotelAddress;
}
