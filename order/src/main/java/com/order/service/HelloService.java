package com.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "nacos-payment")
public interface HelloService {

    @GetMapping(value = "/payment")
    public String hello();
}
