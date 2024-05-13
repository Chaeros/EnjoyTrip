package com.ssafy.enjoytrip.global.oauth2.handler;

import com.ssafy.enjoytrip.domain.member.Member;
import com.ssafy.enjoytrip.domain.member.Role;
import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.global.jwt.service.JwtService;
import com.ssafy.enjoytrip.global.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberMapper memberMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                response.setHeader("email",oAuth2User.getEmail());
                response.sendRedirect("http://localhost:5173/oauth2/sign-up?email="+oAuth2User.getEmail()); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                Member findMember = memberMapper.findMemberByEmail(oAuth2User.getEmail());
                if ( findMember == null ) throw new IllegalArgumentException("이메일에 해당하는 유저가 없습니다.");
                findMember.authorizeUser();
            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);
        response.sendRedirect("http://localhost:5173?accessToken="+accessToken); // 프론트의 인덱스로 이동
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}