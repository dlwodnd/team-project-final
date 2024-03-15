package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelAddressInfo {
    @JsonIgnore
    private long hotelPk;
    private String addressName;
    private String region1DepthName;
    private String region2DepthName;
    private String region3DepthName;
    private String zoneNum;
    private String x;
    private String y;
    private String detailAddress;
}
