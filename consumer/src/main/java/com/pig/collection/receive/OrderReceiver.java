package com.pig.collection.receive;
import com.pig.model.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "QEmail",durable = "true"),
                    exchange = @Exchange(name = "verifyExchange",
                            durable = "true",type = "direct"),
                    key="EtoD"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception {
        System.out.println("----Email收到消息，开始消费-----");
        System.out.println("d订单id："+order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        channel.basicAck(deliveryTag,false);
        System.out.println("--------消费完成--------");
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "QSms",durable = "true"),
                    exchange = @Exchange(name = "verifyExchange",
                            durable = "true",type = "direct"),
                    key="EtoS"
            )
    )
    @RabbitHandler
    public void onOrderMessage1(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception {
        System.out.println("----SMS收到消息，开始消费-----");
        System.out.println("d订单id："+order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        channel.basicAck(deliveryTag,false);
        System.out.println("--------消费完成--------");
    }

}
