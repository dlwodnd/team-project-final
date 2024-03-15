package com.green.hoteldog.business_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ResDogInfo {
    private long dogResDogPk;
    private long sizePk;
    private String dogNm;
    private long dogAge;
    private String information;
}
