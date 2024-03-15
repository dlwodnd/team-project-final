package com.green.hoteldog.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WithdrawalErrorCode implements ErrorCode {
    EXIST_IN_OPERATION_HOTEL(HttpStatus.BAD_REQUEST, "운영중인 호텔이 존재합니다."),
    BUSINESS_USER_EXISTS(HttpStatus.BAD_REQUEST, "사업자가 존재합니다."),
    NOT_CHECK_OUT_RESERVATIONS_REMAIN(HttpStatus.BAD_REQUEST, "체크아웃이 안된 예약이 남아있습니다."),
    NON_REFUNDABLE_RESERVATIONS_REMAIN(HttpStatus.BAD_REQUEST, "환불 처리가 안된 예약이 남아있습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
