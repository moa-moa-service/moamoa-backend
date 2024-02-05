package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.service.module.redis.RedisModuleService;

@Hidden
@RestController
@RequiredArgsConstructor
public class RootController {

    private final RedisModuleService redisModuleService;

    @GetMapping("/health")
    public String healthCheck() {
        redisModuleService.checkHealth();
        return "Good!";
    }

}
