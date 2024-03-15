package com.green.hoteldog.dog;

import com.green.hoteldog.common.entity.UserDogEntity;
import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.repository.DogSizeRepository;
import com.green.hoteldog.common.repository.UserDogRepository;
import com.green.hoteldog.common.repository.UserRepository;
import com.green.hoteldog.common.utils.MyFileUtils;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.common.utils.RandomCodeUtils;
import com.green.hoteldog.dog.models.*;
import com.green.hoteldog.exceptions.AuthorizedErrorCode;
import com.green.hoteldog.exceptions.CommonErrorCode;
import com.green.hoteldog.exceptions.CustomException;
import com.green.hoteldog.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DogService {
    private final DogMapper dogMapper;
    private final MyFileUtils fileUtils;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    private final UserDogRepository userDogRepository;
    private final DogSizeRepository dogSizeRepository;

    //-------------------------------------유저가 등록한 강아지 리스트 호출-------------------------------------------------
    /*public List<GetDogListVo> selUserDogList() { //Mybatis version
        GetUserDogDto dto = new GetUserDogDto();
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        return dogMapper.selUserDog(dto);
    }*/
    @Transactional
    public List<GetDogListVo> selUserDogList() {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        return userEntity.getUserDogEntities().stream()
                .map(item -> GetDogListVo.builder()
                        .userDogPk(item.getUserDogPk())
                        .sizePk(item.getDogSizeEntity().getSizePk())
                        .dogSize(item.getDogSizeEntity().getDogSize())
                        .dogNm(item.getDogNm())
                        .dogAge(item.getDogAge())
                        .dogPic(item.getDogPic())
                        .dogEtc(item.getEtc())
                        .createdAt(item.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }

    //-------------------------------------------------유저 강아지 등록---------------------------------------------------
    /*@Transactional(rollbackFor = Exception.class) // Mybatis version
    public ResVo insUserDog(InsUserDogDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        dto.setDogNum("D" + RandomCodeUtils.getRandomCode(5));
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        try {
            dogMapper.insUserDog(dto);
            if (dto.getDogPic() != null) {
                String target = "/user/" + dto.getUserPk() + "/" + dto.getUserDogPk();
                String saveFileNm = fileUtils.transferTo(dto.getDogPic(), target);
                SetUserDogPicDto picDto = new SetUserDogPicDto();
                picDto.setPic(saveFileNm);
                picDto.setUserDogPk(dto.getUserDogPk());
                picDto.setUserPk(dto.getUserPk());
                dogMapper.setUserDogPic(picDto);
            }
            return new ResVo(1);
        } catch (Exception e) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER);
        }
    }*/

    @Transactional
    public ResVo insUserDog(InsUserDogDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        UserDogEntity userDogEntity = UserDogEntity.builder()
                .dogNm(dto.getDogNm())
                .dogAge(dto.getDogAge())
                .etc(dto.getDogEtc())
                .dogSizeEntity(dogSizeRepository.findById(dto.getSizePk())
                        .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_PARAMETER)))
                .userDogNum("D" + RandomCodeUtils.getRandomCode(5))
                .build();
        userDogRepository.save(userDogEntity);
        if (dto.getDogPic() != null) {
            String target = "/user/" + userEntity.getUserPk() + "/" + userDogEntity.getUserDogPk();
            String saveFileNm = fileUtils.transferTo(dto.getDogPic(), target);
            userDogEntity.setDogPic(saveFileNm);
            userDogRepository.save(userDogEntity);
        }
        return new ResVo(1);
    }

    //---------------------------------------------유저 강아지 정보 수정---------------------------------------------------
    /*public ResVo updUserDog(UpdUserDogDto dto) { //Mybatis version
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int result = dogMapper.updUserDog(dto);
        return new ResVo(result);
    }*/
    @Transactional
    public ResVo updUserDog(UpdUserDogDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        UserDogEntity userDogEntity = userDogRepository.findById(dto.getUserDogPk())
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_PARAMETER));
        if(!userDogEntity.getUserEntity().equals(userEntity)){
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        userDogEntity.setDogNm(dto.getDogNm());
        userDogEntity.setDogAge(dto.getDogAge());
        userDogEntity.setEtc(dto.getDogEtc());
        userDogEntity.setDogSizeEntity(dogSizeRepository.findById(dto.getSizePk())
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_PARAMETER)));
        userDogRepository.save(userDogEntity);
        return new ResVo(1);
    }


    //--------------------------------------------유저 강아지 사진 정보 수정------------------------------------------------
    /*public ResVo updUserDogPic(PatchUserDogPicDto dto) { //Mybatis version
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        String target = "/user/" + facade.getLoginUserPk() + "/" + dto.getUserDogPk();
        fileUtils.delFolderTrigger(target);
        String saveFileNm = fileUtils.transferTo(dto.getPic(), target);
        SetUserDogPicDto picDto = new SetUserDogPicDto();
        picDto.setUserPk((int) facade.getLoginUserPk());
        picDto.setUserDogPk(dto.getUserDogPk());
        picDto.setPic(saveFileNm);
        try {
            dogMapper.setUserDogPic(picDto);
        } catch (Exception e) {
            return new ResVo(0);
        }
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo updUserDogPic(PatchUserDogPicDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        UserDogEntity userDogEntity = userDogRepository.findById(dto.getUserDogPk())
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_PARAMETER));
        if (!userDogEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        String target = "/user/" + userEntity.getUserPk() + "/" + userDogEntity.getUserDogPk();
        fileUtils.delFolderTrigger(target);
        String saveFileNm = fileUtils.transferTo(dto.getPic(), target);
        userDogEntity.setDogPic(saveFileNm);
        userDogRepository.save(userDogEntity);
        return new ResVo(1);
    }

    //---------------------------------------------------유저 강아지 삭제-------------------------------------------------
    /*public ResVo delUserDog(DelUserDogDto dto) { //Mybatis version
        dto.setUserPk((int) facade.getLoginUserPk());
        String target = "/user/" + dto.getUserPk();
        try {
            dogMapper.delUserDog(dto);
        } catch (Exception e) {
            return new ResVo(0);
        }
        fileUtils.delFolderTrigger(target);
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo delUserDog(DelUserDogDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        UserDogEntity userDogEntity = userDogRepository.findById(dto.getUserDogPk())
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_PARAMETER));
        if (!userDogEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        if(userDogEntity.getDogPic() != null){
            String target = "/user/" + userEntity.getUserPk() + "/" + userDogEntity.getUserDogPk();
            fileUtils.delFolderTrigger(target);
        }
        return new ResVo(1);
    }
}
