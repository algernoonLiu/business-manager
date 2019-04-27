package com.gary.businessmanager.rabbitmq.tx;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSender {

    private static final String QUEUE_NAME = "test_queue_tx_amqp";

    /*
     * 这种方式 Select/Commit/Rollback 事务机制会有大量的请求，降低吞吐量
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "TX msg ......";

        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//            int i = 1/0;
            channel.txCommit();
            System.out.println("Send Msg: " +  msg);
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("Exception occur. rollback...");
        }

        channel.close();
        connection.close();
    }
}
