package com.example.hearurbackend.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component
public class SocialClientRegistration {

    public ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("fGMii6y0U1sRNvOt5AMw")
                .clientSecret("KEXtf34wxd")
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("97180559241-8u2jhuuua4ci0vb434kdu8m371qovf34.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }

    public ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId("ca10ce0ac9ca9af4f407dc5203ecbca7")
                //.clientSecret("O6O8V0dj9UUNpMfkGxPlj00iZeIU9JLy")
                .redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .issuerUri("https://kauth.kakao.com")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .build();
    }
}