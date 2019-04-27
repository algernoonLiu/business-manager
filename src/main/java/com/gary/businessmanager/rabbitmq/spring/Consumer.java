package com.gary.businessmanager.rabbitmq.spring;

public class Consumer {

    public void listen(String msg) {
        System.out.println("consumer msg: " + msg);
    }

}
