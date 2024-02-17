package site.moamoa.backend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Value("${server.url.local}")
    private String localUrl;

    @Value("${server.url.dev-http}")
    private String devHttpUrl;

    @Value("${server.url.dev-https}")
    private String devHttpsUrl;

    @Bean
    public GroupedOpenApi defaultAPI() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("모아모아 서비스 API 명세서")
                .version("0.1")
                .description("모아모아 서비스의 API Docs 입니다.");

        Server localServer = new Server();
        localServer.setDescription("Local Server");
        localServer.setUrl(localUrl);

        Server devHttpsServer = new Server();
        devHttpsServer.setDescription("Develop Https Server");
        devHttpsServer.setUrl(devHttpsUrl);

        Server devHttpServer = new Server();
        devHttpServer.setDescription("Develop Http Server");
        devHttpServer.setUrl(devHttpUrl);

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement))
                .servers(Arrays.asList(localServer, devHttpsServer, devHttpServer))
                .info(info);
    }
}

