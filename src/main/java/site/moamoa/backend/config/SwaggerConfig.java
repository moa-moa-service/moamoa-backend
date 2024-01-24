package site.moamoa.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.moamoa.backend.api_payload.code.status.SuccessStatus;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi defaultAPI() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(springSecurityLoginEndpointCustomiser())
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("모아모아 서비스 API 명세서")
                .version("0.1")
                .description("모아모아 서비스의 API Docs 입니다.");

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
                .info(info);
    }

    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomiser() {
        return openAPI -> {
            openAPI.path("/api/auth/authorize", new PathItem().get(
                    new Operation()
                            .security(Collections.emptyList())
                            .responses(new ApiResponses().addApiResponse(SuccessStatus._OK.getCode(),
                                    new ApiResponse().description(SuccessStatus._OK.getMessage())))
                            .addTagsItem("인증 API")
                            .summary("인가 코드 발급 (Swagger로 테스트 불가능)")
                            .description("인가 코드를 받아옵니다. (RequestURL을 통해 직접 테스트 필요)")
            ));

            openAPI.path("/api/auth/token", new PathItem().get(
                    new Operation()
                            .parameters(Arrays.asList(
                                    new Parameter()
                                            .name("code")
                                            .in("query")
                                            .required(true)
                                            .schema(new Schema<>().type("string"))
                                            .description("인가 code")
                                            .example("V8l1td3fVCAbuJm40u"),
                                    new Parameter()
                                            .name("state")
                                            .in("query")
                                            .required(true)
                                            .schema(new Schema<>().type("string"))
                                            .description("인가 state")
                                            .example("C5DWJW1yJmCx7GdQsfrgQjkTMM_vc_5j7JLAbPpvNqE=")
                            ))
                            .security(Collections.emptyList())
                            .responses(new ApiResponses().addApiResponse(SuccessStatus._OK.getCode(),
                                    new ApiResponse().description(SuccessStatus._OK.getMessage())))
                            .addTagsItem("인증 API")
                            .summary("인증 토큰 발급 (Swagger로 테스트 불가능)")
                            .description("인가 코드와 상태를 받아 인증 토큰을 받아옵니다. (RequestURL을 통해 직접 테스트 필요)")
            ));
        };
    }
}

