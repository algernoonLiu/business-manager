package com.gary.businessmanager.rabbitmq.ps;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        // 申明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); // 分发

        String msg = "发布订阅 fanout 分发消息！";

        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        System.out.println(msg);

        channel.close();
        connection.close();
    }
}
