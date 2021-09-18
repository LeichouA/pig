package com.pig.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueConfig {

    public static final String EXCHANGE = "verifyExchange";

    public static final String QUEUE_EMAIL = "QEmail";

    public static final String QUEUE_SMS = "QSms";

    public static final String EMAIL_DEAD_EXCHANGE = "EmailDeadExchange";

    public static final String EMAIL_QUEUE_DEAD = "QEmailDead";

    public static final String SMS_DEAD_EXCHANGE = "SmsDeadExchange";

    public static final String SMS_QUEUE_DEAD = "QSmsDead";



    /**
     *  邮件 短信交换机
     * @return
     */
    @Bean("verifyExchange")
    public DirectExchange verifyExchange(){
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 邮件 死信交换机
     * @return
     */
    @Bean("EmailDeadExchange")
    public DirectExchange EmailDeadExchange(){
        return new DirectExchange(EMAIL_DEAD_EXCHANGE);
    }

    /**
     *  短信死信交换机
     * @return
     */
    @Bean("SmsDeadExchange")
    public DirectExchange SmsDeadExchange(){
        return new DirectExchange(SMS_DEAD_EXCHANGE);
    }


    /**
     * 邮件队列
     * @return
     */
    @Bean("QEmail")
    public Queue QEmail(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", EMAIL_DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "EtoD");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);

        return QueueBuilder.durable(QUEUE_EMAIL).withArguments(args).build();
    }

    /**
     *  交换机绑定邮件队列
     * @return
     */
    @Bean
    public Binding queueBindingEmail(@Qualifier("QEmail") Queue queueEmail,
                                  @Qualifier("verifyExchange") DirectExchange verifyExchange){
        return BindingBuilder.bind(queueEmail).to(verifyExchange).with("Email");
    }

    /**
     *  短信队列
     * @return
     */
    @Bean("QSms")
    public Queue QSms(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", SMS_DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "StoD");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_SMS).withArguments(args).build();
    }

    /**
     *  绑定短信队列
     * @return
     */
    @Bean
    public Binding queueBindingSms(@Qualifier("QSms") Queue QSms,
                                     @Qualifier("verifyExchange") DirectExchange verifyExchange){
        return BindingBuilder.bind(QSms).to(verifyExchange).with("sms");
    }

    /**
     *  邮件死信队列
     * @return
     */
    @Bean("QEmailDead")
    public Queue QEmailDead(){
        return new Queue(EMAIL_QUEUE_DEAD);
    }


    /**
     *  短信死信队列
     * @return
     */
    @Bean("QSmsDead")
    public Queue QSmsDead(){
        return new Queue(SMS_QUEUE_DEAD);
    }

    /**
     *  绑定邮件死信队列
     * @return
     */
    @Bean
    public Binding deadLetterBindingEmail(@Qualifier("QEmailDead") Queue QEmailDead,
                                        @Qualifier("EmailDeadExchange") DirectExchange EmailDeadExchange){
        return BindingBuilder.bind(QEmailDead).to(EmailDeadExchange).with("EtoD");
    }

    /**
     *  绑定短信死信队列
     * @return
     */
    @Bean
    public Binding deadLetterBindingSms(@Qualifier("QSmsDead") Queue QSmsDead,
                                        @Qualifier("SmsDeadExchange") DirectExchange SmsDeadExchange){
        return BindingBuilder.bind(QSmsDead).to(SmsDeadExchange).with("StoD");
    }
}
