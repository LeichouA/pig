package com.pig.config;

import com.pig.model.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class deadQueue {

    @RabbitListener(
            bindings = @QueueBinding(                    //数据是否持久化
                    value = @Queue(value = "QEmailDead",durable = "true"),
                    exchange = @Exchange(name = "EmailDeadExchange",
                            durable = "true",type = "direct"),
                    key="EtoD"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception {
        System.out.println("----Email死信队列收到消息，开始消费-----");
        System.out.println("d订单id："+order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        channel.basicAck(deliveryTag,false);
    }

    @RabbitListener(
            bindings = @QueueBinding(                    //数据是否持久化
                    value = @Queue(value = "QSmsDead",durable = "true"),
                    exchange = @Exchange(name = "SmsDeadExchange",
                            durable = "true",type = "direct"),
                    key="StoD"
            )
    )
    @RabbitHandler
    public void onOrderMessageSms(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception {
        System.out.println("----Email死信队列收到消息，开始消费-----");
        System.out.println("d订单id："+order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        channel.basicAck(deliveryTag,false);
    }

}
