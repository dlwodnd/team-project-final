package com.green.hoteldog.manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private List<UserInfo> userInfoList;
    private long totalPage;
}
