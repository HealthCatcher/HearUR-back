package com.example.hearurbackend.service;

import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.dto.user.UserDto;
import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.jwt.JWTUtil;
import com.example.hearurbackend.repository.UserRepository;
import com.example.hearurbackend.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String senderEmail = "master@healthcatcher.net";


    public String generateJwtToken(String username) {
        // JWT 토큰 생성 및 반환
        String role = UserRole.ROLE_USER.toString();
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
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("안녕하세요. 인증번호입니다.");
        helper.setFrom(senderEmail); // 동적으로 발신자 이메일 설정
        helper.setText("인증번호: " + authCode, true);

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
        if (codeFoundByEmail == null) {
            return false;
        }
        if (codeFoundByEmail.equals(code)) {
            log.info("code found by email: {}", codeFoundByEmail);
            redisUtil.deleteData(email);
            redisUtil.setDataExpire(email, "verified", 60 * 30L);
            return true;
        }
        return false;
    }

    public Boolean isVerified(String email) {
        if (redisUtil.existData(email)) {
            return redisUtil.getData(email).equals("verified");
        }
        return false;
    }

    @Transactional
    public User registerUser(UserDto userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Given user already exists");
        }
        User user = new User(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getName(),
                userDTO.getUsername(),
                UserRole.ROLE_USER
        );
        return userRepository.save(user);
    }

    public void changePassword(UserDto userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.changePassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}
