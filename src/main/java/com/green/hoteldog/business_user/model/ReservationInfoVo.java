package com.green.hoteldog.business_user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ReservationInfoVo {
    private List<ReservationInfo> reservationInfoList;
    private int totalPage;
}
