package com.gj.gmall.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

@Slf4j
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("Rabbit的消息确认机制函数RabbitConfirmCallback的调用； correlationData: {}", correlationData);
        log.info("Rabbit的消息确认机制函数RabbitConfirmCallback的调用； ack: {}", ack);
        log.info("Rabbit的消息确认机制函数RabbitConfirmCallback的调用； cause: {}", cause);
    }

}
