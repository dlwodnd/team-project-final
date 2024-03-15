package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelRoomInfo {
    private long hotelRoomPk;
    private long sizePk;
    private String hotelRoomNm;
    private String roomPic;
    private long hotelRoomEa;
    private long hotelRoomCost;
    private long maximum;
    private long roomAble;
    private String discountPer;
    private String createdAt;
    private long discountSignStatus;
}
