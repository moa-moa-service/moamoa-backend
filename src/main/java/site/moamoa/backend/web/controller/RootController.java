package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.service.module.redis.RedisModuleService;

@Hidden
@RestController
@RequiredArgsConstructor
public class RootController {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    private final RedisModuleService redisModuleService;

    @GetMapping("/health")
    public String healthCheck() {
//        redisModuleService.checkHealth();
        return "Good!";
    }

    @GetMapping("/health/redis")
    public String healthCheckRedis() {
        return redisHost + redisModuleService.checkHealth();
    }
}
