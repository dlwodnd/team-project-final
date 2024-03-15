package com.green.hoteldog.business_user;

import com.green.hoteldog.business_user.model.*;
import com.green.hoteldog.common.Const;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.exceptions.BoardErrorCode;
import com.green.hoteldog.exceptions.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
@Tag(name = "호텔 사업자 API", description = "사업자 유저 관련 처리")
public class BusinessController {
    private final BusinessService service;


    // 호텔 상태 전환
    @Operation(summary = "호텔 상태 전환",
            description = "호텔 상태가 운영 중에는 호텔 중지 신청을 합니다<br>" +
                    "호텔이 중지상태라면 바로 운영 중으로 전환합니다<br>" +
                    "결과값은 중지 신청시 result = 1 , 운영중으로 변경시 result = 2 입니다<br>" +
                    "중지 신청시에는 사유를 입력해야 합니다")
    @PostMapping("/state")
    public ResVo insHotelStateChange(@RequestBody HotelSateChangeInsDto dto){
        return service.insHotelStateChange(dto);
    }

    // 광고 신청
    @Operation(summary = "광고 신청", description = "광고 신청<br>" +
            "cardNum: 카드 번호<br>" +
            "cardValidThru: 카드 유효기간<br>" +
            "userBirth: 생년월일<br>" +
            "cardUserName: 카드 소유자<br>" +
            "이미 광고 신청상태면 작동하지 않습니다.")
    @PostMapping("/advertise")
    public ResVo postHotelAdvertiseApplication(@RequestBody HotelAdvertiseApplicationDto dto){
        return service.postHotelAdvertiseApplication(dto);
    }
    // 광고 연장 취소
    @Operation(summary = "광고 연장 취소", description = "광고 연장 취소<br>" +
            "이미 광고 취소상태면 작동하지 않습니다.<br>")
    @GetMapping("/advertise/cancel")
    public ResVo postHotelAdvertiseCancel(){
        return service.postHotelAdvertiseCancel();
    }

