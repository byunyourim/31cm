package com.shop31cm.service.login.dto;

import com.shop31cm.controller.user.dto.LoginRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;

    public static LoginDto from(LoginRequest request) {
        return LoginDto.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();
    }
}
