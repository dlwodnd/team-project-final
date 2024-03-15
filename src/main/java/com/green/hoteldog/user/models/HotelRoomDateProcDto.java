package com.green.hoteldog.user.models;

import com.green.hoteldog.common.entity.HotelRoomInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class HotelRoomDateProcDto {
    private HotelRoomInfoEntity hotelRoomInfoEntity;
    private LocalDate toDate;
    private LocalDate fromDate;
}
