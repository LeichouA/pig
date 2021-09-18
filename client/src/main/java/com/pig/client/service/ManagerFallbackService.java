package com.pig.client.service;

import org.springframework.stereotype.Component;

@Component
public class ManagerFallbackService implements HelloService{
    @Override
    public String hello() {
        return "hello fallback";
    }

    @Override

    public String hello1() {
        return "hello1 fallback";
    }
}
