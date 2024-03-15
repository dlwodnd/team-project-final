package com.green.hoteldog.common.repository;

import com.green.hoteldog.hotel.model.HotelDetailInfoDto;
import com.green.hoteldog.hotel.model.HotelRoomInfoVo;
import com.green.hoteldog.user.models.HotelRoomDateProcDto;

import java.util.List;

public interface HotelResRoomQDslRepository {
    void updateAllHotelResRoomRefundCount(List<HotelRoomDateProcDto> hotelRoomDateProcDtoList);
    void updateHotelResRoomRefundCount(HotelRoomDateProcDto hotelRoomDateProcDto);
    void updateHotelResRoomReservationCount(HotelRoomDateProcDto hotelRoomDateProcDto);

    List<HotelRoomInfoVo> findResAbleHotelRoomInfo(long dogCount , HotelDetailInfoDto dto);
}
