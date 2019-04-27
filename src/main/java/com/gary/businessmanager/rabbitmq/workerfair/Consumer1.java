package com.gary.businessmanager.rabbitmq.workerfair;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class Consumer1 {

    private static  final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);

        // 保证 一次只分发一条消息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, Charset.forName("UTF-8"));
                System.out.println("Consumer[1] Msg: " + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Consumer[1] Done. ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

}
