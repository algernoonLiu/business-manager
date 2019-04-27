package com.gary.businessmanager.rabbitmq.routing;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String EXCHANGE_NAME = "test_routing_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg = "基于路由键的消息";

        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

        System.out.println("Send msg: " + msg);

        channel.close();
        connection.close();

    }
    
}
