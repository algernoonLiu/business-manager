package com.gary.businessmanager.rabbitmq.simple;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String QUEUE_NAME = "test_simple_queue";

    /**
     *
     * Producer ------ Work Queue ------- Consumer
     *
     */

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中获取一个通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "这个是一个简单队列中文消息！";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("Send Msg Success!");

        channel.close();
        connection.close();
    }


}
