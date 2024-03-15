package com.green.hoteldog.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailInfoDto {
    private long hotelPk;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> dogSizePkList;
}
