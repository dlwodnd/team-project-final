package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationTodayInfoVo {
    private List<ReservationTodayInfo> reservationTodayInfoList;
    private int totalPage;
}
