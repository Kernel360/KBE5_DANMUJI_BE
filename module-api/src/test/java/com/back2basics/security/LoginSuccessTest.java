package com.back2basics.security;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.back2basics.auth.LoginRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
public class LoginSuccessTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CreateUserUseCase createUserUseCase;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RedisUtil redisUtil;

    String password;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
        UserCreateRequest request = new UserCreateRequest("loginSuccessUser", "testname",
            "test@email.com",
            "01012345678", "PM");

        password = createUserUseCase.create(request.toCommand()).getPassword();
    }

    @Test
    public void 로그인_성공시_토큰_발급() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("loginSuccessUser", password);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginRequest);

        mvc.perform(
                // when
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData))
            //then
            .andExpect(status().isOk())
            .andExpect(header().string("Authorization",
                Matchers.startsWith("Bearer "))) // 헤더 확인
            .andExpect(cookie().exists("refreshToken")); // 쿠키 확인

        // Redis에 refreshToken이 저장되었는지 확인
        boolean hasKey = redisUtil.hasKey("RT:" + loginRequest.username());
        assertTrue(hasKey, "Redis에 키가 존재합니다.");
    }

}
