package com.example.hearurbackend.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component
public class SocialClientRegistration {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.redirect.uri}")
    private String naverRedirectUri;

    @Value("${naver.scope}")
    private String[] naverScopes;

    @Value("${naver.authorization.uri}")
    private String naverAuthorizationUri;

    @Value("${naver.token.uri}")
    private String naverTokenUri;

    @Value("${naver.userinfo.uri}")
    private String naverUserInfoUri;

    @Value("${naver.username.attribute}")
    private String naverUserNameAttribute;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${google.redirect.uri}")
    private String googleRedirectUri;

    @Value("${google.scope}")
    private String[] googleScopes;

    @Value("${google.authorization.uri}")
    private String googleAuthorizationUri;

    @Value("${google.token.uri}")
    private String googleTokenUri;

    @Value("${google.jwkset.uri}")
    private String googleJwkSetUri;

    @Value("${google.issuer.uri}")
    private String googleIssuerUri;

    @Value("${google.userinfo.uri}")
    private String googleUserInfoUri;

    @Value("${google.username.attribute}")
    private String googleUserNameAttribute;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.scope}")
    private String[] kakaoScopes;

    @Value("${kakao.authorization.uri}")
    private String kakaoAuthorizationUri;

    @Value("${kakao.token.uri}")
    private String kakaoTokenUri;

    @Value("${kakao.issuer.uri}")
    private String kakaoIssuerUri;

    @Value("${kakao.userinfo.uri}")
    private String kakaoUserInfoUri;

    @Value("${kakao.username.attribute}")
    private String kakaoUserNameAttribute;

    public ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .redirectUri(naverRedirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(naverScopes)
                .authorizationUri(naverAuthorizationUri)
                .tokenUri(naverTokenUri)
                .userInfoUri(naverUserInfoUri)
                .userNameAttributeName(naverUserNameAttribute)
                .build();
    }

    public ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .redirectUri(googleRedirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(googleScopes)
                .authorizationUri(googleAuthorizationUri)
                .tokenUri(googleTokenUri)
                .jwkSetUri(googleJwkSetUri)
                .issuerUri(googleIssuerUri)
                .userInfoUri(googleUserInfoUri)
                .userNameAttributeName(googleUserNameAttribute)
                .build();
    }

    public ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(kakaoClientId)
                .redirectUri(kakaoRedirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(kakaoScopes)
                .authorizationUri(kakaoAuthorizationUri)
                .tokenUri(kakaoTokenUri)
                .issuerUri(kakaoIssuerUri)
                .userInfoUri(kakaoUserInfoUri)
                .userNameAttributeName(kakaoUserNameAttribute)
                .build();
    }
}