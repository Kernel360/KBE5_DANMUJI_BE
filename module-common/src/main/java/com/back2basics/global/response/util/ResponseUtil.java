package com.back2basics.global.response.util;

import com.back2basics.global.response.result.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void writeJson(HttpServletResponse response,
        ResponseEntity<ApiResponse<T>> apiResponse)
        throws IOException {
        response.setStatus(apiResponse.getStatusCode().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String result = objectMapper.writeValueAsString(apiResponse.getBody());
        response.getWriter().write(result);
    }

}
