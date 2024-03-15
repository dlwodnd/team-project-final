package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelRoomAndDogInfoVo {
    private ResDogInfo resDogInfo;
    private ResRoomInfo resRoomInfo;
}
