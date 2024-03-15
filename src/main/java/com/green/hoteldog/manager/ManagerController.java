package com.green.hoteldog.manager;

import com.green.hoteldog.business_user.model.BusinessUserHotelVo;
import com.green.hoteldog.manager.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {
    private final ManagerService service;


//    @GetMapping("/userList")
//    public List<UserEntity> getUserList() {
//
//        return service.allUsers();
//    }
//}


//    @GetMapping("/userList")
//    @Operation(summary = "유저목록", description = "사업자 유저 1 일반유저 0 보냄됨")
//    public List<UserListVo.BusinessVo> getUserList(@RequestParam(required = false) Integer accountStatus ,@PageableDefault(page = 1,size = 2) Pageable pageable) {
//        if (accountStatus != null && accountStatus == 1) {
//            // accountStatus가 1인 경우 사업자 유저 목록 반환
//            List<Long> businessUserPks = service.getBusinessUserPks();
//            List<UserListVo.BusinessVo> businessUsers = service.getUsersPks(businessUserPks,pageable);
//            return businessUsers;
//        } else if (accountStatus != null && accountStatus != 1) {
//            List<Long> normalUserPks = service.getNormalUserPks();
//            List<UserListVo.BusinessVo> normalUsers = service.getUsersPks(normalUserPks,pageable);
//            // 그 외의 경우에는 모든 유저 목록 반환
//            return normalUsers;
//        }
//        return null;
//    }

    /*@GetMapping("/userList")
    @Operation(summary = "유저목록", description = "사업자 유저 1 일반유저 0 보냄됨")
    public List<UserListVo2> getUserList(@RequestParam(required = false) UserRoleEnum userRole, @PageableDefault(page = 1, size = 2) Pageable pageable) {
        if (userRole != null && userRole == UserRoleEnum.USER) {
            // accountStatus가 1인 경우 사업자 유저 목록 반환
            List<UserListVo2> normalUsers = service.getUsers(UserRoleEnum.USER, pageable);
            return normalUsers;

        } else if (userRole != null && userRole == UserRoleEnum.BUSINESS_USER) {
            List<UserListVo2> businessUsers = service.businessUsers(UserRoleEnum.BUSINESS_USER, pageable);

            // 그 외의 경우에는 모든 유저 목록 반환
            return businessUsers;
        }
        return null;
    }*/
    @GetMapping("/userList")
    @Operation(summary = "유저 목록", description = "일반 유저 목록")
    public UserInfoVo getUserList(@PageableDefault(page = 1, size = 15) Pageable pageable) {
        return service.getUsers(pageable);
    }
    @GetMapping("/hotel/info")
    @Operation(summary = "호텔 정보", description = "호텔 정보")
    public BusinessUserHotelVo getHotelInfo(long hotelPk) {
        return service.getHotelInfo(hotelPk);
    }
    @GetMapping("/businessUserList")
    @Operation(summary = "사업자 유저 목록", description = "사업자 유저 목록")
    public BusinessUserInfoVo getBusinessUserList(@PageableDefault(page = 1, size = 15) Pageable pageable) {
        return service.businessUsers(pageable);
    }
   // 대기 유저 사업자유저 전환
   @Operation(summary = "대기 유저 사업자유저 전환", description = "보내야될 비지니스 pk와  대기자가 0인AccountStatus를 1보내면됨 ")
   @PutMapping("/BusinessTransform")
   public void BusinessTransform(@RequestParam long businessPk) {
       service.updateAccountStatusTo1(businessPk);
   }


    // 호텔 목록을 가져오는 메서드
    @GetMapping("/hotelList")
    @Operation(summary = "호텔목록", description = "호텔목록")
    public HotelInfoListVo getManagementHotelList(@PageableDefault(page = 1,size = 15) Pageable pageable) {
        return service.getManagementHotelList(pageable);
    }

    // 승인 대기 호텔목록 가져오는 메서드
    @GetMapping("/hotelAccountStatus")
    @Operation(summary = "승인 대길 호텔", description = "사이즈 숫자 뒤에, 부분부터 쇼트 대괄호하나 지우셈")

    public HotelInfoListVo getManagementApprovalHotelList(@PageableDefault(page = 1,size = 15) Pageable pageable) {
        return service.getManagementHotelByBusinessEntity_AccountStatus(pageable);
    }

    // 광고 승인 목록
//    @GetMapping("/adHotelList/approval")
//    @Operation(summary = "광고 승인목록", description = "광고 승인 목록처리")
//    public List<ApprovalAdListVo> getApprovalAdList(@PageableDefault(page = 1,size = 2) Pageable pageable) {
//        return service.getApprovalAdList( pageable);
//    }

//    @PatchMapping("/approval")
//    @Operation(summary = "광고 승인", description = "광고 승인할 호텔pk보내면됨")
//
//    public void approvalAd(@RequestParam long hotelPk) {
//        service.updateHotelAdvertiseEntityBySignStatus( hotelPk);
//    }
//
//    @PatchMapping("/adRefuse")
//    @Operation(summary = "광고 승인 거절", description = "광고 승인 거절 cancelReason 사유와 hotel pk보내면됨")
//
//    public void updateHotelAdvertiseEntityBySignStatusAndCancelReason( String cancelReason, long hotelPk){
//        service.updateHotelAdvertiseEntityBySignStatusAndCancelReason(cancelReason, hotelPk);
//    }


    @PatchMapping("/hotelApproval")
    @Operation(summary = "호텔 승인", description = "호텔 승인할 pk 보내면됨")
    public void updateHotelEntityByApproval(@RequestParam long hotelPk) {
        service.updateHotelEntityByApproval(hotelPk);
    }


    @PatchMapping("/suspendApproval")
    @Operation(summary = "호텔 중지 신청 승인", description = "호텔 중지 신청할 pk보내면됨")
    public void updateHotelSuspendedEntityBySignStatus(@RequestParam long hotelPk) {
        service.updateHotelSuspendedEntityBySignStatus(hotelPk);
    }


    @PatchMapping("/suspendRefuse")
    @Operation(summary = "호텔 중지 신청 거절", description = "호텔 중지 거절할 pk보와 그사유 suspendedReason  보내면됨")
    public void updateHotelAdvertiseEntityBySignStatusAndCancelReason(@RequestParam("suspendedReason") String suspendedReason, @RequestParam("hotelPk") Long hotelPk) {
        service.updateHotelAdvertiseEntityBySignStatusAndCancel( suspendedReason, hotelPk);
    }








//    @PatchMapping("/suspendRefusee")
//    @Operation(summary = "호텔 중지 신청 거절1", description = "호텔 중지 신청 거절 사유까지 처리")
//    public void updateHotelSuspendedEntityBySignStatusAndSuspendedReason( String suspendedReason,@RequestParam long hotelPk){
//        service.updateHotelSuspendedEntityBySignStatusAndSuspendedReason( suspendedReason, hotelPk);
//    }
}




