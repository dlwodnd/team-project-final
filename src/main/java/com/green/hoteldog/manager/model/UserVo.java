package com.green.hoteldog.manager.model;

import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import lombok.Data;

@Data
public class UserVo {
    private Long userPk;
    private String userEmail;
    private String upw;
    private String nickname;
    private String phoneNum;
    private String userAddress;
    private Long userStatus;
    private UserRoleEnum userRole;
    private String userNum;

    public static UserVo toDto(UserEntity userEntity) {
        UserVo userDto = new UserVo();
        userDto.setUserPk(userEntity.getUserPk());
        userDto.setUserEmail(userEntity.getUserEmail());
        userDto.setUpw(userEntity.getUpw());
        userDto.setNickname(userEntity.getNickname());
        userDto.setPhoneNum(userEntity.getPhoneNum());
        userDto.setUserAddress(userEntity.getUserAddress());
        userDto.setUserStatus(userEntity.getUserStatus());
        userDto.setUserRole(userEntity.getUserRole());
        userDto.setUserNum(userEntity.getUserNum());
        return userDto;
    }

}
