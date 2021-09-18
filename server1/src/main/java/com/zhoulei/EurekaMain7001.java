package com.zhoulei;



import com.test.HelloworldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @create
 */
@SpringBootApplication
@EnableEurekaServer
@RestController
public class EurekaMain7001
{
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class, args);
    }

    @Autowired
    private HelloworldService helloworldService;

    @RequestMapping("/hello")
    public String sayHello() {
        return helloworldService.sayHello();
    }
}
