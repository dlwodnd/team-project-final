package com.green.hoteldog.business_user.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationTodayInfo {
    private String resNum;
    private long resPk;
    private String nickname;
    private long hotelRoomPk;
    private String hotelRoomNm;
    private String hotelRoomPic;
    private long resDogPk;
    private String resDogNm;
    private String resDogInfo;
    private long resDogAge;
    private long dogSizePk;
    private String dogSizeNm;
    private String fromDate;
    private String toDate;
    private String userPhoneNum;
    private long paymentAmount; // 결제금액
    private long resStatus; // 예약 상태 0.진행중, 1.예약완료, 2.이용완료, 3.본인취소, 4사업자취소

    @QueryProjection
    public ReservationTodayInfo(String resNum
            , long resPk
            , String nickname
            , long hotelRoomPk
            , String hotelRoomNm
            , long resDogPk
            , String resDogNm
            , String resDogInfo
            , long resDogAge
            , long dogSizePk
            , String dogSizeNm
            , String fromDate
            , String toDate
            , String userPhoneNum
            , long paymentAmount
            , long resStatus
            , String hotelRoomPic) {
        this.resNum = resNum;
        this.resPk = resPk;
        this.nickname = nickname;
        this.hotelRoomPk = hotelRoomPk;
        this.hotelRoomNm = hotelRoomNm;
        this.resDogPk = resDogPk;
        this.resDogNm = resDogNm;
        this.resDogInfo = resDogInfo;
        this.resDogAge = resDogAge;
        this.dogSizePk = dogSizePk;
        this.dogSizeNm = dogSizeNm;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userPhoneNum = userPhoneNum;
        this.paymentAmount = paymentAmount;
        this.resStatus = resStatus;
        this.hotelRoomPic = hotelRoomPic;
    }
}

