package com.green.hoteldog.common;

import com.green.hoteldog.common.entity.*;
import com.green.hoteldog.common.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
    private final HotelRepository hotelRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;
    private final HotelAdvertiseRepository hotelAdvertiseRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelResRoomRepository hotelResRoomRepository;
    private final PaymentAdRepository paymentAdRepository;
    private final UserRepository userRepository;


    @Scheduled(cron = "0 0/1 * * * *")
    public void checkHotelAdvertise() {
        List<HotelAdvertiseEntity> hotelAdvertiseEntities = hotelAdvertiseRepository.findAll();
        for (HotelAdvertiseEntity hotelAdvertiseEntity : hotelAdvertiseEntities) {
            if (hotelAdvertiseEntity.getAdStatus() == 1 && hotelAdvertiseEntity.getHotelAdvertiseEndDate().isBefore(LocalDateTime.now())) {
                paymentAdRepository.save(PaymentAdEntity.builder()
                        .hotelAdvertiseEntity(hotelAdvertiseEntity)
                        .hotelEntity(hotelAdvertiseEntity.getHotelEntity())
                        .paymentStatus(1L)
                        .cardNum(hotelAdvertiseEntity.getPaymentAdEntityList().get(0).getCardNum())
                        .userBirth(hotelAdvertiseEntity.getPaymentAdEntityList().get(0).getUserBirth())
                        .cardValidThru(hotelAdvertiseEntity.getPaymentAdEntityList().get(0).getCardValidThru())
                        .cardUserName(hotelAdvertiseEntity.getPaymentAdEntityList().get(0).getCardUserName())
                        .paymentAmount(hotelAdvertiseEntity.getPaymentAdEntityList().get(0).getPaymentAmount())
                        .paymentDate(LocalDateTime.now())
                        .build());
            }
            if (hotelAdvertiseEntity.getAdStatus() == 2 && hotelAdvertiseEntity.getHotelAdvertiseEndDate().isBefore(LocalDateTime.now())) {
                HotelEntity hotelEntity = hotelRepository.findById(hotelAdvertiseEntity.getHotelEntity().getHotelPk()).get();
                hotelEntity.setAdvertise(0L);
                hotelEntity.setHotelAdvertiseEntity(null);
            }
        }

        log.info("호텔 광고 갱신 스케쥴러 시작");
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void checkWithdrawalUser() {
        List<WithdrawalUserEntity> withdrawalUserEntities = withdrawalUserRepository.findAll();
        for (WithdrawalUserEntity withdrawalUserEntity : withdrawalUserEntities) {
            if(withdrawalUserEntity.getApplyDate().toLocalDate().equals(LocalDate.now())){
                UserEntity userEntity = withdrawalUserEntity.getUserEntity();
                userRepository.delete(userEntity);
            }
        }
        log.info("유저 탈퇴 스케쥴러 시작");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void refreshResRoomDate(){

    }
}
