package com.green.hoteldog.dog.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDogListVo {
    private long userDogPk;
    private long sizePk;
    private String dogSize;
    private String dogNm;
    private long dogAge;
    private String dogPic;
    private String dogEtc;
    private String createdAt;
}
