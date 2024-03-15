package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.UserEntity;
import lombok.Data;

@Data

public class UserInfo {



        private Long userPk;
        private String userNum;
        private String userEmail;
        private String nickname;
        private String userAddress;
        private String phoneNum;

        public static UserInfo UserList2(UserEntity userEntity) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserPk(userEntity.getUserPk());
            userInfo.setUserNum(userEntity.getUserNum());
            userInfo.setUserEmail(userEntity.getUserEmail());
            userInfo.setNickname(userEntity.getNickname());
            userInfo.setUserAddress(userEntity.getUserAddress());
            userInfo.setPhoneNum(userEntity.getPhoneNum());
            return userInfo;
    }
}
