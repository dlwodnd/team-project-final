package com.green.hoteldog.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "북마크 등록 한 호텔 리스트")
public class HotelBookMarkListVo {
    @JsonProperty(value = "avg_star")
    private float avgStar;
    @JsonProperty(value = "hotelRoomCost")
    private String hotelRoomCost;
    @JsonProperty(value = "hotel_pk")
    private long hotelPk;
    @JsonProperty(value = "hotel_nm")
    private String hotelNm;
    @JsonProperty(value = "address_name")
    private String addressName;
    @JsonProperty(value = "hotel_pic")
    private String hotelPic;
    @JsonProperty(value = "discount_per")
    private int discountPer;
    @JsonProperty(value = "book_mark")
    private int bookMark;
    @JsonProperty(value = "review_count")
    private int reviewCount;
}
