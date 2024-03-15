package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.BusinessEntity;
import com.green.hoteldog.common.entity.HotelEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class HotelVo {
    private Long hotelPk;
    private BusinessEntity businessEntity;
    private String hotelNm;
    private String hotelDetailInfo;
    private String businessNum;
    private String hotelCall;
    private Long advertise;
    private Long approval;
    private Long signStatus;
    private String cancelReason;
    private String hotelNum;
    private String refuseReason;
    private String businessCertificate;

    public static HotelVo hotelVo(HotelEntity hotelEntity) {
        HotelVo hotelVo = new HotelVo();
        hotelVo.setHotelPk(hotelEntity.getHotelPk());
        hotelVo.setBusinessEntity(hotelEntity.getBusinessEntity());
        hotelVo.setHotelNm(hotelEntity.getHotelNm());
        hotelVo.setHotelDetailInfo(hotelEntity.getHotelDetailInfo());
        hotelVo.setBusinessNum(hotelEntity.getBusinessNum());
        hotelVo.setHotelCall(hotelEntity.getHotelCall());
        hotelVo.setAdvertise(hotelEntity.getAdvertise());
        hotelVo.setApproval(hotelEntity.getApproval());
        hotelVo.setSignStatus(hotelEntity.getSignStatus());
        hotelVo.setCancelReason(hotelEntity.getCancelReason());
        hotelVo.setHotelNum(hotelEntity.getHotelNum());
        hotelVo.setRefuseReason(hotelEntity.getRefuseReason());
        hotelVo.setBusinessCertificate(hotelEntity.getBusinessCertificate());
        return hotelVo;
    }
}
