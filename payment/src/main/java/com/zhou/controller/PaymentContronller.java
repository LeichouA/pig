package com.zhou.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PaymentContronller {


    @GetMapping("/payment")
    public String hello(){
        return "payment";
    }
}
