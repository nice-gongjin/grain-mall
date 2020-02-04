package com.gj.gmall.consumer;

import com.gj.gmall.util.myException.MyException;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TopicReciver {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "topic.order", containerFactory = "simpleRabbitListenerContainerFactory")
    public void orderReceiver(Message message, Channel channel) throws IOException {
        System.out.println("topic.order -- Receiver: " + message);
        System.out.println("当前时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (null != message && null != message.getBody() && 0 != message.getBody().length) {
            try {
                /**
                 * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
                 * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
                 * 解决方案：手动ACK,或者try-catch 然后在 catch 里面将错误的消息转移到其它的系列中去
                 */
                //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                /**
                 * basicRecover方法是进行补发操作，其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
                 * 设置为false是只补发给当前的consumer
                 */
                channel.basicRecover(false);
            }
        }
    }

    @RabbitListener(queues = "topic.reorder", containerFactory = "simpleRabbitListenerContainerFactory")
    public void reorderReceiver(Message message, Channel channel) throws IOException {
        System.out.println("topic.reorder -- Receiver: " + message);
        System.out.println("当前时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (null != message && null != message.getBody() && 0 != message.getBody().length) {
            try {
                /**
                 * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
                 * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
                 * 解决方案：手动ACK,或者try-catch 然后在 catch 里面将错误的消息转移到其它的系列中去
                 */
                //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                /**
                 * basicRecover方法是进行补发操作，其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
                 * 设置为false是只补发给当前的consumer
                 */
                channel.basicRecover(false);
            }
        }
    }

    @RabbitListener(queues = "topic.stock", containerFactory = "simpleRabbitListenerContainerFactory")
    public void stockReceiver(Message message, Channel channel) throws IOException {
        System.out.println("topic.stock -- Receiver: " + message);
        System.out.println("当前时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (null != message && null != message.getBody() && 0 != message.getBody().length) {
            try {
                /**
                 * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
                 * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
                 * 解决方案：手动ACK,或者try-catch 然后在 catch 里面将错误的消息转移到其它的系列中去
                 */
                //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                /**
                 * basicRecover方法是进行补发操作，其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
                 * 设置为false是只补发给当前的consumer
                 */
                channel.basicRecover(false);
            }
        }
    }

    @RabbitListener(queues = "topic.error", containerFactory = "simpleRabbitListenerContainerFactory")
    public void msgReceiver(Message message, Channel channel) throws IOException {
        System.out.println("topic.msg -- Receiver: " + message);
        System.out.println("进行死信消息的转发： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("当前时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (null != message && null != message.getBody() && 0 != message.getBody().length) {
            try {
                /**
                 * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
                 * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
                 * 解决方案：手动ACK,或者try-catch 然后在 catch 里面将错误的消息转移到其它的系列中去
                 */
                //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                //当然 如果这个订单处理失败了, 我们也需要告诉rabbitmq 告诉他这条消息处理失败了 可以退回 也可以遗弃
                //前两个参数 和上面的意义一样， 最后一个参数 就是这条消息是返回到原队列 还是这条消息作废 就是不退回了。
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    // 示列方法: 处理业务时参照此方法
    private void getMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody(),"UTF-8"));
        System.out.println(message.getBody());
        //这里我们调用了一个下单方法  如果下单成功了 那么这条消息就可以确认被消费了
        boolean f = true;
        if (f){
            //传入这条消息的标识， 这个标识由rabbitmq来维护 我们只需要从message中拿出来就可以
            //第二个boolean参数指定是不是批量处理的 什么是批量处理我们待会儿会讲到
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }else {
            //当然 如果这个订单处理失败了, 我们也需要告诉rabbitmq 告诉他这条消息处理失败了 可以退回 也可以遗弃
            // 要注意的是 无论这条消息成功与否  一定要通知 就算失败了 如果不通知的话 rabbitmq端会显示这条消息一直处于未确认状态，
            // 那么这条消息就会一直堆积在rabbitmq端 除非与rabbitmq断开连接 那么他就会把这条消息重新发给别人 所以 一定要记得通知！
            //前两个参数 和上面的意义一样， 最后一个参数 就是这条消息是返回到原队列 还是这条消息作废 就是不退回了。
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            //其实 这个API也可以去告诉rabbitmq这条消息失败了  与basicNack不同之处 就是 他不能批量处理消息结果 只能处理单条消息
            // 其实basicNack作为basicReject的扩展开发出来的
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

}
