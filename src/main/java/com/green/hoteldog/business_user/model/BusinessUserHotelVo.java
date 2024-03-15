package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUserHotelVo {
    private long hotelPk;
    private String hotelNum;
    private String hotelNm;
    private String hotelDetailInfo;
    private String businessName;
    private String businessNum;
    private String hotelCall;
    private String createdAt;

    private List<PicsInfo> hotelPics;
    private String hotelFullAddress;
    private HotelAddressInfo hotelAddressInfo;
    private long approval;
    private List<HotelOptionInfoVo> optionList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String businessCertificate;

    private List<HotelRoomInfo> hotelRoomInfoList;

    private long advertise;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String hotelAdvertiseToDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String hotelAdvertiseEndDate;
}
