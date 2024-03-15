package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.BusinessEntity;
import lombok.Data;

public class UserListVo {
    @Data
    public static class BusinessVo {


        private Long businessUserPk;
        private String userEmail;
        private String businessName;
        private String phoneNum;

        public static BusinessVo UserList(BusinessEntity businessEntity) {
            BusinessVo businessVo = new BusinessVo();
            businessVo.setBusinessUserPk(businessEntity.getBusinessPk());
            businessVo.setBusinessName(businessEntity.getBusinessName());
            businessVo.setUserEmail(businessEntity.getBusinessEmail());
            businessVo.setPhoneNum(businessEntity.getBusinessPhoneNum());
            return businessVo;
        }
    }
}