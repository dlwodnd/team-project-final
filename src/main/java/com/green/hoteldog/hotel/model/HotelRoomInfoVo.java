package com.green.hoteldog.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "호텔 방 정보")
public class HotelRoomInfoVo {
    @NotNull
    @JsonProperty(value = "hotel_room_pk")
    private long hotelRoomPk;
    @NotNull
    @JsonProperty(value = "hotel_room_nm")
    private String hotelRoomNm;
    @NotNull
    @JsonProperty(value = "hotel_room_ea")
    private long hotelRoomEa;
    @NotNull
    @JsonProperty(value = "hotel_room_cost")
    private long hotelRoomCost;

    private String pic;

    @NotNull
    private long maximum;

    private long roomAble;

    @QueryProjection
    public HotelRoomInfoVo(long hotelRoomPk
            , String hotelRoomNm
            , long hotelRoomEa
            , long hotelRoomCost
            , String pic
            , long maximum
            , long roomAble) {
        this.hotelRoomPk = hotelRoomPk;
        this.hotelRoomNm = hotelRoomNm;
        this.hotelRoomEa = hotelRoomEa;
        this.hotelRoomCost = hotelRoomCost;
        this.pic = pic;
        this.maximum = maximum;
        this.roomAble = roomAble;
    }
}

