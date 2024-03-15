package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class HotelRoomInsDto {
    @JsonIgnore
    private int iuser;
    private int hotelPk;
    private int sizePk;
    private String hotelNm;
    private int hotelRoomEa;
    private int hotelRoomCost;
    private int maximum;
    private String discountPer;
}
