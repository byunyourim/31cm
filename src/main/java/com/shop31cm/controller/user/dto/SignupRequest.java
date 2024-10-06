package com.shop31cm.controller.user.dto;

import com.shop31cm.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효하지 않은 이메일 주소입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인값은 필수 입력 항목입니다.")
    private String confirmPassword;

    private String phone;

    private String verificationKey;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
            .nickname(nickname)
            .email(email)
            .phone(passwordEncoder.encode(password))
            .build();
    }

}
