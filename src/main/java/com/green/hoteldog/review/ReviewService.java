package com.green.hoteldog.review;

import com.green.hoteldog.common.Const;
import com.green.hoteldog.common.entity.*;
import com.green.hoteldog.common.entity.composite.ReviewFavComposite;
import com.green.hoteldog.common.repository.*;
import com.green.hoteldog.common.utils.MyFileUtils;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.common.utils.RandomCodeUtils;
import com.green.hoteldog.exceptions.*;
import com.green.hoteldog.review.models.*;
import com.green.hoteldog.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final AuthenticationFacade facade;
    private final MyFileUtils fileUtils;
    private final ReviewRepository reviewRepository;
    private final ReviewPicRepository reviewPicRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final ReviewFavRepository reviewFavRepository;
    private final ResComprehensiveInfoRepository resComprehensiveInfoRepository;

    private void checkResUser(int resPk, int userPk) {
        CheckResUserDto checkResUserDto = new CheckResUserDto();
        checkResUserDto.setResPk(resPk);
        checkResUserDto.setUserPk(userPk);
        if (reviewMapper.checkResUser(checkResUserDto) == null || reviewMapper.checkResUser(checkResUserDto) != userPk) {
            throw new CustomException(ReviewErrorCode.MIS_MATCH_USER_PK);
        }
    }

    //-----------------------------------------------------리뷰 등록------------------------------------------------------
    /*@Transactional
    public ResVo insReview(ReviewInsDto dto) { //Mybatis version
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int checkStatus = reviewMapper.checkResStatus(dto);
        if (checkStatus == 0) {
            throw new CustomException(ReviewErrorCode.NOT_CHECK_OUT_STATUS);
        }
        try {
            reviewMapper.insReview(dto);
        } catch (Exception e) {
            throw e;
        }
        if (dto.getPics() != null) {
            List<String> pics = new ArrayList<>();
            String target = "/review/" + dto.getReviewPk();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                pics.add(saveFileNm);
            }
            ReviewInsPicsDto picsDto = new ReviewInsPicsDto();
            picsDto.setReviewPk(dto.getReviewPk());
            picsDto.setPics(pics);
            reviewMapper.insReviewPics(picsDto);
        }
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo insReview(ReviewInsDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReservationEntity reservationEntity = reservationRepository.findById(dto.getResPk())
                .orElseThrow(() -> new CustomException(ReservationErrorCode.RESERVATION_NOT_FOUND));
        if (!reservationEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(ReviewErrorCode.MIS_MATCH_USER_PK);
        }
        if (reservationEntity.getResStatus() != 3) {
            throw new CustomException(ReviewErrorCode.NOT_CHECK_OUT_STATUS);
        }

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .comment(dto.getComment())
                .reservationEntity(reservationEntity)
                .score(dto.getScore())
                .reviewNum("V" + RandomCodeUtils.getRandomCode(5))
                .build();
        reviewRepository.save(reviewEntity);
        if (dto.getPics() != null && !dto.getPics().isEmpty()) {

            String target = "/review/" + reviewEntity.getReviewPk();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                reviewPicRepository.save(ReviewPicEntity.builder()
                        .reviewEntity(reviewEntity)
                        .pic(saveFileNm)
                        .build());

            }

        }

        return new ResVo(1);

    }

    //--------------------------------------------------리뷰 전체 수정-----------------------------------------------------
    //Mybatis version
    /*@Transactional(rollbackFor = Exception.class)
    public ResVo putReview(ReviewUpdDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        checkResUser(dto.getResPk(), dto.getUserPk());
        try {
            reviewMapper.updReview(dto);
        } catch (Exception e) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER);
        }
        if (dto.getPics() != null) {
            reviewMapper.delReviewPics(dto);
            ReviewInsPicsDto picsDto = new ReviewInsPicsDto();
            List<String> pics = new ArrayList<>();
            String target = "/review/" + dto.getReviewPk();
            fileUtils.delFolderTrigger(target);
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                pics.add(saveFileNm);
            }
            picsDto.setReviewPk(dto.getReviewPk());
            picsDto.setPics(pics);
            reviewMapper.insReviewPics(picsDto);
        }
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo putReview(ReviewUpdDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReviewEntity reviewEntity = reviewRepository.findById(dto.getReviewPk()).orElseThrow();
        if (!reviewEntity.getReservationEntity().getUserEntity().equals(userEntity)) {
            throw new CustomException(ReviewErrorCode.MIS_MATCH_USER_PK);
        }
        reviewEntity.setComment(dto.getComment());
        reviewEntity.setScore(dto.getScore());
        reviewRepository.save(reviewEntity);
        String target = "/review/" + dto.getReviewPk();
        if (dto.getDelPicsPk() != null && !dto.getDelPicsPk().isEmpty()) {
            List<ReviewPicEntity> reviewPicEntityList = reviewPicRepository.findAllById(dto.getDelPicsPk());
            List<String> pics = reviewPicEntityList.stream().map(ReviewPicEntity::getPic).toList();
            for (String pic : pics) {
                String delFile = "/" + pic;
                fileUtils.delFile(target, delFile);
            }
            reviewPicRepository.deleteAll(reviewPicEntityList);
        }
        if (dto.getPics() != null && !dto.getPics().isEmpty()) {
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                reviewPicRepository.save(ReviewPicEntity.builder()
                        .reviewEntity(reviewEntity)
                        .pic(saveFileNm)
                        .build());
            }
        }
        return new ResVo(Const.SUCCESS);
    }


    //--------------------------------------------------리뷰 코멘트 수정---------------------------------------------------
    //Mybatis version
    /*public ResVo patchReviewComment(ReviewPatchDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        checkResUser(dto.getResPk(), dto.getUserPk());
        try {
            reviewMapper.updReviewComment(dto);
            return new ResVo(1);
        } catch (Exception e) {
            return new ResVo(0);
        }
    }*/
    public ResVo patchReviewComment(ReviewPatchDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReviewEntity reviewEntity = reviewRepository.findById(dto.getReviewPk()).orElseThrow();
        if (!reviewEntity.getReservationEntity().getUserEntity().equals(userEntity)) {
            throw new CustomException(ReviewErrorCode.MIS_MATCH_USER_PK);
        }
        reviewEntity.setComment(dto.getComment());
        reviewRepository.save(reviewEntity);
        return new ResVo(Const.SUCCESS);
    }

    //--------------------------------------------------리뷰 좋아요 토글---------------------------------------------------
    //Mybatis version
    /*public ResVo patchReviewFav(ReviewFavDto dto) {
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        if (reviewMapper.delReviewFav(dto) == 0) {
            int result = reviewMapper.insReviewFav(dto);
            return new ResVo(result);
        }
        return new ResVo(2);
    }*/
    public ResVo patchReviewFav(ReviewFavDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReviewEntity reviewEntity = reviewRepository.findById(dto.getReviewPk()).orElseThrow();
        ReviewFavComposite reviewFavComposite = new ReviewFavComposite(userEntity.getUserPk(), reviewEntity.getReviewPk());
        if (reviewFavRepository.existsById(reviewFavComposite)) {
            reviewFavRepository.deleteById(reviewFavComposite);
            return new ResVo(2);
        }
        else {
            reviewFavRepository.save(ReviewFavEntity.builder()
                    .composite(reviewFavComposite)
                    .reviewEntity(reviewEntity)
                    .userEntity(userEntity)
                    .build());
            return new ResVo(1);
        }
    }


    //--------------------------------------------------리뷰 삭제---------------------------------------------------
    // Mybatis version
    /*@Transactional
    public ResVo delReview(DelReviewDto dto) {
        dto.setUserPk((int) facade.getLoginUserPk());
        log.info("DelReviewDto : {}", dto);
        fileUtils.delAllFolderTrigger("/review/" + dto.getReviewPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        checkResUser(dto.getResPk(), dto.getUserPk());
        try {
            reviewMapper.delReviewFavAll(dto);
            reviewMapper.delReviewPicsAll(dto);
            reviewMapper.delReview(dto);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            throw new CustomException(CommonErrorCode.CONFLICT);
        }
    }*/
    public ResVo delReview(DelReviewDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        ReviewEntity reviewEntity = reviewRepository.findById(dto.getReviewPk()).orElseThrow();
        if (!reviewEntity.getReservationEntity().getUserEntity().equals(userEntity)) {
            throw new CustomException(ReviewErrorCode.MIS_MATCH_USER_PK);
        }
        reviewRepository.delete(reviewEntity);
        return new ResVo(Const.SUCCESS);
    }

    //------------------------------------------------유저가 등록한 리뷰 목록--------------------------------------------
    //Mybatis version
    /*public List<UserReviewVo> userReviewList() {
        int userPk = (int) facade.getLoginUserPk();
        if (userPk == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        List<UserReviewVo> userReviewVoList = reviewMapper.selUserResPk(userPk);
        log.info("userReviewVoList : {}", userReviewVoList);
        if (userReviewVoList != null && userReviewVoList.size() > 1) {
            *//*List<Integer> resPkList = new ArrayList<>();*//*
            List<Integer> reviewPkList = new ArrayList<>();
            HashMap<Integer, UserReviewVo> resRoomInfoMap = new HashMap<>();
            HashMap<Integer, UserReviewVo> reviewPicMap = new HashMap<>();
            for (UserReviewVo vo : userReviewVoList) {
                *//*resPkList.add(vo.getResPk());*//*
                resRoomInfoMap.put(vo.getReviewPk(), vo);
                reviewPkList.add(vo.getReviewPk());
                reviewPicMap.put(vo.getReviewPk(), vo);
            }
            List<UserResRoomVo> userResRoomVoList = reviewMapper.selUserResRoomInfo(reviewPkList);
            log.info("userResRoomVoList : {}", userResRoomVoList);
            for (UserResRoomVo vo : userResRoomVoList) {
                log.info("UserResRoomVo : {}", vo);
                resRoomInfoMap.get(vo.getReviewPk()).getRoomNm().add(vo.getHotelRoomNm());
            }
            List<UserReviewPic> userReviewPicList = reviewMapper.selUserReviewPics(reviewPkList);
            log.info("userReviewPicList : {}", userReviewPicList);
            if (userReviewPicList != null && userReviewPicList.size() > 0) {
                for (UserReviewPic pic : userReviewPicList) {
                    log.info("UserReviewPic : {}", pic);
                    reviewPicMap.get(pic.getReviewPk()).getReviewPics().add(pic.getReviewPic());
                }
            }
        }


        return userReviewVoList;
    }*/
    @Transactional
    public List<UserReviewVo> userReviewList() {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        Set<ReservationEntity> reservationEntitySet = userEntity.getReservationEntities();
        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByReservationEntityInOrderByCreatedAtDesc(reservationEntitySet);
        return reviewEntityList.stream()
                .map(item -> {
                    List<ResComprehensiveInfoEntity> resComprehensiveInfoEntities = resComprehensiveInfoRepository.findAllByReservationEntity(item.getReservationEntity());
                    return UserReviewVo.builder()
                            .reviewPk(item.getReviewPk())
                            .resPk(item.getReservationEntity().getResPk())
                            .hotelNm(item.getReservationEntity().getHotelEntity().getHotelNm())
                            .comment(item.getComment())
                            .score(item.getScore())
                            .createdAt(item.getCreatedAt().toString())
                            .roomNm(resComprehensiveInfoEntities.stream().map(roomNm -> roomNm.getHotelRoomInfoEntity().getHotelRoomNm()).toList())
                            .reviewPics(reviewPicRepository.findAllByReviewEntity(item).stream()
                                    .map(pic -> ReviewPicInfo.builder()
                                            .reviewPic(pic.getPic())
                                            .reviewPk(pic.getReviewPicPk())
                                            .build()).toList())
                            .build();
                }).collect(Collectors.toList());
    }


        //------------------------------------------------호텔 리뷰-----------------------------------------------------------
    public List<HotelReviewSelVo> getHotelReview(HotelReviewSelDto dto) {
        // n+1 이슈 해결
        try {
            List<HotelReviewSelVo> list = reviewMapper.selHotelReview(dto);
            for (HotelReviewSelVo vo : list) {
                dto.getReviewPk().add(vo.getReviewPk());
            }
            List<HotelReviewPicsSelVo> pics = reviewMapper.selHotelReviewPics(dto);

            // pk를 담을 list, pk 및 해당 객체 주솟 값을 담을 map 생성
            List<Integer> revPk = new ArrayList<>();
            Map<Integer, HotelReviewSelVo> hashMap = new HashMap<>();
            for (HotelReviewSelVo vo : list) {
                revPk.add(vo.getReviewPk());
                hashMap.put(vo.getReviewPk(), vo);
            }
            for (HotelReviewPicsSelVo vo : pics) {
                hashMap.get(vo.getReviewPk()).getPics().add(vo.getPic());
            }
            // 사진 3개까지 제거
            for (HotelReviewSelVo vo : list) {
                while (vo.getPics().size() > 3) {
                    vo.getPics().remove(vo.getPics().size() - 1);
                }
            }
            return list;
        } catch (Exception e) {
            throw new CustomException(ReviewErrorCode.PAGE_COUNT_EXEEDED_ERROR);
        }
    }
}
