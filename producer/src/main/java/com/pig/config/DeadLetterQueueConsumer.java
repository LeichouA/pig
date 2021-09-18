package com.pig.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class DeadLetterQueueConsumer {
    private static Logger logger = Logger.getLogger(DeadLetterQueueConsumer.class.getName());
    @RabbitListener(queues = "QD")
    public void receiveD() throws IOException
    {
        logger.info("当前时间：收到死信队列信息");
    }
}
