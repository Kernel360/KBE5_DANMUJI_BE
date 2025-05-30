package com.back2basics.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.back2basics.auth.LoginRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.user.port.in.CreateUserUseCase;
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
class LoginFailTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CreateUserUseCase createUserUseCase;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    String password;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @Test
    void 로그인_실패시_예외처리() throws Exception {
        // given
        UserCreateRequest request = new UserCreateRequest("loginFailTest", "testname",
            "test@email.com",
            "01012345678", "PM");

        password = createUserUseCase.create(request.toCommand()).getPassword();

        LoginRequest loginRequest = new LoginRequest("loginFailTest", "wrongpassword");

        String requestData = objectMapper.writeValueAsString(loginRequest);

        mvc.perform(
                // when
                post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData))
            //then
            .andDo(print())
            .andExpect(status().isForbidden());
    }

}
