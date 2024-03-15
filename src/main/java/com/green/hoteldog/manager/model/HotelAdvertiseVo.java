package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.BusinessEntity;
import com.green.hoteldog.common.entity.HotelAdvertiseEntity;
import com.green.hoteldog.common.entity.HotelEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
public class HotelAdvertiseVo {
    private Long hotelAdvertisePk;
    private HotelEntity hotelEntity;
    private LocalDateTime hotelAdvertiseToDate;
    private LocalDateTime hotelAdvertiseFromDate;
    private Long paymentStatus;
    private Long signStatus;
    private String cancelReason;
    private String hotelAdvertiseNum;

    public  HotelAdvertiseVo hotelAdvertiseVo(HotelAdvertiseEntity hotelAdvertiseEntity) {
        HotelAdvertiseVo hotelAdvertiseVo = new HotelAdvertiseVo();
        hotelAdvertiseVo.setHotelAdvertisePk(hotelAdvertiseEntity.getHotelAdvertisePk());
        hotelAdvertiseVo.setHotelEntity(hotelAdvertiseEntity.getHotelEntity());
        hotelAdvertiseVo.setHotelAdvertiseToDate(hotelAdvertiseEntity.getHotelAdvertiseToDate());
        hotelAdvertiseVo.setHotelAdvertiseFromDate(hotelAdvertiseEntity.getHotelAdvertiseEndDate());
        hotelAdvertiseVo.setPaymentStatus(hotelAdvertiseEntity.getPaymentStatus());
        hotelAdvertiseVo.setHotelAdvertiseNum(hotelAdvertiseEntity.getHotelAdvertiseNum());
        return hotelAdvertiseVo;
    }
}
