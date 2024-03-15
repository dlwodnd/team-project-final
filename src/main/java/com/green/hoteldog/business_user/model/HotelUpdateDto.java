package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HotelUpdateDto {
    private String hotelDetailInfo;

    private List<Long> optionList;

    private List<Long> deletePicsPk;

    @JsonIgnore
    List<MultipartFile> hotelPics;
}
