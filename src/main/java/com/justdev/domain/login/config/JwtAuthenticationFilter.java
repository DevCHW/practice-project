package com.justdev.domain.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justdev.domain.login.LoginRequest;
import com.justdev.domain.login.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.config.Elements.JWT;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = null;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            log.error("ERROR={}", e);
            throw new RuntimeException(e);
        }

        //유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);


        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("Authentication : {}", principalDetails.getUser().getUsername());
        return authentication;
    }

    // JWT Token 생성해서 response에 담아주기

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
