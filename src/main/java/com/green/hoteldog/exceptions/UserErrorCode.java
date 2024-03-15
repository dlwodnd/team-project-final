package com.green.hoteldog.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    HOTEL_PICS_SIZE_ERROR(HttpStatus.BAD_REQUEST,"사진은 1장 이상 5장 이하로 등록해주세요."),
    NOT_EXISTS_WITHDRAWAL_USER(HttpStatus.NOT_FOUND,"탈퇴한 유저가 아닙니다."),
    NOT_CERTIFICATION_EMAIL(HttpStatus.BAD_REQUEST,"인증이 안된 이메일 입니다."),
    ALREADY_USED_EMAIL(HttpStatus.BAD_REQUEST,"이미 등록된 이메일입니다."),
    ALREADY_USED_NICKNAME(HttpStatus.BAD_REQUEST,"이미 등록된 닉네임입니다"),
    UNKNOWN_EMAIL_ADDRESS(HttpStatus.NOT_FOUND,"등록되지 않은 이메일입니다."),
    NOT_BUSINESS_USER(HttpStatus.NOT_FOUND,"사업자 유저가 아닙니다."),
    WITHDRAWAL_USER(HttpStatus.NOT_FOUND,"회원 탈퇴 신청한 유저입니다."),
    NOT_EXISTS_RESERVATION(HttpStatus.NOT_FOUND,"예약 정보가 없습니다."),
    MISS_MATCH_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호를 확인 해 주세요");


    private final HttpStatus httpStatus;
    private final String message;
}
