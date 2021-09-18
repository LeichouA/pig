package com.pig.service;

import com.pig.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface OrderService {

    public void createOrder(Order order);
}
