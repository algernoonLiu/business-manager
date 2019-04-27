package com.gary.businessmanager.rabbitmq.confirm;

import com.gary.businessmanager.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Sender3 {

//    private static final String QUEUE_NAME = "test_queue_confirm_async";
    private static final String QUEUE_NAME = "test_queue_confirm_normal";

    /**
     * 异步模式
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // channel设置为confirm模式
        channel.confirmSelect();

        // 未确认消息的标识
        final SortedSet<Long> unconfirmedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        // 监听channel
        channel.addConfirmListener(new ConfirmListener() {
            // 没有问题的 Handle Ack
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handle Ack --- multiple");
                    unconfirmedSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handle Ack --- multiple false");
                    unconfirmedSet.remove(deliveryTag);
                }
            }

            // 可以加一些重试机制 1s 3s 10s 到达一定次数不再重试
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handle Nack --- multiple");
                    unconfirmedSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handle Nack --- multiple false");
                    unconfirmedSet.remove(deliveryTag);
                }
            }
        });

        String msg = "async confirm msg .........";

        while(true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            unconfirmedSet.add(seqNo);
        }
    }

}
