package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResRoomInfo {
    private long hotelRoomPk;
    private long hotelPk;
    private long sizePk;
    private String hotelRoomNm;
    private String roomPic;
}
