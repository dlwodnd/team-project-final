package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BusinessHotelRoomPutDto {
    private long hotelRoomPk;

    @JsonIgnore
    private MultipartFile roomPic;
    @NotBlank
    private long hotelRoomEa;
    @NotBlank
    private long hotelRoomCost;

    @NotBlank
    private long discountPer;

}
