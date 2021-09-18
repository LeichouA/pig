package com.order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Document: https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class order {

    public static void main(String[] args) {
        SpringApplication.run(order.class, args);
    }
}




