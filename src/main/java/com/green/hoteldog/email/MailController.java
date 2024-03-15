package com.green.hoteldog.email;

import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import com.green.hoteldog.common.utils.RedisUtil;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.email.models.EmailCheckDto;
import com.green.hoteldog.email.models.EmailRequestDto;
import com.green.hoteldog.email.models.EmailResponseVo;
import com.green.hoteldog.exceptions.CustomException;
import com.green.hoteldog.exceptions.EmailErrorCode;
import com.green.hoteldog.exceptions.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class MailController {
    private final MailSendService mailSendService;
    private final RedisUtil redisUtil;



    @PostMapping("/mailSend")
    @Operation(summary = "이메일 인증 보내기",description = "이메일 인증 보내기")
    public ResVo sendEmail(@RequestBody @Valid EmailRequestDto dto){
        if(mailSendService.checkUserDuplicationEmail(dto.getEmail())){
            throw new CustomException(UserErrorCode.ALREADY_USED_EMAIL);
        }
        String redisSaveKey = "CU_" + dto.getEmail();
        redisUtil.deleteData(redisSaveKey);
        mailSendService.joinEmail(dto.getEmail(), UserRoleEnum.USER);
        return new ResVo(1);

    }
    @PostMapping("/business/mailSend")
    @Operation(summary = "사업자 유저 이메일 인증 보내기",description = "사업자 유저 이메일 인증 보내기")
    public ResVo businessSendMail(@RequestBody @Valid EmailRequestDto dto){
        if(mailSendService.checkBusinessUserDuplicationEmail(dto.getEmail())){
            throw new CustomException(UserErrorCode.ALREADY_USED_EMAIL);
        }
        String redisSaveKey = "BU_" + dto.getEmail();
        redisUtil.deleteData(redisSaveKey);
        mailSendService.joinEmail(dto.getEmail(), UserRoleEnum.BUSINESS_USER);
        return new ResVo(1);

    }
    @PostMapping("/mailAuthCheck")
    @Operation(summary = "이메일 인증번호 체크",description = "이메일 인증번호 체크")
    public EmailResponseVo checkMail(@RequestBody @Valid EmailCheckDto dto){
        String redisSaveKey = "CU_" + dto.getEmail();
        EmailResponseVo vo = new EmailResponseVo();
        if(!mailSendService.checkAuthNum(redisSaveKey, dto.getAuthNum())){
            throw new CustomException(EmailErrorCode.MISS_MATCH_CODE);
        }
        vo.setEmail(dto.getEmail());
        vo.setResult(1);
        return vo;
    }

    @PostMapping("/business/mailAuthCheck")
    @Operation(summary = "사업자 이메일 인증번호 체크",description = "사업자 이메일 인증번호 체크")
    public EmailResponseVo businesscheckMail(@RequestBody @Valid EmailCheckDto dto){
        String redisSaveKey = "BU_" + dto.getEmail();
        EmailResponseVo vo = new EmailResponseVo();
        if(!mailSendService.checkAuthNum(redisSaveKey, dto.getAuthNum())){
            throw new CustomException(EmailErrorCode.MISS_MATCH_CODE);
        }
        vo.setEmail(dto.getEmail());
        vo.setResult(1);
        return vo;
    }
}
