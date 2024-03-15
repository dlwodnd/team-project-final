package com.green.hoteldog.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfo {
    private Long hotelPk;
    private String hotelNum;
    private String hotelNm;
    private String businessName;
    private String hotelFullAddress;
    private String hotelCall;
    private Long advertise;
    private Long approval;
}
