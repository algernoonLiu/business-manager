package com.gary.businessmanager.rabbitmq.workerfair;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static  final String QUEUE_NAME = "test_work_queue";

    /**
     * 公平分发 - 能者多劳
     *                            \-------Consumer1
     * Producer ------ Work Queue \
     *                            \------- Consumer2
     *
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);

        /**
         * 每个消费者确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         *
         * 限制发送给同一个消费者不得超过一条消息
         */
        int  prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i < 50; i++) {
            String msg = "Work Fair Queue Msg " + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("Send Msg: " + msg);
            Thread.sleep(100);
        }
    }

}
