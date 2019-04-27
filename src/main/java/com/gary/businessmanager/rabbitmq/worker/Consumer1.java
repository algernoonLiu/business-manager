package com.gary.businessmanager.rabbitmq.worker;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class Consumer1 {

    private static  final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);

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
                }
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

}
