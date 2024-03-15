package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.BusinessEntity;
import com.green.hoteldog.common.entity.HotelAdvertiseEntity;
import com.green.hoteldog.common.entity.PaymentAdEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelPaymentAdVo {

    private Long paymentPk;
    private BusinessEntity businessEntity;
    private HotelAdvertiseEntity hotelAdvertiseEntity;
    private Long paymentStatus;
    private LocalDateTime paymentDate;
    private String paymentAdNum;



    public static HotelPaymentAdVo hotelPaymentAdVo(PaymentAdEntity paymentAdEntity) {
        HotelPaymentAdVo hotelPaymentAdVo = new HotelPaymentAdVo();
        hotelPaymentAdVo.setPaymentPk(paymentAdEntity.getPaymentPk());
        hotelPaymentAdVo.setBusinessEntity(paymentAdEntity.getHotelEntity().getBusinessEntity());
        hotelPaymentAdVo.setHotelAdvertiseEntity(paymentAdEntity.getHotelAdvertiseEntity());
        hotelPaymentAdVo.setPaymentStatus(paymentAdEntity.getPaymentStatus());
        hotelPaymentAdVo.setPaymentDate(paymentAdEntity.getPaymentDate());
        hotelPaymentAdVo.setPaymentAdNum(paymentAdEntity.getPaymentAdNum());
        return hotelPaymentAdVo;
    }
}
