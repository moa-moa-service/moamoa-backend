package site.moamoa.backend.global.config;

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
import site.moamoa.backend.global.jwt.filter.JwtAuthenticationProcessingFilter;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.global.oauth2.handler.OAuth2LoginFailureHandler;
import site.moamoa.backend.global.oauth2.handler.OAuth2LoginSuccessHandler;
import site.moamoa.backend.global.oauth2.service.CustomOAuth2UserService;
import site.moamoa.backend.repository.MemberRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                        authorize.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**",
                                        "/health").permitAll()
                                .anyRequest().authenticated()
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

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
    }

}
