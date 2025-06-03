package com.back2basics.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.back2basics.domain.auth.dto.LoginRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.out.UserRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
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

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReissueFailTest {

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
    String password;
    MvcResult result;

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

        if (!initialized) {
            if (userRepositoryPort.findByUsername("reissueTest").isEmpty()) {
                UserCreateRequest request = new UserCreateRequest(
                    "reissueTest", "testname", "test@email.com",
                    "01012345678", "PM"
                );
                password = createUserUseCase.create(request.toCommand()).password();
            }
            initialized = true;
        }

        LoginRequest loginRequest = new LoginRequest("reissueTest", password);
        String requestData = objectMapper.writeValueAsString(loginRequest);
        result = mvc.perform(
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData))
            .andReturn();

    }

    @Test
    void 블랙리스트_리프레시토큰으로_재발급_실패() throws Exception {

        String accessToken = result.getResponse().getHeader("Authorization");

        String refreshToken = Arrays.stream(result.getResponse().getCookies())
            .filter(c -> "refreshToken".equals(c.getName()))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);

        // given
        mvc.perform(
            post("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content("{}"));

        // when
        mvc.perform(
                post("/api/auth/reissue")
                    .header("Authorization", accessToken)
                    .cookie(new Cookie("refreshToken", refreshToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            // then
            .andExpect(status().isUnauthorized());
    }

}
