package com.zhoulei.controller;

import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FallbackController {

    @GetMapping("/fallbackA")
    public String fallbackA() {
        return "failed";
    }
}
