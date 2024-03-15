package com.green.hoteldog.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressInfo {
    @JsonIgnore
    private long userPk;
    private String addressName;
    private String region1DepthName;
    private String region2DepthName;
    private String region3DepthName;
    private String zoneNum;
    private String x;
    private String y;
    private String detailAddress;
}
