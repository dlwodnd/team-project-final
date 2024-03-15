package com.green.hoteldog.business_user.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ReservationInfo {
    private long resPk;
    private String resNum;
    private String nickname;
    private String hotelNm;
    private String fromDate;
    private String toDate;
    private String userPhoneNum;
    private long payment; // 결제금액
    private long resStatus; // 예약 상태 0.진행중, 1.예약완료, 2.이용완료, 3.본인취소, 4사업자취소

    @QueryProjection
    public ReservationInfo(long resPk, String resNum, String nickname, String hotelNm, String fromDate, String toDate, String userPhoneNum, long payment, long resStatus) {
        this.resPk = resPk;
        this.resNum = resNum;
        this.nickname = nickname;
        this.hotelNm = hotelNm;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userPhoneNum = userPhoneNum;
        this.payment = payment;
        this.resStatus = resStatus;
    }
}
