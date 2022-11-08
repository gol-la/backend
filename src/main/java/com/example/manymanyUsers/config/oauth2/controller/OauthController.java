package com.example.manymanyUsers.config.oauth2.controller;

import com.example.manymanyUsers.config.oauth2.service.OauthService;
import com.example.manymanyUsers.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OauthController {

    private final OauthService oauthService;


    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {

        LoginResponse loginResponse = oauthService.login(provider, code);

        return ResponseEntity.ok().body(loginResponse);

    }
}
