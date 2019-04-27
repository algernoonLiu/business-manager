**自动应答解释**

    boolean autoAck = true;
    # 自动确认模式 一旦rabbitmq将消息分发给消费者，就会从内存中删除
    # 这种情况如果杀死正在执行的消费者，就会丢失正在处理的消息
    
    boolean autoAck = false;
    # 手动确认模式 如果有一个消费者挂掉就会交给其他消费者
    # rabbitmq支持消息应答，消费者发送一个消息应答，告诉rabbitmq这个消息我已经处理完成，你可以删了，然后rabbitmq就删除内存中的消息
    
    消息自动应答默认是打开的 false
    
**如果rabbitmq挂了怎么办？消息不持久化肯定会丢失掉**

    boolean durable = false;
    channel.queueDeclare(QUEUE_NAME, durable, false, false,  null);
    
    # 之前的基础上直接 boolean durable = false; 改为true是不可以的，test_work_queue 这个队列开始的时候是申明为不持久化的，rabbitmq不准许重新定义（不同参数）一个已经存在的队列
    
    
    
---

在 rabbitmq中，我们可以通过持久化数据解决服务器异常的数据丢失问题

但是生产者消息发出后怎么确认消息有没有到达rabbitmq 

两种方式
- AMQP实现了事务机制
- Confirm模式

txSelect txConfirm txRollback