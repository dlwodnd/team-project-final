package com.green.hoteldog.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResRefundInfoVo {
    private long resPk;
    private long hotelPk;
    private String hotelNm;
    private String resNum;
    private String toDate;
    private String fromDate;
    private long paymentAmount;
    private long refundAmount;
}
