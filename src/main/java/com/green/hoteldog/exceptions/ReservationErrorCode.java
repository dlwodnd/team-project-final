package com.green.hoteldog.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode implements ErrorCode{
    RESERVATION_NOT_FOUND
            (HttpStatus.BAD_REQUEST, "예약 정보를 찾을 수 없습니다."),
    RESERVATION_PERIOD_EXCEEDS_30_DAYS
            (HttpStatus.BAD_REQUEST, "예약 기간은 30일을 초과할 수 없습니다."),
    DOG_SIZE_AND_ROOM_SIZE_DO_NOT_MATCH
            (HttpStatus.BAD_REQUEST, "방과 강아지 사이즈가 맞지 않습니다."),
    BAD_REQUEST
            (HttpStatus.BAD_REQUEST, "날짜 정보를 잘 못 입력했습니다."),
    NO_DATE_INFORMATION
            (HttpStatus.BAD_REQUEST, "날짜 정보가 없습니다."),
    NOT_CHECK_IN(
            HttpStatus.BAD_REQUEST, "체크인 되지 않은 예약입니다."),
    CANCEL_RESERVATION
            (HttpStatus.BAD_REQUEST, "취소된 예약입니다"),
    ALREADY_CHECK_OUT
            (HttpStatus.BAD_REQUEST, "이미 체크아웃된 예약입니다."),
    ALREADY_CHECK_IN
            (HttpStatus.BAD_REQUEST, "이미 체크인된 예약입니다."),
    NOT_APPROVAL_RESERVATION
            (HttpStatus.BAD_REQUEST, "승인되지 않은 예약입니다."),
    UNKNOWN_DOG_SIZE_PK
            (HttpStatus.BAD_REQUEST, "알 수 없는 강아지 사이즈 pk"),
    NOT_CANCELABLE
            (HttpStatus.BAD_REQUEST, "취소할 수 없는 예약입니다."),
    NOT_WAITING_APPROVAL
            (HttpStatus.BAD_REQUEST, "승인 대기중인 예약이 아닙니다."),
    UNKNOWN_USER_PK
            (HttpStatus.BAD_REQUEST,"로그인 후 이용 해주세요."),
    RESERVATION_TABLE_REGISTRATION_FAILED
            (HttpStatus.BAD_REQUEST, "예약 테이블 등록 실패"),
    RESERVATION_DOG_TABLE_REGISTRATION_FAILED
            (HttpStatus.BAD_REQUEST, "예약 강아지 테이블 등록 실패"),
    ROOM_AND_DOG_RESERVATION_TABLE_REGISTRATION_FAILED
            (HttpStatus.BAD_REQUEST, "예약방&강아지 정보 예약 테이블 등록 실패"),
    HOTEL_ROOM_MANAGEMENT_TABLE_UPDATE_FAILED
            (HttpStatus.BAD_REQUEST, "호텔 방 관리 테이블 수정 실패"),
    NO_RESERVATION_INFORMATION
            (HttpStatus.BAD_REQUEST, "예약 정보 없음"),
    ROOM_PK_SELECT_FAILURE
            (HttpStatus.BAD_REQUEST, "방pk 셀렉트 실패"),
    FAILED_TO_DELETE_HOTEL_ROOM_DOG_TABLE
            (HttpStatus.BAD_REQUEST, "호텔방 강아지 테이블 삭제 실패"),
    FAILED_TO_DELETE_RESERVED_DOG_TABLE
            (HttpStatus.BAD_REQUEST, "예약 강아지 테이블 삭제 실패"),
    NOT_EXIST_RESERVATION
            ( HttpStatus.BAD_REQUEST, "예약 정보가 없습니다."),
    FAILED_TO_DELETE_HOTEL_RESERVATION_TABLE
            (HttpStatus.BAD_REQUEST, "호텔 예약 테이블 삭제 실패"),
    NO_ROOMS_AVAILABLE_FOR_THIS_DATE
            (HttpStatus.BAD_REQUEST, "해당 날짜에 방을 예약 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
