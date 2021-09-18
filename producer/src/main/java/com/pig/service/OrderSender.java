package com.pig.service;

import com.pig.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class OrderSender implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
        /**
        * true：
        * 交换机无法将消息进行路由时，会将该消息返回给生产者
        * false：
        * 如果发现消息无法进行路由，则直接丢弃
        */
        rabbitTemplate.setMandatory(true);
        //设置回退消息交给谁处理
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 信息投递的方法
     * @param order
     * @throws Exception
     */
    public void send(Order order) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessage_id());
        rabbitTemplate.convertAndSend("verifyExchange",
                "Email",
                order,
                correlationData);
        rabbitTemplate.convertAndSend("verifyExchange",
                "sms",
                order,
                correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause)
    {String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机收到消息确认成功, id:{}", id);
        } else {
            log.error("消息 id:{}未成功投递到交换机,原因是:{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String
            exchange, String routingKey) {
        log.error(" 消 息 {}, 被 交 换 机 {} 退 回 ， 退 回 原 因 :{}, 路 由 key:{}",new
                String(message.getBody()),exchange,replyText,routingKey);

    }
}
