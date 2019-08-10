package cucn.collect.common.domain.RabbitMQ;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*@Component*/
public class ReceiveMqReceive {
    @RabbitListener(queues = {RabbitConfigrution.QUEUE_INFORM_EMAIL})
    public void receiveType1(Message message, Channel channel) {
        System.out.println(message + "" + channel);
    }

    @RabbitListener(queues = {RabbitConfigrution.QUEUE_INFORM_TELEPHONE})
    public void receiveType2(Message message, Channel channel) {
        System.out.println(message);
    }
}
