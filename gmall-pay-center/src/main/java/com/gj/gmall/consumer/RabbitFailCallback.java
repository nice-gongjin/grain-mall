package com.gj.gmall.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class RabbitFailCallback implements RabbitTemplate.ReturnCallback {

    /**
     *  消息发送失败的时候会调用我们事先准备好的回调函数，并且把失败的消息 和失败原因等 返回过来
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("Rabbit的失败回调函数RabbitFailCallback的调用； Message: {}", message);
        log.info("Rabbit的失败回调函数RabbitFailCallback的调用； replyCode: {}", replyCode);
        log.info("Rabbit的失败回调函数RabbitFailCallback的调用； replyText: {}", replyText);
        log.info("Rabbit的失败回调函数RabbitFailCallback的调用； exchange: {}", exchange);
        log.info("Rabbit的失败回调函数RabbitFailCallback的调用； routingKey: {}", routingKey);
    }

}
