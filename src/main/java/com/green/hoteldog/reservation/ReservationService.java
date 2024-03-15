package com.green.hoteldog.reservation;

import com.green.hoteldog.common.Const;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.common.entity.*;
import com.green.hoteldog.common.entity.composite.ResComprehensiveInfoComposite;
import com.green.hoteldog.common.repository.*;
import com.green.hoteldog.common.utils.RandomCodeUtils;
import com.green.hoteldog.exceptions.*;
import com.green.hoteldog.reservation.model.*;
import com.green.hoteldog.security.AuthenticationFacade;
import com.green.hoteldog.user.models.HotelRoomDateProcDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.green.hoteldog.common.utils.CommonUtils.getRefundAmount;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper reservationMapper;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final ReservationRepository reservationRepository;
    private final ResPaymentRepository resPaymentRepository;
    private final ResComprehensiveInfoRepository resComprehensiveInfoRepository;
    private final HotelRepository hotelRepository;
    private final HotelResRoomRepository hotelResRoomRepository;
    private final ResDogInfoRepository resDogInfoRepository;
    private final DogSizeRepository dogSizeRepository;
    private final RefundRepository refundRepository;


    //--------------------------------------------------호텔 예약---------------------------------------------------------
    @Transactional(rollbackFor = Exception.class)
    public ResVo postHotelReservation(List<HotelReservationInsDto> dto) {
        log.info("dto : {}", dto);
        for (HotelReservationInsDto dtos : dto) {
            dtos.setUserPk((int)authenticationFacade.getLoginUserPk());
            if (dtos.getUserPk() == 0) {
                throw new CustomException(ReservationErrorCode.UNKNOWN_USER_PK);
            }
            int affectedrows = reservationMapper.insHotelReservation(dtos);
            dtos.setResPk(dtos.getResPk());
            if (dtos.getResPk() == null) {
                throw new CustomException(ReservationErrorCode.RESERVATION_TABLE_REGISTRATION_FAILED); // 예약 테이블에 등록 실패
            }
            for (DogInfo info : dtos.getDogInfo()) {
                int affectedRows1 = reservationMapper.insHotelReservationDogInfo(info);
                if (affectedRows1 == 0) {
                    throw new CustomException(ReservationErrorCode.RESERVATION_DOG_TABLE_REGISTRATION_FAILED); // 예약 강아지 정보 등록 실패
                }
            }
        }
        for (HotelReservationInsDto dtos : dto) {
            int affectedRows2 = reservationMapper.insHotelReservationInfo(dtos);
            if (affectedRows2 == 0) {
                throw new CustomException(ReservationErrorCode.ROOM_AND_DOG_RESERVATION_TABLE_REGISTRATION_FAILED); // 예약방강아지 예약정보 등록 실패
            }
        }
        // 호텔 방 관리 테이블 업데이트
        // 날짜 list , 호텔 방 pk list 박스 생성
        List<HotelReservationUpdProcDto> updList = new ArrayList<>();
        for (HotelReservationInsDto hotelReservationInsDto : dto) {
            for (DogInfo dogInfo : hotelReservationInsDto.getDogInfo()) {
                HotelReservationUpdProcDto updProcDto = new HotelReservationUpdProcDto();
                updProcDto.setHotelRoomPk((int) dogInfo.getHotelRoomPk());

                LocalDate fromDate = hotelReservationInsDto.getFromDate();
                LocalDate toDate = hotelReservationInsDto.getToDate();
                // fromDate부터 toDate까지 날짜 배열 생성
                List<LocalDate> dateRange = new ArrayList<>();
                while (!fromDate.isAfter(toDate)) {
                    dateRange.add(fromDate);
                    fromDate = fromDate.plusDays(1);
                }
                /*// 날짜 출력
                for ( LocalDate dateList : dateRange ) {
                    log.info("date : {}", dateList);
                }*/
                updProcDto.setDate(dateRange);
                updList.add(updProcDto);
            }
        }
        try {
            int affectedRows3 = reservationMapper.updRemainedHotelRoom(updList);
            log.info("affectedRows3 : {}", affectedRows3);
        } catch (Exception e) {
            throw new CustomException(ReservationErrorCode.NO_ROOMS_AVAILABLE_FOR_THIS_DATE);
        }
        return new ResVo(Const.SUCCESS);
    }
    @Transactional(rollbackFor = Exception.class)
    public ResVo postHotelReservationFix(HotelReservationInsDto dto) {
        if(dto.getFromDate().isBefore(LocalDate.now())){
            throw new CustomException(ReservationErrorCode.BAD_REQUEST);
        }
        log.info("dto : {}", dto);
        if(dto.getToDate() == null || dto.getFromDate() == null){
            throw new CustomException(ReservationErrorCode.NO_DATE_INFORMATION);
        }
        if(dto.getToDate().isBefore(LocalDate.now())){
            throw new CustomException(ReservationErrorCode.NO_DATE_INFORMATION);
        }
        if(!dto.getToDate().isAfter(dto.getFromDate())){
            throw new CustomException(ReservationErrorCode.NO_DATE_INFORMATION);
        }
        if(ChronoUnit.DAYS.between(dto.getFromDate(), dto.getToDate()) > 30){
            throw new CustomException(ReservationErrorCode.RESERVATION_PERIOD_EXCEEDS_30_DAYS);
        }
        UserEntity userEntity = userRepository.findById(authenticationFacade.getLoginUserPk()).orElseThrow(() -> new CustomException(ReservationErrorCode.UNKNOWN_USER_PK));
        HotelEntity hotelEntity = hotelRepository.findById(dto.getHotelPk()).orElseThrow(() -> new CustomException(HotelErrorCode.NOT_EXIST_HOTEL));
        if(hotelEntity.getApproval() != 1){
            throw new CustomException(HotelErrorCode.NOT_OPERATING_HOTEL);
        }
        long totalPrice = 0;
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .hotelEntity(hotelEntity)
                .userEntity(userEntity)
                .resNum("R" + RandomCodeUtils.getRandomCode(5))
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .hotelEntity(hotelEntity)
                .userEntity(userEntity)
                .resStatus(0L)
                .build();
        reservationRepository.save(reservationEntity);
        for (DogInfo dogInfo : dto.getDogInfo()){
            DogSizeEntity dogSizeEntity = dogSizeRepository.findById(dogInfo.getSizePk()).orElseThrow(() -> new CustomException(ReservationErrorCode.UNKNOWN_DOG_SIZE_PK));
            HotelRoomInfoEntity hotelRoomInfoEntity = hotelRoomRepository.findById(dogInfo.getHotelRoomPk()).orElseThrow(() -> new CustomException(HotelErrorCode.NOT_EXIST_HOTEL_ROOM));
            if (dogSizeEntity.getSizePk() != hotelRoomInfoEntity.getDogSizeEntity().getSizePk()) {
                throw new CustomException(ReservationErrorCode.DOG_SIZE_AND_ROOM_SIZE_DO_NOT_MATCH);
            }
            ResDogInfoEntity resDogInfoEntity = ResDogInfoEntity.builder()
                    .dogNm(dogInfo.getDogNm())
                    .dogSizeEntity(dogSizeEntity)
                    .age(dogInfo.getDogAge())
                    .information(dogInfo.getInformation())
                    .build();
            resDogInfoRepository.save(resDogInfoEntity);
            ResComprehensiveInfoComposite composite = ResComprehensiveInfoComposite.builder()
                    .resPk(reservationEntity.getResPk())
                    .hotelRoomPk(hotelRoomInfoEntity.getHotelRoomPk())
                    .resDogPk(resDogInfoEntity.getResDogPk())
                    .build();
            ResComprehensiveInfoEntity resComprehensiveInfoEntity = ResComprehensiveInfoEntity.builder()
                    .composite(composite)
                    .reservationEntity(reservationEntity)
                    .hotelRoomInfoEntity(hotelRoomInfoEntity)
                    .resDogInfoEntity(resDogInfoEntity)
                    .build();
            resComprehensiveInfoRepository.save(resComprehensiveInfoEntity);
            HotelRoomDateProcDto hotelRoomDateProcDto = HotelRoomDateProcDto.builder()
                    .hotelRoomInfoEntity(hotelRoomInfoEntity)
                    .fromDate(dto.getFromDate())
                    .toDate(dto.getToDate())
                    .build();
            hotelResRoomRepository.updateHotelResRoomReservationCount(hotelRoomDateProcDto);
            totalPrice += dogInfo.getRoomAmount();
        }
        ResPaymentEntity resPaymentEntity = ResPaymentEntity.builder()
                .reservationEntity(reservationEntity)
                .resPaymentNum("P" + RandomCodeUtils.getRandomCode(5))
                .paymentAmount(totalPrice)
                .businessEntity(hotelEntity.getBusinessEntity())
                .userEntity(userEntity)
                .paymentStatus(1L)
                .build();
        resPaymentRepository.save(resPaymentEntity);

        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------예약 취소--------------------------------------------------------
    @Transactional(rollbackFor = Exception.class)
    public ResVo delHotelReservation(HotelReservationDelDto dto) {
        dto.setUserPk((int)authenticationFacade.getLoginUserPk());
        // 먼저 유저가 예약 했는지 셀렉트
        List<HotelReservationSelProcVo> procVo = reservationMapper.selHotelReservation(dto);
        if (procVo.size() == 0) {
            throw new CustomException(ReservationErrorCode.NO_RESERVATION_INFORMATION);  // 예약 정보 없음
        }
        for (HotelReservationSelProcVo vo : procVo) {
            dto.setResPk(vo.getResPk()); // 다 같은 pk
            dto.getResDogPkList().add(vo.getResDogPk());
        }
        List<Integer> roomPk = reservationMapper.selHotelRoomPk(dto);
        if (roomPk == null) {
            throw new CustomException(ReservationErrorCode.ROOM_PK_SELECT_FAILURE);
        }
        dto.setHotelRoomPk(roomPk);

        // 호텔방강아지테이블 삭제
        int affectedRows1 = reservationMapper.delHotelReservationInfo(dto);
        log.info("affectedRowsHotelReservationInfoDel : {}", affectedRows1);
        if (affectedRows1 == 0) {
            throw new CustomException(ReservationErrorCode.FAILED_TO_DELETE_HOTEL_ROOM_DOG_TABLE);
        }
        // 예약 강아지 테이블 삭제
        int affectedRows2 = reservationMapper.delHotelReservationDogInfo(dto);
        log.info("affectedRowsHotelDogInfoDel : {}", affectedRows2);
        if (affectedRows2 == 0) {
            throw new CustomException(ReservationErrorCode.FAILED_TO_DELETE_RESERVED_DOG_TABLE);
        }
        // 호텔 예약 테이블 삭제
        int affectedRows3 = reservationMapper.delHotelReservation(dto);
        log.info("affectedRowsHotelReservationDel : {}", affectedRows3);
        if (affectedRows3 == 0) {
            throw new CustomException(ReservationErrorCode.FAILED_TO_DELETE_HOTEL_RESERVATION_TABLE);
        }
        // 호텔방 관리테이블 업데이트
        // fromdate, todate를 다 가져와 dateRange로 변경
        List<LocalDate> dateRange = new ArrayList<>();
        for (HotelReservationSelProcVo vo : procVo) {
            LocalDate fromDate = vo.getFromDate();
            LocalDate toDate = vo.getToDate();
            while (!fromDate.isAfter(toDate)) {
                dateRange.add(fromDate);
                fromDate = fromDate.plusDays(1);
            } // 여러번 돌아도 같은값
            dto.setDateRange(dateRange);
        }
        HotelReservationUpdProc2Dto pDtoList = new HotelReservationUpdProc2Dto();
        pDtoList.setDate(dateRange);
        pDtoList.setHotelRoomPk(dto.getHotelRoomPk());
        int affectedRows4 = reservationMapper.updRemainedHotelRoom2(pDtoList);
        log.info("affectedRowsHotelRoomUpd : {}", affectedRows4);
        if (affectedRows4 == 0) {
            throw new CustomException(ReservationErrorCode.HOTEL_ROOM_MANAGEMENT_TABLE_UPDATE_FAILED);
        }
        return new ResVo(Const.SUCCESS);
    }
    //예약 취소 및 환불 jpa
    @Transactional(rollbackFor = Exception.class)
    public ResVo refundHotelReservation(HotelReservationDelDto dto) {
        UserEntity userEntity = userRepository.findById(authenticationFacade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReservationEntity reservationEntity = reservationRepository.findById(dto.getResPk())
                .orElseThrow(() -> new CustomException(ReservationErrorCode.NOT_EXIST_RESERVATION));
        reservationEntity.setResStatus(4L);
        RefundEntity refundEntity = RefundEntity.builder()
                .userEntity(userEntity)
                .reservationEntity(reservationEntity)
                .refundAmount(getRefundAmount(reservationEntity, reservationEntity.getResPaymentEntity().getPaymentAmount())  )
                .refundDate(LocalDateTime.now())
                .refundNum("R" + RandomCodeUtils.getRandomCode(5))
                .build();
        refundRepository.save(refundEntity);

        reservationEntity.getResPaymentEntity().setPaymentStatus(2L);
        ResComprehensiveInfoEntity resComprehensiveInfoEntity = resComprehensiveInfoRepository.findByReservationEntity(reservationEntity);
        HotelRoomDateProcDto hotelRoomDateProcDto = HotelRoomDateProcDto.builder()
                .hotelRoomInfoEntity(resComprehensiveInfoEntity.getHotelRoomInfoEntity())
                .fromDate(reservationEntity.getFromDate())
                .toDate(reservationEntity.getToDate())
                .build();

        hotelResRoomRepository.updateHotelResRoomRefundCount(hotelRoomDateProcDto);
        return new ResVo(1);

    }

    //--------------------------------------------------예약 정보---------------------------------------------------------
    public List<ResInfoVo> getUserReservation(int userPk, int page) {
        int perPage = Const.RES_LIST_COUNT_PER_PAGE;
        int pages = (page - 1) * Const.RES_LIST_COUNT_PER_PAGE;
        List<ResInfoVo> resInfoVos = reservationMapper.getUserReservation(userPk, perPage, pages);
        if(resInfoVos != null && resInfoVos.size() > 0){
            List<Integer> resPkList = resInfoVos
                    .stream()
                    .map(ResInfoVo::getResPk)
                    .collect(Collectors.toList());
            List<Integer> hotelPkList = resInfoVos
                    .stream()
                    .map(ResInfoVo::getHotelPK)
                    .collect(Collectors.toList());
            log.info("hotelPkList : {}", hotelPkList);

            List<ResDogInfoVo> resDogInfo = reservationMapper.getDogInfoReservation(resPkList);
            List<ResHotelPicVo> resHotelPicVos = reservationMapper.getHotelResPic(hotelPkList);
            if(resDogInfo != null && resDogInfo.size() > 0){
                resInfoVos.forEach(resInfoVo -> {
                    List<ResDogInfoVo> resDogInfoVoList = resDogInfo
                            .stream()
                            .filter(resDogInfoVo -> resInfoVo.getResPk() == resDogInfoVo.getResPk())
                            .collect(Collectors.toList());
                    resInfoVo.setResDogInfoVoList(resDogInfoVoList);
                });
            }

            if(resHotelPicVos != null && resHotelPicVos.size() > 0){
                resHotelPicVos.forEach(picVo ->
                        resInfoVos.stream()
                                .filter(vo -> picVo.getHotelPk() == vo.getHotelPK())
                                .findFirst()
                                .ifPresent(vo -> vo.setPic(picVo.getPic()))
                );
            }

            log.info("resHotelPicVos : {}", resHotelPicVos);
        }


        return resInfoVos;
    }
}
