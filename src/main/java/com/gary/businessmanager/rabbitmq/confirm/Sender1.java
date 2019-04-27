package com.gary.businessmanager.rabbitmq.confirm;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender1 {

    private static final String QUEUE_NAME = "test_queue_confirm_normal";

    /**
     * 同步模式
     * 普通模式(单条发送确认)
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // channel设置为confirm模式
        channel.confirmSelect();

        String msg = "confirm msg......";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        if (channel.waitForConfirms()) {
            System.out.println("msg send ok.");
        } else {
            System.out.println("msg send failed.");
        }

        channel.close();
        connection.close();
    }

}
