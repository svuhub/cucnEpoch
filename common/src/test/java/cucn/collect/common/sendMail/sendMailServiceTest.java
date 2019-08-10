package cucn.collect.common.sendMail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class sendMailServiceTest {

    @Resource
    cucn.collect.common.domain.sendMail.sendMailService sendMailService;
    @Resource
    TemplateEngine templateEngine;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void sendTestI() {
        sendMailService.sendTextMail("170736012@qq.com","来自SpringBoot","第一封文本邮件");
    }

    @Test
    public void sendTestII() throws Exception{
        String content="<font color='red'>强哥邀请你访问我的博客：http://cuisuqiang.iteye.com/！</font>";
        sendMailService.sendHtmlMail("170736012@qq.com","来自SpringBoot",content);
    }

    @Test
    public void sendTestIII() throws Exception{
       String path="d:/dog.jpg";
        sendMailService.sendFileMail("170736012@qq.com","来自SpringBoot","附件：",path);
    }

    @Test
    @Scheduled(cron = "*/5 * * * * ?")
    public void sendTestIIII() throws Exception{
        Context context=new Context();
        context.setVariable("id",006);
        String Content=templateEngine.process("emailTemplate",context);
        sendMailService.sendHtmlMail("170736012@qq.com","来自SpringBoot",Content);
    }

    @Test
    public void sendTestIIIII() {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (int i = 0; i < 1000000; i++) {
                    String key = "123" + i;
                    connection.zCount(key.getBytes(), 0,Integer.MAX_VALUE);
                }
                List<Object> result=connection.closePipeline();
                return null;
            }
        });

    }




}