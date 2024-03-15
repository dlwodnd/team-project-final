package com.green.hoteldog.email;

import com.green.hoteldog.common.entity.jpa_enum.UserRoleEnum;
import com.green.hoteldog.common.repository.BusinessRepository;
import com.green.hoteldog.common.utils.RedisUtil;
import com.green.hoteldog.user.UserMapper;
import com.green.hoteldog.common.repository.UserRepository;
import com.green.hoteldog.user.models.UserInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSendService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    private String authNumber;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    public boolean checkUserDuplicationEmail(String email){
        return userRepository.findByUserEmail(email).isPresent();
    }
    public boolean checkBusinessUserDuplicationEmail(String email){
        return businessRepository.findByBusinessEmail(email).isPresent();
    }

    public boolean checkAuthNum(String email,String authNum){


        if(redisUtil.getData(email)==null){
            return false;
        }
        else if(redisUtil.getData(email).equals(authNum)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean businessCheckAuthNum(String email,String authNum){


        if(redisUtil.getData(email)==null){
            return false;
        }
        else if(redisUtil.getData(email).equals(authNum)){
            return true;
        }
        else{
            return false;
        }
    }


    /*public void makeRandomNumber() throws NoSuchAlgorithmException {
        String randomNum;
        do {
            int num = SecureRandom.getInstanceStrong().nextInt(99999);
             randomNum = String.valueOf(num);
        } while (randomNum.length() != 6);
        this.authNumber = randomNum;
    }*/
    public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = randomNumber;
    }

    public String joinEmail(String email , UserRoleEnum role) {
        makeRandomNumber();
        String setFrom = "greendoghotel2024@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email; // 받는 사람 이메일 주소
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "저희 프로젝트 테스트에 참여해 주셔서 감사합니다." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content,role);
        return authNumber;
    }
    public void mailSend(String setFrom,String toMail,String title,String content,UserRoleEnum role){
        String redisKey = "";

        if (role.equals(UserRoleEnum.USER)){
            redisKey = "CU_" + toMail;
        }
        else if(role.equals(UserRoleEnum.BUSINESS_USER)){
            redisKey = "BU_" + toMail;
        }
        MimeMessage message = mailSender.createMimeMessage();
        redisUtil.setDataExpire(redisKey,authNumber,60*5L);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
