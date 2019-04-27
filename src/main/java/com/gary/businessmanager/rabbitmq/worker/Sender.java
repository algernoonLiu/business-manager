package com.gary.businessmanager.rabbitmq.worker;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static  final String QUEUE_NAME = "test_work_queue";

    /**
     * 轮询分发
     *                            \-------Consumer1
     * Producer ------ Work Queue \
     *                            \------- Consumer2
     *
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false,  null);

        for (int i = 0; i < 50; i++) {
            String msg = "Work Queue Msg " + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("Send Msg: " + msg);
            Thread.sleep(100);
        }
    }

}
