package com.green.hoteldog.user;

import com.green.hoteldog.common.utils.CommonUtils;
import com.green.hoteldog.exceptions.*;
import com.green.hoteldog.hotel.model.DogSizeEa;
import com.green.hoteldog.user.models.BusinessUserSignupDto;
import com.green.hoteldog.business_user.model.HotelInsDto;
import com.green.hoteldog.common.AppProperties;
import com.green.hoteldog.common.Const;
import com.green.hoteldog.common.entity.*;
import com.green.hoteldog.common.entity.composite.HotelOptionComposite;
import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import com.green.hoteldog.common.repository.*;
import com.green.hoteldog.common.utils.CookieUtils;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.common.utils.MyFileUtils;
import com.green.hoteldog.common.utils.RandomCodeUtils;
import com.green.hoteldog.security.AuthenticationFacade;
import com.green.hoteldog.security.JwtTokenProvider;
import com.green.hoteldog.security.MyUserDetails;
import com.green.hoteldog.security.MyPrincipal;
import com.green.hoteldog.user.models.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.green.hoteldog.common.utils.CommonUtils.getRefundAmount;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookie;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final WithdrawalUserRepository withdrawalUserRepository;
    private final MyFileUtils myFileUtils;
    private final HotelOptionRepository hotelOptionRepository;
    private final HotelOptionInfoRepository hotelOptionInfoRepository;
    private final DogSizeRepository dogSizeRepository;
    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final ReservationRepository reservationRepository;
    private final ResPaymentRepository resPaymentRepository;
    private final RefundRepository refundRepository;
    private final ResComprehensiveInfoRepository resComprehensiveInfoRepository;
    private final HotelResRoomRepository hotelResRoomRepository;
    private final ManagerRepository managerRepository;


    //--------------------------------------------------유저 회원가입-----------------------------------------------------
    @Transactional(rollbackFor = {Exception.class})
    public ResVo userSignup(UserSignupDto dto) {
        log.info("UserSignupDto : {}", dto);
        UserEntity userEntity = UserEntity.builder()
                .userStatus(0L)
                .userNum("U" + RandomCodeUtils.getRandomCode(5))
                .userEmail(dto.getEmailResponseVo().getEmail())
                .upw(passwordEncoder.encode(dto.getUpw()))
                .nickname(dto.getNickname())
                .phoneNum(dto.getPhoneNum())
                .userAddress(dto.getAddressEntity().getAddressName() + " " + dto.getAddressEntity().getDetailAddress())
                .build();
        userRepository.save(userEntity);

        UserWhereEntity userWhereEntity = UserWhereEntity.builder()
                .userEntity(userEntity)
                .x(dto.getAddressEntity().getX())
                .y(dto.getAddressEntity().getY())
                .addressName(dto.getAddressEntity().getAddressName())
                .userEntity(userEntity)
                .zoneNum(dto.getAddressEntity().getZoneNum())
                .region1DepthName(dto.getAddressEntity().getRegion1DepthName())
                .region2DepthName(dto.getAddressEntity().getRegion2DepthName())
                .region3DepthName(dto.getAddressEntity().getRegion3DepthName())
                .detailAddress(dto.getAddressEntity().getDetailAddress())
                .build();
        userEntity.setUserWhereEntity(userWhereEntity);
        return new ResVo(1);
    }

    //--------------------------------------------------유저 로그인-------------------------------------------------------
    @Transactional
    public UserSigninVo userSignin(HttpServletResponse response, HttpServletRequest request, UserSigninDto dto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserEmail(dto.getUserEmail());

        UserEntity userEntity = userEntityOptional.orElseThrow(() -> new CustomException(UserErrorCode.UNKNOWN_EMAIL_ADDRESS));
        if (userEntity.getUserRole().equals(UserRoleEnum.WITHDRAWAL)) {
            throw new CustomException(UserErrorCode.WITHDRAWAL_USER);
        }
        if (!passwordEncoder.matches(dto.getUpw(), userEntity.getUpw())) {
            throw new CustomException(UserErrorCode.MISS_MATCH_PASSWORD);
        }
        Optional<WithdrawalUserEntity> withdrawalUserEntityOptional = withdrawalUserRepository.findById(userEntity.getUserPk());


        MyPrincipal myPrincipal = MyPrincipal.builder()
                .userPk(userEntity.getUserPk())
                .build();
        myPrincipal.getRoles().add(userEntity.getUserRole().name());
        String at = tokenProvider.generateAccessToken(myPrincipal);
        //엑서스 토큰 값 받아오기
        String rt = tokenProvider.generateRefreshToken(myPrincipal);
        //리프레쉬 토큰 값 받아오기
        /*List<Integer> dogSizeList = mapper.selUserDogSize(userInfo.getUserPk());*/

        int rtCookieMaxAge = (int) appProperties.getJwt().getRefreshTokenExpiry() / 1000;
        cookie.deleteCookie(response, "rt");
        cookie.setCookie(response, "rt", rt, rtCookieMaxAge);

        List<UserDogEntity> dogEntityList = userEntity.getUserDogEntities();


        UserSigninVo vo = new UserSigninVo();
        vo.setUserRole(userEntity.getUserRole().name());
        vo.setUserPk(userEntity.getUserPk());
        vo.setAccessToken(at);
        vo.setNickname(userEntity.getNickname());
        vo.setDepthName(userEntity.getUserWhereEntity().getRegion1DepthName());
        if (!dogEntityList.isEmpty()) {
            Map<Long ,Long> sizeCountMap = new HashMap<>();
            for(UserDogEntity userDogEntity : dogEntityList){
                sizeCountMap.put(userDogEntity.getDogSizeEntity().getSizePk(),sizeCountMap.getOrDefault(userDogEntity.getDogSizeEntity().getSizePk(),0L) + 1);
            }
            vo.setDogInfo(sizeCountMap.entrySet().stream()
                    .map(item -> DogSizeEa.builder().dogSize(item.getKey()).dogCount(item.getValue()).build())
                    .collect(Collectors.toList())
            );
        }
        return vo;
    }

    //--------------------------------------------------유저 닉네임 체크--------------------------------------------------
    public ResVo checkNickname(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname);
        if (userEntity != null) {
            throw new CustomException(UserErrorCode.ALREADY_USED_NICKNAME);
        }

        return new ResVo(1);
    }

    //--------------------------------------------------유저 로그아웃-----------------------------------------------------
    public ResVo signout(HttpServletResponse response) {
        cookie.deleteCookie(response, "rt");
        return new ResVo(1);
    }

    //--------------------------------------------------유저 정보 조회----------------------------------------------------
    public UserInfoVo getUserInfo(UserInfoDto dto) {
        Optional<UserEntity> optionalUser = userRepository.findById(facade.getLoginUserPk());
        UserEntity userEntity = optionalUser.orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));

        if (!passwordEncoder.matches(dto.getUpw(), userEntity.getUpw())) {
            throw new CustomException(UserErrorCode.MISS_MATCH_PASSWORD);
        }
        UserInfoVo vo = new UserInfoVo();

        vo.setUserPk(userEntity.getUserPk());
        vo.setUserEmail(userEntity.getUserEmail());
        vo.setNickname(userEntity.getNickname());
        vo.setPhoneNum(userEntity.getPhoneNum());
        vo.setUserAddress(userEntity.getUserAddress());
        vo.setAddressEntity(UserAddressInfo.builder()
                .userPk(userEntity.getUserPk())
                .x(userEntity.getUserWhereEntity().getX())
                .y(userEntity.getUserWhereEntity().getY())
                .addressName(userEntity.getUserWhereEntity().getAddressName())
                .detailAddress(userEntity.getUserWhereEntity().getDetailAddress())
                .zoneNum(userEntity.getUserWhereEntity().getZoneNum())
                .region1DepthName(userEntity.getUserWhereEntity().getRegion1DepthName())
                .region2DepthName(userEntity.getUserWhereEntity().getRegion2DepthName())
                .region3DepthName(userEntity.getUserWhereEntity().getRegion3DepthName())
                .build());
        return vo;
    }

    //--------------------------------------------------유저 정보 업데이트-------------------------------------------------
    @Transactional
    public ResVo updUserInfo(UserUpdateDto dto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(facade.getLoginUserPk());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        userEntity.setUserAddress(dto.getAddressEntity().getAddressName() + " " + dto.getAddressEntity().getDetailAddress());
        userEntity.setNickname(dto.getNickname());
        UserWhereEntity userWhereEntity = userEntity.getUserWhereEntity();
        userWhereEntity.setX(dto.getAddressEntity().getX());
        userWhereEntity.setY(dto.getAddressEntity().getY());
        userWhereEntity.setAddressName(dto.getAddressEntity().getAddressName());
        userWhereEntity.setZoneNum(dto.getAddressEntity().getZoneNum());
        userWhereEntity.setRegion1DepthName(dto.getAddressEntity().getRegion1DepthName());
        userWhereEntity.setRegion2DepthName(dto.getAddressEntity().getRegion2DepthName());
        userWhereEntity.setRegion3DepthName(dto.getAddressEntity().getRegion3DepthName());
        userWhereEntity.setDetailAddress(dto.getAddressEntity().getDetailAddress());
        userEntity.setUserWhereEntity(userWhereEntity);
        userEntity.setPhoneNum(dto.getPhoneNum());
        return new ResVo(Const.SUCCESS);
    }

    //-------------------------------------------------리프레쉬 토큰 재발급------------------------------------------------
    public RefreshTokenVo getRefreshToken(HttpServletRequest request) {
        RefreshTokenVo vo = new RefreshTokenVo();
        Cookie userCookie = cookie.getCookie(request, "rt");
        if (userCookie == null) {
            throw new CustomException(AuthorizedErrorCode.NOT_EXISTS_REFRESH_TOKEN);
        }
        String token = userCookie.getValue();
        if (!tokenProvider.isValidateToken(token)) {
            throw new CustomException(AuthorizedErrorCode.REFRESH_TOKEN_IS_EXPIRATION);
        }
        MyUserDetails myUserDetails = (MyUserDetails) tokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myprincipal = myUserDetails.getMyPrincipal();
        String at = tokenProvider.generateAccessToken(myprincipal);
        vo.setUserPk((int) facade.getLoginUserPk());
        vo.setAccessToken(at);
        return vo;
    }

    // 사업자 유저 로그인
    @Transactional
    public BusinessSigninVo businessSignin(HttpServletResponse response, HttpServletRequest request, UserSigninDto dto) {
        BusinessEntity businessEntity = businessRepository.findByBusinessEmail(dto.getUserEmail()).orElseThrow(() -> new CustomException(UserErrorCode.UNKNOWN_EMAIL_ADDRESS));
        if(businessEntity.getRole().equals(UserRoleEnum.WITHDRAWAL)){
            throw new CustomException(UserErrorCode.WITHDRAWAL_USER);
        }
        if (!passwordEncoder.matches(dto.getUpw(), businessEntity.getBusinessPw())) {
            throw new CustomException(UserErrorCode.MISS_MATCH_PASSWORD);
        }


        MyPrincipal myPrincipal = MyPrincipal.builder()
                .userPk(businessEntity.getBusinessPk())
                .build();
        myPrincipal.getRoles().add(businessEntity.getRole().name());
        String at = tokenProvider.generateAccessToken(myPrincipal);
        //엑서스 토큰 값 받아오기
        String rt = tokenProvider.generateRefreshToken(myPrincipal);
        //리프레쉬 토큰 값 받아오기
        /*List<Integer> dogSizeList = mapper.selUserDogSize(userInfo.getUserPk());*/

        int rtCookieMaxAge = (int) appProperties.getJwt().getRefreshTokenExpiry() / 1000;
        cookie.deleteCookie(response, "rt");
        cookie.setCookie(response, "rt", rt, rtCookieMaxAge);

        return BusinessSigninVo.builder()
                .businessPk(businessEntity.getBusinessPk())
                .businessName(businessEntity.getBusinessName())
                .accessToken(at)
                .userRole(businessEntity.getRole().name())
                .build();
    }

    //사업자 유저 회원가입
    @Transactional
    public ResVo insBusinessUser(BusinessUserSignupDto businessUserDto, HotelInsDto hotelDto) {
        if (businessRepository.findByBusinessEmail(hotelDto.getBusinessNum()).isPresent()){
            throw new CustomException(HotelErrorCode.ALREADY_USED_BUSINESS_NUM);
        }

        //사업자 엔티티 등록
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setBusinessEmail(businessUserDto.getEmailResponseVo().getEmail());
        businessEntity.setBusinessPw(passwordEncoder.encode(businessUserDto.getUpw()));
        businessEntity.setBusinessPhoneNum(businessUserDto.getPhoneNum());
        businessEntity.setRole(UserRoleEnum.BUSINESS_USER);
        businessEntity.setBusinessStatus(1L);
        businessEntity.setBusinessName(businessUserDto.getBusinessName());
        businessRepository.save(businessEntity);
        //사업자 엔티티 등록

        //호텔 등록
        HotelEntity hotelEntity = HotelEntity.builder()
                .hotelNm(hotelDto.getHotelNm())
                .businessEntity(businessEntity)
                .hotelDetailInfo(hotelDto.getHotelDetailInfo())
                .hotelFullAddress(hotelDto.getHotelAddressInfo().getAddressName() + " " + hotelDto.getHotelAddressInfo().getDetailAddress())
                .businessNum(hotelDto.getBusinessNum())
                .hotelCall(hotelDto.getHotelCall())
                .businessCertificate("x")
                .advertise(0L)
                .approval(0L)
                .signStatus(0L)
                .hotelNum("H" + RandomCodeUtils.getRandomCode(5))
                .build();
        hotelRepository.save(hotelEntity);
        String hotelCertificationFile = "";
        if (hotelDto.getBusinessCertificationFile() != null) {
            String target = "/manager/hotel/" + businessEntity.getBusinessPk();
            hotelCertificationFile = myFileUtils.transferTo(hotelDto.getBusinessCertificationFile(), target);
            hotelEntity.setBusinessCertificate(hotelCertificationFile);

        }



        HotelWhereEntity hotelWhereEntity = HotelWhereEntity.builder()
                .x(hotelDto.getHotelAddressInfo().getX())
                .y(hotelDto.getHotelAddressInfo().getY())
                .addressName(hotelDto.getHotelAddressInfo().getAddressName())
                .zoneNum(hotelDto.getHotelAddressInfo().getZoneNum())
                .detailAddress(hotelDto.getHotelAddressInfo().getDetailAddress())
                .region1DepthName(hotelDto.getHotelAddressInfo().getRegion1DepthName())
                .region2DepthName(hotelDto.getHotelAddressInfo().getRegion2DepthName())
                .region3DepthName(hotelDto.getHotelAddressInfo().getRegion3DepthName())
                .hotelEntity(hotelEntity)
                .build();
        hotelEntity.setHotelWhereEntity(hotelWhereEntity);
        if(hotelDto.getHotelPics() != null){
            List<HotelPicEntity> hotelPicEntityList = new ArrayList<>();
            for (MultipartFile file : hotelDto.getHotelPics()) {
                String target = "/hotel/" + hotelEntity.getHotelPk();
                String hotelPicFile = myFileUtils.transferTo(file, target);
                HotelPicEntity hotelPicsEntity = HotelPicEntity.builder()
                        .hotelEntity(hotelEntity)
                        .pic(hotelPicFile)
                        .build();
                hotelPicEntityList.add(hotelPicsEntity);
            }
            hotelEntity.setHotelPicEntity(hotelPicEntityList);
        }



        List<HotelOptionEntity> hotelOptionEntityList = hotelOptionRepository.findAllById(hotelDto.getHotelOption());

        List<HotelOptionInfoEntity> hotelOptionInfoEntityList = new ArrayList<>();
        for (HotelOptionEntity hotelOptionEntity : hotelOptionEntityList) {
            HotelOptionComposite hotelOptionComposite = HotelOptionComposite.builder()
                    .hotelPk(hotelEntity.getHotelPk())
                    .optionPk(hotelOptionEntity.getOptionPk()).build();

            HotelOptionInfoEntity hotelOptionInfoEntity = HotelOptionInfoEntity.builder()
                    .composite(hotelOptionComposite)
                    .hotelEntity(hotelEntity)
                    .hotelOptionEntity(hotelOptionEntity)
                    .build();
            hotelOptionInfoEntityList.add(hotelOptionInfoEntity);
        }
        hotelOptionInfoRepository.saveAll(hotelOptionInfoEntityList);
        //호텔 등록


        //호텔 방 자동 등록
        dogSizeRepository.findAll();
        List<HotelRoomInfoEntity> hotelRoomInfoEntityList = new ArrayList<>();
        HotelRoomInfoEntity smallRoomInfo = HotelRoomInfoEntity.builder()
                .dogSizeEntity(dogSizeRepository.findById(1L).get())
                .hotelEntity(hotelEntity)
                .hotelRoomNm("소형견(7kg 이하)")
                .discountPer("0")
                .hotelRoomCost(0L)
                .maximum(1L)
                .hotelRoomEa(0L)
                .build();
        hotelRoomInfoEntityList.add(smallRoomInfo);
        HotelRoomInfoEntity mediumRoomInfo = HotelRoomInfoEntity.builder()
                .dogSizeEntity(dogSizeRepository.findById(2L).get())
                .maximum(1L)
                .discountPer("0")
                .hotelRoomCost(0L)
                .hotelRoomEa(0L)
                .hotelEntity(hotelEntity)
                .hotelRoomNm("중형견(15kg 이하)")
                .build();
        hotelRoomInfoEntityList.add(mediumRoomInfo);
        HotelRoomInfoEntity largeRoomInfo = HotelRoomInfoEntity.builder()
                .dogSizeEntity(dogSizeRepository.findById(3L).get())
                .maximum(1L)
                .discountPer("0")
                .hotelRoomCost(0L)
                .hotelRoomEa(0L)
                .hotelEntity(hotelEntity)
                .hotelRoomNm("대형견(40kg 이하)")
                .build();
        hotelRoomInfoEntityList.add(largeRoomInfo);
        HotelRoomInfoEntity superLargeRoomInfo = HotelRoomInfoEntity.builder()
                .dogSizeEntity(dogSizeRepository.findById(4L).get())
                .maximum(1L)
                .hotelRoomEa(0L)
                .hotelRoomCost(0L)
                .discountPer("0")
                .hotelEntity(hotelEntity)
                .hotelRoomNm("초대형견(40kg 초과)")
                .build();
        hotelRoomInfoEntityList.add(superLargeRoomInfo);
        hotelRoomRepository.saveAll(hotelRoomInfoEntityList);
        //호텔 방 자동 등록
        List<LocalDate> getDatesThisYear = CommonUtils.getDatesThisYear();
        for(HotelRoomInfoEntity hotelRoomInfoEntity : hotelRoomInfoEntityList){
            for(LocalDate localDate : getDatesThisYear){
                HotelResRoomEntity hotelResRoomEntity = new HotelResRoomEntity();
                hotelResRoomEntity.setHotelRoomInfoEntity(hotelRoomInfoEntity);
                hotelResRoomEntity.setHotelLeftEa(0L);
                hotelResRoomEntity.setRoomDate(localDate);
                hotelResRoomRepository.save(hotelResRoomEntity);
            }
        }


        return new ResVo(1);
    }

    @Transactional
    public List<ResRefundInfoVo> getRefundList() {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk()).orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        List<ReservationEntity> reservationEntityList = reservationRepository.findByUserEntityAndResStatus(userEntity, 0);
        List<ResPaymentEntity> resPaymentEntityList = reservationRepository.getResPaymentList(reservationEntityList);
        List<ResComprehensiveInfoEntity> resComprehensiveInfoEntityList = resComprehensiveInfoRepository.findAllByReservationEntityIn(reservationEntityList);

        return reservationEntityList.isEmpty() ? new ArrayList<>() : reservationEntityList.stream()
                .map(item -> {
                    long paymentAmount = getPaymentAmount(resPaymentEntityList, item);
                    long refundAmount = getRefundAmount(item, paymentAmount);
                    return ResRefundInfoVo.builder()
                            .hotelPk(item.getHotelEntity().getHotelPk())
                            .resPk(item.getResPk())
                            .hotelNm(item.getHotelEntity().getHotelNm())
                            .resNum(item.getResNum())
                            .toDate(item.getToDate().toString())
                            .fromDate(item.getFromDate().toString())
                            .paymentAmount(paymentAmount)
                            .refundAmount(refundAmount)
                            .build();
                }).collect(Collectors.toList());
    }
    @Transactional
    public ResVo postRefund(List<ResRefundDto> list){
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk()).orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        List<ResPaymentEntity> resPaymentEntityList = resPaymentRepository.findAllByUserEntityAndPaymentStatus(userEntity , 1);
        List<ReservationEntity> reservationEntityList = reservationRepository.findByUserEntityAndResStatus(userEntity, 0);
        List<HotelRoomDateProcDto> hotelRoomDateProcDtoList = new ArrayList<>();

        List<RefundEntity> refundEntityList = new ArrayList<>();
        for(ResRefundDto resRefundDto : list){
            RefundEntity refundEntity = RefundEntity.builder()
                    .userEntity(userEntity)
                    .reservationEntity(reservationRepository.findById(resRefundDto.getResPk()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_EXISTS_RESERVATION)))
                    .refundNum("C" + RandomCodeUtils.getRandomCode(5))
                    .refundDate(LocalDateTime.now())
                    .refundAmount(resRefundDto.getRefundAmount()).build();
            refundEntityList.add(refundEntity);
        }
        refundRepository.saveAll(refundEntityList);
        for(ResPaymentEntity resPaymentEntity : resPaymentEntityList){
            resPaymentEntity.setPaymentStatus(2L);
        }
        for(ReservationEntity reservationEntity : reservationEntityList){
            resComprehensiveInfoRepository.findAllByReservationEntity(reservationEntity).forEach(item -> {
                HotelRoomDateProcDto hotelRoomDateProcDto = HotelRoomDateProcDto.builder()
                        .hotelRoomInfoEntity(item.getHotelRoomInfoEntity())
                        .fromDate(reservationEntity.getFromDate())
                        .toDate(reservationEntity.getToDate())
                        .build();
                hotelRoomDateProcDtoList.add(hotelRoomDateProcDto);
            });
            reservationEntity.setResStatus(4L);
        }
        hotelResRoomRepository.updateAllHotelResRoomRefundCount(hotelRoomDateProcDtoList);


        return new ResVo(1);
    }


    //유저가 결제한 가격 가져오기
    public long getPaymentAmount(List<ResPaymentEntity> resPaymentEntityList, ReservationEntity reservationEntity) {
        for (ResPaymentEntity resPaymentEntity : resPaymentEntityList) {
            if (resPaymentEntity.getReservationEntity().getResPk().equals(reservationEntity.getResPk())) {
                if (resPaymentEntity.getPaymentStatus() == 0 || resPaymentEntity.getPaymentStatus() == 2) {
                    return 0;
                }
                return resPaymentEntity.getPaymentAmount();
            }
        }
        return 0;
    }


    //유저 회원 탈퇴 진행
    @Transactional
    public ResVo userWithdrawal() {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));

        List<ReservationEntity> reservationEntityList = reservationRepository.findByUserEntityAndResStatus(userEntity, 0);

        if (!reservationEntityList.isEmpty()) {
            throw new CustomException(WithdrawalErrorCode.NON_REFUNDABLE_RESERVATIONS_REMAIN);
        }

        LocalDateTime today = LocalDateTime.now();
        WithdrawalUserEntity withdrawalUserEntity = WithdrawalUserEntity.builder()
                .userEntity(userEntity)
                .approvalDate(today)
                .applyDate(today.plusDays(30))
                .build();
        withdrawalUserRepository.save(withdrawalUserEntity);
        userEntity.setUserStatus(1L);
        userEntity.setUserRole(UserRoleEnum.WITHDRAWAL);
        return new ResVo(1);
    }
    //유저 탈퇴 철회
    @Transactional
    public ResVo userWithdrawalCancel() {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        WithdrawalUserEntity withdrawalUserEntity = withdrawalUserRepository.findById(userEntity.getUserPk()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_EXISTS_WITHDRAWAL_USER));
        withdrawalUserRepository.deleteById(withdrawalUserEntity.getUserEntity().getUserPk());
        userEntity.setUserStatus(0L);
        userEntity.setUserRole(UserRoleEnum.USER);
        return new ResVo(1);
    }

    /*//관리자 유저 등록
    @Transactional
    public ResVo adminSignup(AdminSignupDto dto) {
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setManagerId(dto.getManagerId());
        managerEntity.setManagerPw(passwordEncoder.encode(dto.getManagerPw()));
        managerEntity.setManagerName(dto.getManagerName());
        managerEntity.setRole(UserRoleEnum.ADMIN);
        managerRepository.save(managerEntity);

        return new ResVo(1);
    }

    //관리자 유저 로그인
    @Transactional
    public AdminSigninVo adminSignin(HttpServletResponse response, HttpServletRequest request, @RequestBody @Valid UserSigninDto dto){
        ManagerEntity managerEntity = managerRepository.findByManagerId(dto.getUserEmail()).orElseThrow(() -> new CustomException(UserErrorCode.UNKNOWN_EMAIL_ADDRESS));
        if (!passwordEncoder.matches(dto.getUpw(), managerEntity.getManagerPw())) {
            throw new CustomException(UserErrorCode.MISS_MATCH_PASSWORD);
        }
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .userPk(managerEntity.getManagerPk())
                .build();
        myPrincipal.getRoles().add(managerEntity.getRole().name());
        String at = tokenProvider.generateAccessToken(myPrincipal);
        String rt = tokenProvider.generateRefreshToken(myPrincipal);
        int rtCookieMaxAge = (int) appProperties.getJwt().getRefreshTokenExpiry() / 1000;
        cookie.deleteCookie(response, "rt");
        cookie.setCookie(response, "rt", rt, rtCookieMaxAge);
        return AdminSigninVo.builder()
                .managerId(managerEntity.getManagerId())
                .managerName(managerEntity.getManagerName())
                .accessToken(at)
                .role(managerEntity.getRole().name())
                .build();
    }*/
}
