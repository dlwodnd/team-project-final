package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.PaymentAdEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalAdListVo {
    private Long hotelPk;
    private String businessNum;
    private String hotelNum;
    private String hotelNm;
    private LocalDateTime hotelAdvertiseToDate;
    private LocalDateTime hotelAdvertiseFromDate;



    public static ApprovalAdListVo approvalAdListVo(PaymentAdEntity paymentAdEntity) {
        ApprovalAdListVo approvalAdListVo = new ApprovalAdListVo();
        approvalAdListVo.setHotelPk(paymentAdEntity.getHotelAdvertiseEntity().getHotelEntity().getHotelPk());
        approvalAdListVo.setBusinessNum(paymentAdEntity.getHotelAdvertiseEntity().getHotelEntity().getBusinessNum());
        approvalAdListVo.setHotelNum(paymentAdEntity.getHotelAdvertiseEntity().getHotelEntity().getHotelNum());
        approvalAdListVo.setHotelNm(paymentAdEntity.getHotelAdvertiseEntity().getHotelEntity().getHotelNm());
        approvalAdListVo.setHotelAdvertiseToDate(paymentAdEntity.getHotelAdvertiseEntity().getHotelAdvertiseToDate());
        approvalAdListVo.setHotelAdvertiseFromDate(paymentAdEntity.getHotelAdvertiseEntity().getHotelAdvertiseEndDate());
        return approvalAdListVo;
    }
}
