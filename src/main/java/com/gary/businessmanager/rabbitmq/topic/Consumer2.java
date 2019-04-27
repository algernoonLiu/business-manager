package com.gary.businessmanager.rabbitmq.topic;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    private static final String EXCHANGE_NAME = "test_routing_topic";
    private static final String QUEUE_NAME = "test_topic_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.#");

        channel.basicQos(1);

        DefaultConsumer consumer1 = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, Charset.forName("UTF-8"));
                System.out.println("Topic Consumer[2] Msg: " + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Topic Consumer[2] Done. ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer1);
    }

}
