package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.HotelSuspendedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class HotelSuspendedVo {
    private Long suspendedPk;
    private HotelEntity hotelEntity;
    private String suspendedReason;


    public  HotelSuspendedVo HotelSuspended(HotelSuspendedEntity hotelSuspendedEntity) {
        HotelSuspendedVo hotelSuspendedVo = new HotelSuspendedVo();
        hotelSuspendedVo.setSuspendedPk(hotelSuspendedEntity.getSuspendedPk());
        hotelSuspendedVo.setHotelEntity(hotelSuspendedEntity.getHotelEntity());
        hotelSuspendedVo.setSuspendedReason(hotelSuspendedEntity.getSuspendedReason());
        return hotelSuspendedVo;
    }
}
