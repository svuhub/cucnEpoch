package cucn.collect.common.domain.RabbitMQ;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*@Controller
@RequestMapping(value = "/rabbit")*/
public class RabbitMqSend {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMqSend.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/sendType1")
    public void SendMQType1() {
        String content = "Haloha";
        /*
         * 交换机名称 key 内容
         * */
        rabbitTemplate.convertAndSend(RabbitConfigrution.EXCHANGE_TOPICS_INFORM, RabbitConfigrution.QUEUE_INFORM_EMAIL, content);
    }

    @RequestMapping(value = "/sendType2")
    public void SendMQType2() {
        String content = "TypeTwoMessage";
        /*
         * 交换机名称 key 内容
         * */
        rabbitTemplate.convertAndSend(RabbitConfigrution.EXCHANGE_TOPICS_INFORM, RabbitConfigrution.QUEUE_INFORM_TELEPHONE);
    }

}
