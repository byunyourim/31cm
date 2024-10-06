package com.shop31cm.service.user;

import com.shop31cm.domain.User;
import com.shop31cm.enums.UserRole;
import com.shop31cm.service.user.dto.UserDto;
import com.shop31cm.exception.ErrorCode;
import com.shop31cm.exception.Shop31Exception;
import com.shop31cm.repository.UserRepository;
import com.shop31cm.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(UserDto userDto) {
        String email = userDto.getEmail();
        String nickname = userDto.getNickname();
        String password = userDto.getPassword();
        String confirmPassword = userDto.getConfirmpassword();
        String verificationKey = userDto.getVertificationKey();

        checkDuplicateEmail(email);

        checkDuplicateNickname(nickname);

        validatePassword(password, confirmPassword);

        String phone = getPhoneFromKey(verificationKey);

        User user = createUser(email, nickname, password, phone);

        userRepository.save(user);
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new Shop31Exception(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
    }

    private void checkDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new Shop31Exception(ErrorCode.ALREADY_EXISTS_NICKNAME);
        }
    }

    private void validatePassword(String password, String confirmPassword) {
        if (!PasswordUtils.validate(password)) {
            throw new Shop31Exception(ErrorCode.INVALID_PASSWORD_FORMAT);
        }

        if (!password.equals(confirmPassword)) {
            throw new Shop31Exception(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
        }
    }

    private String getPhoneFromKey(String verificationKey) {
        // 추후 작업
        return "010-1111-1111";
    }

    private User createUser(String email, String nickname, String password, String phone) {
        return User.builder()
            .email(email)
            .nickname(nickname)
            .password(password)
            .role(UserRole.BUYER)
            .phone(phone)
            .build();

    }
}
