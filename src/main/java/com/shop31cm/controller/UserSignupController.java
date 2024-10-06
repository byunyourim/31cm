package com.shop31cm.controller;

import com.shop31cm.controller.user.dto.SignupRequest;
import com.shop31cm.service.user.UserService;
import com.shop31cm.service.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class UserSignupController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest request) {

        UserDto userDto = UserDto.from(request);
        userService.signup(userDto);
    }



}
