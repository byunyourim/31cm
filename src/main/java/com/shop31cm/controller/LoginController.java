package com.shop31cm.controller;

import com.shop31cm.controller.user.dto.LoginRequest;
import com.shop31cm.service.LoginService;
import com.shop31cm.service.login.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest request) {
        LoginDto loginDto = LoginDto.from(request);
        loginService.login(loginDto);
    }
}
