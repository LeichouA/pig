package com.pig.controller;

import com.pig.model.Order;
import com.pig.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
public class IndexController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "send")
    public String send() {
        //order对象必须实现序列化
        Order order = new Order();
        order.setId(new Random().nextInt(100));
        order.setName(UUID.randomUUID().toString());
        order.setMessage_id(UUID.randomUUID().toString()+System.currentTimeMillis());
        orderService.createOrder(order);
        return "success";
    }

    @GetMapping("/ttl/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {

        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
    }

}
