package com.back2basics.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.back2basics.domain.auth.dto.LoginRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class LogoutTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CreateUserUseCase createUserUseCase;

    @Autowired
    UserRepositoryPort userRepositoryPort;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private static boolean initialized = false;
    String accessToken;
    String password;

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

        if (!initialized) {
            if (userRepositoryPort.findByUsername("logoutTest").isEmpty()) {
                UserCreateRequest request = new UserCreateRequest(
                    "logoutTest", "testname", "test@email.com",
                    "01012345678", "PM"
                );
                password = createUserUseCase.create(request.toCommand()).password();
            }
            initialized = true;
        }

    }

    @Test
    void 로그아웃_성공() throws Exception {

        // 로그인
        LoginRequest loginRequest = new LoginRequest("logoutTest", password);
        String requestData = objectMapper.writeValueAsString(loginRequest);

        accessToken = mvc.perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData))
            .andReturn()
            .getResponse()
            .getHeader("Authorization");

        // given & when
        mvc.perform(
                post("/logout")
                    .header("Authorization", accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            //then
            .andExpect(status().isOk());

    }

    @Test
    void 로그아웃_실패() throws Exception {
        // given & when
        String invalidAccessToken = "invalid"; // 잘못된 토큰 설정
        mvc.perform(
                post("/logout")
                    .header("Authorization", invalidAccessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            // then
            .andExpect(status().isUnauthorized());
    }
}

