package com.pig.service;

import com.pig.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{


    @Autowired
    private OrderSender orderSender;

    @Override
    public void createOrder(Order order) {
        try {
            orderSender.send(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