    // 예약 리스트 출력
    @GetMapping("/reservation")
    @Operation(summary = "호텔 예약 정보 리스트", description = "호텔 예약 정보 리스트<br>" +
            "출력 값<br>" +
            "totalPage: 전체 페이지 수<br>" +
            "reservationInfoList: 예약 정보 리스트<br>" +
            "reservationInfoList의 출력 값<br>" +
            "resPk: 예약 pk<br>" +
            "resNum: 예약 번호<br>" +
            "nickname: 유저 닉네임<br>" +
            "hotelNm: 호텔 이름<br>" +
            "fromDate: 체크인 날짜<br>" +
            "toDate: 체크아웃 날짜<br>" +
            "userPhoneNum: 유저 전화번호<br>" +
            "payment: 결제 가격<br>" +
            "resStatus: 예약 상태<br>"+
            "예약상태 : 0 : 승인 전, 1 : 예약 승인, 2 : 체크인 , 3 : 체크아웃, 4 : 유저 예약 취소, 5 : 사업자 예약 취소<br>"
    )
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", required = true)
    })
    public ReservationInfoVo getHotelReservationList(@RequestParam int page){
        if(page - 1 < 0){
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, Const.COMMON_PAGE_SIZE);
        return service.getHotelReservationList(pageable);
    }
    // 오늘 예약 정보 리스트
    @GetMapping("/reservation/today")
    @Operation(summary = "오늘 호텔 방 예약 리스트", description = "오늘 호텔 방 예약 리스트<br>" +
            "출력 값<br>" +
            "totalPage: 전체 페이지 수<br>" +
            "reservationTodayInfoList: 오늘 호텔 방 예약 정보 리스트<br>" +
            "reservationTodayInfoList 출력 값<br>" +
            "resNum: 예약 번호<br>" +
            "resPk: 예약 pk<br>" +
            "nickname: 유저 닉네임<br>" +
            "hotelRoomPk: 호텔 방 pk<br>" +
            "hotelRoomNm: 호텔 방 이름<br>" +
            "resDogPk: 예약된 강아지 pk<br>" +
            "resDogNm: 예약된 강아지 이름<br>" +
            "resDogInfo: 예약된 강아지 정보<br>" +
            "dogSizePk: 강아지 사이즈 pk<br>" +
            "dogSizeNm: 강아지 사이즈 이름<br>" +
            "fromDate: 체크인 날짜<br>" +
            "toDate: 체크아웃 날짜<br>" +
            "userPhoneNum: 유저 전화번호<br>" +
            "paymentAmount: 예약 결제 금액<br>" +
            "resStatus: 예약 상태<br>" +
            "예약상태 : 0 : 승인 전, 1 : 예약 승인, 2 : 체크인 , 3 : 체크아웃, 4 : 유저 예약 취소, 5 : 사업자 예약 취소<br>"
    )
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", required = true)
    })
    public ReservationTodayInfoVo getHotelReservationListToday(@RequestParam int page){

        Pageable pageable = PageRequest.of(page - 1, Const.COMMON_PAGE_SIZE);
        return service.getHotelReservationTodayList(pageable);
    }


    //사업자 유저 호텔 등록 -- 사업자 회원 가입 기능으로 변경
    /*@PostMapping("/registration")
    @Operation(summary = "사업자 유저 호텔 등록", description = "사업자 유저가 호텔을 등록합니다.<br>" +
            "입력값<br>" +
            "businessCertificationFile: 사업자 등록증 파일<br>" +
            "hotelPics: 호텔 사진 리스트(최대 5장)<br>"
    )
    public ResVo postHotel(@RequestPart HotelInsDto dto,
                           @RequestPart MultipartFile businessCertificationFile,
                           @RequestPart List<MultipartFile> hotelPics){
        if(hotelPics.size() > 5){
            throw new CustomException(BoardErrorCode.PICS_SIZE_OVER);
        }
        dto.setBusinessCertificationFile(businessCertificationFile);
        dto.setHotelPics(hotelPics);
        return service.insHotel(dto);
    }*/
    //사업자 유저 호텔 정보 수정
    @PutMapping(value = "/hotel",consumes = "multipart/form-data")
    @Operation(summary = "사업자 유저 호텔 정보 수정", description = "사업자 유저가 등록한 호텔 정보를 수정합니다.<br>" +
            "입력값<br>" +
            "hotelPics: 호텔 사진 리스트(최대 5장)<br>" +
            "hotelDetailInfo: 호텔 정보<br>" +
            "optionList: 호텔 옵션pk 리스트<br>"
    )
    public ResVo putHotel(@RequestPart HotelUpdateDto dto,
                          @RequestPart(required = false) List<MultipartFile> hotelPics){
        if(hotelPics.size() > 5){
            throw new CustomException(BoardErrorCode.PICS_SIZE_OVER);
        }

        dto.setHotelPics(hotelPics);
        return service.putHotel(dto);
    }

    //사업자 유저가 등록한 호텔 정보
    @GetMapping
    @Operation(summary = "사업자 유저 호텔 정보", description = "사업자 유저가 등록한 호텔 정보를 불러옵니다.")
    public BusinessUserHotelVo getBusinessUserHotel(){
        return service.getBusinessUserHotel();
    }

    //사업자 유저가 등록한 호텔 방 수정
    @PutMapping("/hotelRoom")
    @Operation(summary = "사업자 유저 호텔 방 수정", description = "사업자 유저가 등록한 호텔 방 정보를 수정합니다.")
    public ResVo putHotelRoom(@RequestPart(required = false) MultipartFile roomPic,
                              @RequestPart BusinessHotelRoomPutDto dto){
        dto.setRoomPic(roomPic);
        if(roomPic == null){
            dto.setRoomPic(null);
        }
        return service.putHotelRoomInfo(dto);
    }

    //사업자 유저 호텔에 예약된 강아지 방 정보 리스트
    @GetMapping("/reservation/HotelRoomAndDogInfo")
    @Operation(summary = "사업자 유저 호텔에 예약된 강아지 방 정보 리스트", description = "사업자 유저가 등록한 호텔에 예약된 강아지 방 정보 리스트를 불러옵니다.")
    @Parameters({
            @Parameter(name = "resPk", description = "예약 pk", required = true)
    })
    public List<HotelRoomAndDogInfoVo> getHotelRoomAndDogInfo(long resPk){

        return service.getHotelRoomAndDogInfo(resPk);
    }

    //호텔 방 활성화 & 비활성화 토글
    @PatchMapping("/hotelRoom")
    @Operation(summary = "호텔 방 활성화 & 비활성화 토글", description = "호텔 방 활성화 & 비활성화 토글<br>" +
            "활성화 상태면 비활성화로, 비활성화 상태면 활성화로 전환합니다.<br>" +
            "result: 1.활성화, 2.비활성화<br>"
    )
    public ResVo toggleHotelRoomActive(@RequestBody HotelRoomToggleDto dto){
        return service.toggleHotelRoomActive(dto);
    }

    //사업자 유저 회원 탈퇴
    @PostMapping("/withdrawal")
    @Operation(summary = "사업자 유저 회원 탈퇴", description = "사업자 유저 회원 탈퇴")
    public ResVo postBusinessUserWithdrawal(){
        return service.postBusinessUserWithdrawal();
    }
    //사업자 호텔에 등록된 예약 승인처리
    @PatchMapping("/reservation/approval")
    @Operation(summary = "사업자 호텔에 등록된 예약 접수", description = "사업자 호텔에 등록된 예약 접수")
    @Parameter(name = "resPkList", description = "예약 pk 리스트", required = true)
    public ResVo patchReservationApproval(List<Long> resPkList){
        return service.reservationApproval(resPkList);
    }

    //사업자 호텔에 등록된 예약 취소처리
    @PostMapping("/reservation/cancel")
    @Operation(summary = "사업자 호텔에 등록된 예약 취소", description = "사업자 호텔에 등록된 예약 취소")
    public ResVo patchReservationCheckIn(@RequestBody ResCancelDto dto){
        return service.reservationCancel(dto);
    }

    //사업자 호텔에 등록된 예약 체크인 처리
    @PatchMapping("/reservation/checkIn")
    @Parameter(name = "resPkList", description = "예약 pk 리스트", required = true)
    @Operation(summary = "사업자 호텔에 등록된 예약 체크인 처리", description = "사업자 호텔에 등록된 예약 체크인 처리")
    public ResVo patchReservationCheckIn(@RequestBody List<Long> resPkList){
        return service.reservationCheckIn(resPkList);
    }

    //사업자 호텔에 등록된 예약 체크아웃 처리
    @PatchMapping("/reservation/checkOut")
    @Parameter(name = "resPkList", description = "예약 pk 리스트", required = true)
    @Operation(summary = "사업자 호텔에 등록된 예약 체크아웃", description = "사업자 호텔에 등록된 예약 체크아웃 처리")
    public ResVo patchReservationCheckOut(@RequestBody List<Long> resPkList){
        return service.reservationCheckOut(resPkList);
    }


}
