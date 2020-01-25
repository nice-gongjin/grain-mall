package com.gj.gmall.config;

import com.gj.gmall.consumer.RabbitConfirmCallback;
import com.gj.gmall.consumer.RabbitFailCallback;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.43.254",5672);
        // 我这里直接在构造方法传入了
        // connectionFactory.setHost();
        // connectionFactory.setPort();
        connectionFactory.setUsername("gmall");
        connectionFactory.setPassword("gmall");
        connectionFactory.setVirtualHost("gmallhost");
        //是否开启消息确认机制
        connectionFactory.setPublisherConfirms(true);

        return connectionFactory;
    }

    @Bean
    public Queue topicOrder(){
        return new Queue("topic.order",true);
    }

    @Bean
    public Queue topicReorder(){
        return new Queue("topic.reorder",true);
    }

    @Bean
    public Queue topicStock(){
        return new Queue("topic.stock",true);
    }

    @Bean
    public Queue topicMessage(){
        return new Queue("topic.msg",true);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    /**
     *  交换器和队列进行了绑定。
     *      在绑定的过程中多了一个条件with,这是一种通配符方式的匹配，. 为分隔符，*代表一个，#表示0个或者多个，
     *      如上面的topic.#就可已匹配，topic，topic.z，topic.ma.z.z.z等，而topic.*.z就可以匹配topic.m.z,topic.z.z等，
     *      而topic.msg就只能匹配topic.msg条件的消息
     */
    @Bean
    public Binding bindingOrder(){
        return BindingBuilder.bind(topicOrder()).to(topicExchange()).with("topic.order");
    }

    @Bean
    public Binding bindingReorder(){
        return BindingBuilder.bind(topicReorder()).to(topicExchange()).with("topic.reorder");
    }

    @Bean
    public Binding bindingStock(){
        return BindingBuilder.bind(topicStock()).to(topicExchange()).with("topic.stock");
    }

    @Bean
    public Binding bindingMessage(){
        return BindingBuilder.bind(topicMessage()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //开启mandatory模式（开启失败回调）
        template.setMandatory(true);
        //指定消息的发送方确认模式
        template.setConfirmCallback(new RabbitConfirmCallback());
        //指定失败回调接口的实现类
        template.setReturnCallback(new RabbitFailCallback());

        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        //这边设置消息确认方式由自动确认变为手动确认
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消息预取的数量
        containerFactory.setPrefetchCount(200);

        return containerFactory;
    }

}
