package com.ssafy.enjoytrip.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test", description = "테스트 관련 API")
@RestController
public class TestController {
    @Operation(summary = "JWT 인증 없이도 성공")
    @GetMapping("/api/test")
    public String test(){
        return "test success";
    }

    @Operation(summary = "JWT 인증 되어야 성공")
    @GetMapping("/api/test2")
    public String test2() {
        return "test2 success";
    }

    @GetMapping("/loginuser")
    public String getUserLoginPage() {
        System.out.println("enter step1");
        if (isAuthenticated()) {
            System.out.println("enter step5");
            return "ok user";
        }
        System.out.println("enter step6");
        return "no user";
    }

    private boolean isAuthenticated() {
        System.out.println("enter step2");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            System.out.println("enter step3");
            return false;
        }
        System.out.println("enter step4");
        return authentication.isAuthenticated();
    }
}
