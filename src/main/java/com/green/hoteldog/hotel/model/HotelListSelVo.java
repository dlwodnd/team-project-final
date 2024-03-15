package com.green.hoteldog.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "리스트 출력 VO")
public class HotelListSelVo {
    @JsonProperty(value = "hotel_pk")
    private long hotelPk;
    @JsonProperty(value = "hotel_nm")
    private String hotelNm;
    @JsonProperty(value = "address_name")
    private String addressName;
    @JsonProperty(value = "hotel_pic")
    private String hotelPic; // 가장 처음 등록한 사진
    private float avgStar; // 리뷰 평점 평균
    private String hotelRoomCost; // 하루 단위 최저가 방 가격
    @JsonProperty(value = "discount_per")
    private int discountPer;
    @JsonProperty(value = "book_mark")
    private int bookMark;
    @JsonProperty(value = "review_count")
    private int reviewCount;
}
