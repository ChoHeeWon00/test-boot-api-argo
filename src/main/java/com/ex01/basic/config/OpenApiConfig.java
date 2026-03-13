package com.ex01.basic.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type( SecurityScheme.Type.HTTP )
                .scheme("bearer");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("로컬 개발용");

        Server prodServer = new Server()
                .url("http://web-alb-723542513.ap-northeast-2.elb.amazonaws.com")
                .description("AWS 로드밸런서 서버");


        return new OpenAPI()
                .info(new Info()
                        .title("QUIZ OpenAPI")
                        .description("스웨거 실습 입니다")
                        .version("v1.0.0")
                )
                .servers(List.of( prodServer, localServer ))
                .components(
                        new Components().addSecuritySchemes("JWT",securityScheme)
                );
    }
}
