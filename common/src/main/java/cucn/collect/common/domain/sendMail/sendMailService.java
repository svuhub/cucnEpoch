package cucn.collect.common.domain.sendMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
public class sendMailService {
    @Value("${spring.mail.username}")
    private String sendUser;

    @Autowired
    private JavaMailSender mailSender;

    /*
     * 发送文本邮件
     * */
    public void sendTextMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sendUser);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }
    /*
    * 发送Html类型邮件
    * */
    public void sendHtmlMail(String to, String subject, String content) throws Exception{
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(sendUser);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mailSender.send(mimeMessage);
    }
    /*
     * 发送附件类型邮件
     * */
    public void sendFileMail(String to, String subject, String content,String filePath) throws Exception{
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(sendUser);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.setText(content,true);
        FileSystemResource fileSystemResource=new FileSystemResource(new File(filePath));
        String fileName=fileSystemResource.getFilename();
        mimeMessageHelper.addAttachment(fileName,fileSystemResource);
        mailSender.send(mimeMessage);
    }

}
