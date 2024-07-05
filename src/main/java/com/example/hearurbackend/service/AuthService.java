package com.example.hearurbackend.service;

import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.jwt.JWTUtil;
import com.example.hearurbackend.repository.UserRepository;
import com.example.hearurbackend.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class AuthService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private static final String senderEmail = "sanbyul1@naver.com";

    public AuthService(JWTUtil jwtUtil,
                       UserRepository userRepository,
                       JavaMailSender javaMailSender,
                       RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.redisUtil = redisUtil;
    }

    public String generateJwtToken(String username) {
        // JWT 토큰 생성 및 반환
        String role = "ROLE_USER";
        return jwtUtil.createJwt(username, role, 60 * 60 * 60L);
    }

    public User saveUser(String username, String email, String name, UserRole role) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // 새로운 사용자 생성
            user = new User();
            user.createOAuthUser(username, email, name, role);
        } else {
            // 이미 존재하는 사용자 업데이트
            user.updateOAuthUser(email, name);
        }
        return userRepository.save(user);
    }



    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("안녕하세요. 인증번호입니다.");
        message.setFrom(senderEmail);
        message.setText("인증번호: "+ authCode, "utf-8", "html");

        // Redis 에 해당 인증코드 인증 시간 설정
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }

    public void sendEmail(String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        redisUtil.deleteData(email);
        log.info("code found by email: {}", codeFoundByEmail);
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(code);
    }
}
