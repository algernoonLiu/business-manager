package com.gary.businessmanager.rabbitmq.simple;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            // 处理到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");
                System.out.println("Consume msg: " + msg);

            }
        };

        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }


    @Deprecated
    public static void queueConsume() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        QueueingConsumer qm = new QueueingConsumer(channel);

        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, qm);
        while(true) {
            QueueingConsumer.Delivery delivery = qm.nextDelivery();
            byte[] body = delivery.getBody();
            String msg = new String(body);
            System.out.println("Consume msg: " + msg);
        }
    }
}
