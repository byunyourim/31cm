package com.shop31cm.service.user.dto;

import com.shop31cm.controller.user.dto.SignupRequest;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private String email;
    private String nickname;
    private String phone;
    private String password;
    private String confirmpassword;
    private String vertificationKey;

    public static UserDto from(SignupRequest request) {
        return UserDto.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .password(request.getPassword())
            .confirmpassword(request.getConfirmPassowrd())
            .phone(request.getPhone())
            .vertificationKey(request.getVerificationKey())
            .build();
    }
}

