package com.green.hoteldog.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "호텔 정보 + 리뷰<br>",description = "호텔 상세페이지에 뿌릴<br>" +
        "호텔 정보.(방 정보,옵션,리뷰(3개까지))")

public class HotelDetailInfoVo {
    @NotNull
    @JsonProperty(value = "hotel_pk")
    @Schema(name = "호텔pk")
    private long hotelPk;

    @NotNull
    @JsonProperty(value = "hotel_nm")
    @Schema(name = "호텔 이름")
    private String hotelNm;

    @NotNull
    @JsonProperty(value = "hotel_detail_info")
    @Schema(name = "호텔 상세정보")
    private String hotelDetailInfo;

    @NotNull
    @JsonProperty(value = "business_num")
    @Schema(name = "사장님의 사업자번호")
    private String businessNum;

    @NotNull
    @JsonProperty(value = "hotel_call")
    @Schema(name = "호텔 전화번호")
    private String hotelCall;

    @NotNull
    @JsonProperty(value = "road_address")
    @Schema(name = "호텔 주소")
    private String hotelAddress;


    @Schema(name = "호텔 사진")
    private List<String> pics;

    @NotNull
    @JsonProperty(value = "hotel_option")
    @Schema(name = "호텔 옵션")
    private List<HotelOptionInfoVo> hotelOption;

    @NotNull
    @Schema(name = "호텔 방 정보 리스트.")
    @JsonProperty(value = "room_list")
    private List<HotelRoomInfoVo> roomList;

    @Schema(name = "유저들이 정성스럽게 쓴 호텔 리뷰")
    @JsonProperty(value = "review_list")
    private HotelReviewVo review;

    @Schema(name = "리뷰 더있니?",description = "리뷰 더 없음=0,리뷰 더 있음=1"
            ,defaultValue = "0")
    @JsonProperty(value = "is_more_review")
    private int isMoreReview;
}
