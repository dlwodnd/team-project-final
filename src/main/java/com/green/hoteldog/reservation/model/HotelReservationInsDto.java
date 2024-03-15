package com.green.hoteldog.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(title = "호텔 예약 DTO")
public class HotelReservationInsDto{
    @Min(0)
    @JsonProperty(value = "hotel_pk")
    @Schema(title = "호텔pk")
    private long hotelPk;
    @FutureOrPresent
    @JsonProperty(value = "from_date")
    @Schema(title = "시작일")
    private LocalDate fromDate;
    @Future
    @JsonProperty(value = "to_date")
    @Schema(title = "종료일")
    private LocalDate toDate;
    @JsonProperty(value = "dog_info")
    @Schema(title = "강이지 정보")
    private List<DogInfo> dogInfo;
    @JsonIgnore
    private long totalPrice;
    @JsonIgnore
    private int userPk;
    @JsonIgnore
    private Integer resPk;
}
