package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.BusinessEntity;
import lombok.Data;

@Data
public  class BusinessVo {
    private Long businessPk;
    private UserVo userDto;
    private String accountNumber;
    private String bankNm;
    private Long accountStatus;

    public static BusinessVo toDto(BusinessEntity businessEntity) {
        BusinessVo businessDto = new BusinessVo();
        businessDto.setBusinessPk(businessEntity.getBusinessPk());
        /*businessDto.setUserDto(UserVo.toDto(businessEntity.getUserEntity()));*/
        return businessDto;
    }
}
