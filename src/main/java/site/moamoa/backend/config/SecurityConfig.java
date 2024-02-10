package site.moamoa.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.moamoa.backend.global.jwt.filter.JwtAuthenticationProcessingFilter;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.global.oauth2.handler.OAuth2LoginFailureHandler;
import site.moamoa.backend.global.oauth2.handler.OAuth2LoginSuccessHandler;
import site.moamoa.backend.global.oauth2.service.CustomOAuth2UserService;
import site.moamoa.backend.repository.member.MemberRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer{

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**",
                                        "/health","/health/**").permitAll()
                                .requestMatchers("/api/auth/member-info").hasRole("GUEST")
                                .anyRequest().hasRole("MEMBER")
                )
                .oauth2Login(oauth2Configure ->
                        oauth2Configure
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .authorizationEndpoint(
                                        authorizationEndpointConfig ->
                                                authorizationEndpointConfig.baseUri("/api/auth/authorize"))
                                .redirectionEndpoint(
                                        redirectionEndpointConfig ->
                                                redirectionEndpointConfig.baseUri("/api/auth/token"))
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)));

        http.addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
    }

}
