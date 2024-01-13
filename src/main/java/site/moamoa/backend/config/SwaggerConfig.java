package site.moamoa.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("모아모아 서비스 API 명세서")
                .version("0.1")
                .description("모아모아 서비스의 API Docs 입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
