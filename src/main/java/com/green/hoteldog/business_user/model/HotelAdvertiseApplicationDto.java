package com.green.hoteldog.business_user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotelAdvertiseApplicationDto {
    private String cardNum;
    private LocalDate cardValidThru;
    private String cardUserName;
    private LocalDate userBirth;
}
