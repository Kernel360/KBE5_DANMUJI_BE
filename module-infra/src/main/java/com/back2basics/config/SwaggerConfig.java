package com.back2basics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Danmuji API")
                .version("v1")
                .description("Danmuji API Docs")
                .contact(new Contact()
                    .name("back2bascis")
                    .url("https://github.com/Kernel360/KBE5_DANMUJI_BE")
                    .email("you@example.com"))
            );
    }
}
