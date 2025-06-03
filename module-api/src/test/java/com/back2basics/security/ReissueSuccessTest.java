package com.back2basics.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.back2basics.domain.auth.dto.LoginRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReissueSuccessTest {

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

    String password;
    MvcResult result;

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

        UserCreateRequest request = new UserCreateRequest(
            "reissueSuccessTest", "testname", "test@email.com",
            "01012345678", "PM"
        );
        password = createUserUseCase.create(request.toCommand()).getPassword();

        LoginRequest loginRequest = new LoginRequest("reissueSuccessTest", password);
        String requestData = objectMapper.writeValueAsString(loginRequest);
        result = mvc.perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData))
            .andReturn();

    }

    @Test
    void 재발급_성공() throws Exception {
        // 로그인
        String accessToken = result.getResponse().getHeader("Authorization");

        String refreshToken = Arrays.stream(result.getResponse().getCookies())
            .filter(c -> "refreshToken".equals(c.getName()))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);

        // given & when
        MvcResult resultRefresh = mvc.perform(
                post("/api/auth/reissue")
                    .header("Authorization", accessToken)
                    .cookie(new Cookie("refreshToken", refreshToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            .andReturn();

        // then
        String newRefreshToken = Arrays.stream(resultRefresh.getResponse().getCookies())
            .filter(c -> "refreshToken".equals(c.getName()))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);

        log.info("Old refresh: {}", refreshToken);
        log.info("New refresh: {}", newRefreshToken);

//        assertNotEquals(newRefreshToken, refreshToken);
    }

}
