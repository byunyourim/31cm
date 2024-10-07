package com.shop31cm.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop31cm.controller.user.dto.SignupRequest;
import com.shop31cm.service.user.UserService;
import com.shop31cm.service.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private final String email = "test@gmail.com";
    private final String nickname = "tester";
    private final String password = "1234";
    private final String confirmPassword = "1234";
    private final String phone = "010-1111-1111";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSignup() throws Exception {
        // given
        String url = "/api/user/signup";

        SignupRequest signupRequest = SignupRequest.builder()
            .email(email)
            .nickname(nickname)
            .password(password)
            .confirmPassword(confirmPassword)
            .build();


        UserDto userDto = UserDto.from(signupRequest);

        doNothing().when(userService).signup(userDto);

        String json = new ObjectMapper().writeValueAsString(signupRequest);

        // when & then
        mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isCreated());
    }
}
