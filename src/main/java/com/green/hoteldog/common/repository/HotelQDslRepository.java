package com.green.hoteldog.common.repository;

import com.green.hoteldog.common.entity.DogSizeEntity;
import com.green.hoteldog.common.entity.HotelEntity;
import com.green.hoteldog.common.entity.HotelOptionEntity;
import com.green.hoteldog.hotel.model.HotelListSelDto;
import com.green.hoteldog.hotel.model.HotelListSelVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelQDslRepository {
    List<HotelEntity> getHotelAdvertiseList();

    List<HotelEntity> getHotelFilterList(HotelListSelDto dto);

}
