package com.example.hearurbackend.service;


import com.example.hearurbackend.domain.UserRole;
import com.example.hearurbackend.dto.*;
import com.example.hearurbackend.entity.User;
import com.example.hearurbackend.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        switch (registrationId) {
            case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            default -> {
                return null;
            }
        }
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        if (existData == null) {
            User userEntity = new User();
            userEntity.createOAuthUser(
                    username,
                    oAuth2Response.getEmail(),
                    oAuth2Response.getName(),
                    UserRole.ROLE_USER
            );
            userRepository.save(userEntity);

            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(UserRole.ROLE_USER);
        } else {
            existData.updateOAuthUser(
                    oAuth2Response.getEmail(),
                    oAuth2Response.getName()
            );
            userRepository.save(existData);

            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());
        }
        return new CustomOAuth2User(userDTO);
    }
}
