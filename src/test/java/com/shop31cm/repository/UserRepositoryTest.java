package com.shop31cm.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.shop31cm.domain.User;
import com.shop31cm.enums.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void testUserSignUp() throws Exception {
        // given
        User newUser = User.builder()
            .email("test@gmail.com")
            .password("password")
            .nickname("tester")
            .phone("010-1111-1111")
            .role(UserRole.BUYER)
            .build();

        // when
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(newUser));

        userRepository.save(newUser);
        User result = userRepository.findByEmail("test@gmail.com").orElse(null);


        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(result.getNickname()).isEqualTo(newUser.getNickname());
        assertThat(result.getRole()).isEqualTo(newUser.getRole());
        assertThat(result.getPhone()).isEqualTo(newUser.getPhone());
    }
}
