package cucn.collect.common.domain.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 * rabbitMQ配置类
 * 本类中 声明交换机 队列 以及之间的绑定
 * */
/*@Configuration*/
public class RabbitConfigrution {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    public static final String QUEUE_INFORM_TELEPHONE = "queue_inform_telephone";

    /*
     * 声明交换机
     * */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        //返回一种交换机的类型
        // ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
        //durable(true) 持久化 MQ重启后消息还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    /*
     * 声明队列1
     * */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /*
     *声明队列2
     **/
    @Bean(QUEUE_INFORM_TELEPHONE)
    public Queue QUEUE_INFORM_TELEPHONE() {
        return new Queue(QUEUE_INFORM_TELEPHONE);
    }


    /*
     * 交换机队列绑定声明
     * */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_INFORM_EMAIL).noargs();

    }

    /*
     * 交换机队列绑定声明
     * */
    @Bean
    public Binding BINDING_QUEUE_INFORM_TELEPHONE(@Qualifier(QUEUE_INFORM_TELEPHONE) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_INFORM_TELEPHONE).noargs();

    }

}
