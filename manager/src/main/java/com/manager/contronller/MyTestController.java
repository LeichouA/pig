package com.manager.contronller;

import com.manager.service.UserMapperService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyTestController {

    @Autowired
    private UserMapperService userMapperService;



    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "loadFallback")
    public String hello(String ds) throws Exception{
        return "hello";
    }

    @GetMapping("/hello1")
    public String hello1(String ds) throws Exception{
        Thread.sleep(6000);
        return "hello";
    }
    protected String loadFallback(){
        return "error";
    }
}
