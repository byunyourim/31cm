package com.shop31cm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.shop31cm.domain.User;
import com.shop31cm.enums.UserRole;
import com.shop31cm.exception.ErrorCode;
import com.shop31cm.exception.Shop31Exception;
import com.shop31cm.repository.UserRepository;
import com.shop31cm.service.user.UserService;
import com.shop31cm.service.user.dto.UserDto;
import com.shop31cm.utils.PasswordUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class userServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private String email = "test@gmail.com";
    private String nickname = "tester";
    private String password = "1234";
    private String confirmpassword = "1234";

    private User user = User.builder()
        .email(email)
        .nickname(nickname)
        .password("password")
        .role(UserRole.BUYER)
        .build();

    private UserDto userDto = UserDto.builder()
        .email(email)
        .nickname(nickname)
        .password("1234")
        .confirmpassword("1234")
        .build();

    @Test
    @DisplayName("이메일 중복 검사")
    public void testDuplicateByEmail() throws Exception {
        // given
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // when
        Shop31Exception exception = assertThrows(Shop31Exception.class, () -> userService.signup(userDto));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.ALREADY_EXISTS_EMAIL.message);
    }

    @Test
    @DisplayName("닉네임 중복 검사")
    public void testDuplicateByNickname() throws Exception {
        // given
        when(userRepository.existsByNickname(nickname)).thenReturn(true);

        // when
        Shop31Exception exception = assertThrows(Shop31Exception.class, () -> userService.signup(userDto));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.ALREADY_EXISTS_NICKNAME.message);
    }

    @Test
    @DisplayName("패스워드 형식 유효성")
    public void testValidatePasswordFormat() throws Exception {
        // given
        Mockito.mockStatic(PasswordUtils.class);
        when(PasswordUtils.validate("1234")).thenReturn(false);

        // when
        Shop31Exception exception = assertThrows(Shop31Exception.class, () -> userService.signup(userDto));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.INVALID_PASSWORD_FORMAT.message);
    }

    @Test
    @DisplayName("패스워드, 확인 패스워드 일치 테스트")
    public void testMismatchPassword() throws Exception {
        // given


        // when
        Shop31Exception exception = assertThrows(Shop31Exception.class, () -> userService.signup(
            UserDto.builder()
                .email("test@gmail.com")
                .nickname("tester")
                .password(password)
                .confirmpassword("12345")
                .build()
        ));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH.message);
    }

}
