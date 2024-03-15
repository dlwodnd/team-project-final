package com.green.hoteldog.user;

import com.green.hoteldog.user.models.BusinessUserSignupDto;
import com.green.hoteldog.business_user.model.HotelInsDto;
import com.green.hoteldog.common.utils.RedisUtil;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.exceptions.CustomException;
import com.green.hoteldog.exceptions.UserErrorCode;
import com.green.hoteldog.user.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "유저 관련 처리")
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    private final RedisUtil redisUtil;

    //--------------------------------------------------유저 로그아웃-----------------------------------------------------
    @PostMapping("/signout")
    @Operation(summary = "유저 로그아웃", description = "로그아웃 처리 및 쿠키삭제")
    public ResVo postSignout(HttpServletResponse response) {
        return service.signout(response);
    }

    //--------------------------------------------------유저 회원가입-----------------------------------------------------
    @PostMapping("/signup")
    @Operation(summary = "유저 회원가입", description = "유저 회원가입 처리")
    public ResVo userSignup(@RequestBody @Valid UserSignupDto dto) {
        ResVo vo = new ResVo(0);
        if (dto.getEmailResponseVo().getResult() == 0) {
            throw new CustomException(UserErrorCode.NOT_CERTIFICATION_EMAIL);
        }
        if (dto.getEmailResponseVo().getResult() == 1) {
            vo = service.userSignup(dto);
        }
        if (vo.getResult() == 1) {
            redisUtil.deleteData(dto.getEmailResponseVo().getEmail());
        }
        return vo;
    }

    //-------------------------------------------------- 일반 유저 로그인-------------------------------------------------------
    @PostMapping("/signin")
    @Operation(summary = "유저 로그인", description = "유저 로그인 처리")
    public UserSigninVo userSignin(HttpServletResponse response, HttpServletRequest request, @RequestBody @Valid UserSigninDto dto) {
        return service.userSignin(response, request, dto);
    }

    //--------------------------------------------------닉네임 중복체크---------------------------------------------------
    @GetMapping("/nickname-check")
    @Operation(summary = "닉네임 체크")
    public ResVo nicknameCheck(String nickname) {
        return service.checkNickname(nickname);
    }

    //------------------------------------------------유저 정보 불러오기---------------------------------------------------
    @PostMapping("/info")
    @Operation(summary = "회원정보 불러오기")
    public UserInfoVo userInfo(@RequestBody UserInfoDto dto) {
        return service.getUserInfo(dto);
    }

    //--------------------------------------------------유저 정보 수정----------------------------------------------------
    @PutMapping("/info")
    @Operation(summary = "유저 정보수정")
    public ResVo updUserInfo(@RequestBody UserUpdateDto dto) {
        return service.updUserInfo(dto);
    }

    //-----------------------------------------------리프레쉬 토큰 재발급--------------------------------------------------
    @GetMapping("/refresh-token")
    public RefreshTokenVo getRefreshToken(HttpServletRequest request) {
        return service.getRefreshToken(request);
    }

    //-----------------------------------------------------3차 추가 기능-------------------------------------------------
    // 사업자 유저 로그인
    @PostMapping("/business/signin")
    @Operation(summary = "사업자 로그인", description = "사업자 로그인 처리")
    public BusinessSigninVo businessSignin(HttpServletResponse response, HttpServletRequest request, @RequestBody @Valid UserSigninDto dto) {
        return service.businessSignin(response, request, dto);
    }
    // 사업자 유저 회원가입
    @PostMapping("/business/signup")
    @Operation(summary = "사업자 회원가입", description = "사업자 회원가입 처리")
    public ResVo postBusinessUser(@RequestPart BusinessUserSignupDto businessUserDto,
                                  @RequestPart HotelInsDto hotelDto,
                                  @RequestPart(required = false) MultipartFile businessCertificationFile,
                                  @RequestPart(required = false) List<MultipartFile> hotelPics){
        hotelDto.setBusinessCertificationFile(businessCertificationFile);
        hotelDto.setHotelPics(hotelPics);
        log.info("businessCertificationFile : {}", businessCertificationFile);
        log.info("hotelPics : {}", hotelPics);
        log.info("businessUserDto : {}", businessUserDto);
        log.info("hotelDto : {}", hotelDto);
        /*if (hotelPics.size() >5){
            throw new CustomException(UserErrorCode.HOTEL_PICS_SIZE_ERROR);
        }*/

        return service.insBusinessUser(businessUserDto, hotelDto);
    }
    // 유저 회원 탈퇴
    @PostMapping("/withdrawal")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 처리")
    public ResVo postUserWithdrawal() {
        return service.userWithdrawal();
    }

    // 유저 회원 탈퇴 철회
    @PostMapping("/withdrawal/cancel")
    @Operation(summary = "회원 탈퇴 철회", description = "회원 탈퇴 철회 처리")
    public ResVo postUserWithdrawalCancel() {
        return service.userWithdrawalCancel();
    }

    // 일괄 환불 출력 기능
    @GetMapping("/reservation/refund")
    @Operation(summary = "일괄 환불 출력", description = "일괄 환불 출력")
    public List<ResRefundInfoVo> getRefundList() {
        return service.getRefundList();
    }

    // 일괄 환불 기능
    @PostMapping("/reservation/refund")
    @Operation(summary = "일괄 환불", description = "일괄 환불")
    public ResVo postRefund(@RequestBody List<ResRefundDto> list) {
        return service.postRefund(list);
    }


    /*//최종 관리자 회원가입
    @PostMapping("/admin/signup")
    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입 처리")
    public ResVo postAdminSignup(@RequestBody @Valid AdminSignupDto dto) {
        return service.adminSignup(dto);
    }

    //최종 관리자 로그인
    @PostMapping("/admin/signin")
    @Operation(summary = "관리자 로그인", description = "관리자 로그인 처리")
    public AdminSigninVo adminSignin(HttpServletResponse response, HttpServletRequest request, @RequestBody @Valid UserSigninDto dto) {
        return service.adminSignin(response, request, dto);
    }
*/
}