package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HotelOptionInfoVo {
    private long optionPk;
    private String optionNm;
}
