package cucn.collect.common.domain.ImageCode;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequestMapping("image")
public class ImageCode {
    @Autowired
    DefaultKaptcha defaultKaptcha;

    /**
     * 生成验证码图片
     * 这里无校验
     *
     * @param session
     * @param response
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void captcha(HttpSession session, HttpServletResponse response) throws Exception {
        //String openId = session.getAttribute(SessionKeys.WECHAT_OPENID).toString();
        String openId = UUID.randomUUID().toString();
        session.setAttribute("UUID", openId);
        //生成验证码
        String code = defaultKaptcha.createText();
        //保存到Redis.如果要校验。存放Redis
        // stringRedisTemplate.opsForValue().set("IMAGECODEKEY:" + openId, code, 60, TimeUnit.SECONDS);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }


    @GetMapping("/createProfile")
    //将图片写道页面，此方法用以解决
    public void createProfile( HttpServletResponse response) throws Exception {
        String profileUrl = "http://s.go189.cn/static/2018/11/20/15426993492197845.png";
        InputStream inputStream = HttpClients.createDefault().execute(new HttpGet(profileUrl)).getEntity().getContent();
        ServletOutputStream outputStream = response.getOutputStream();
        int len;
        byte[] bys = new byte[1024];
        while ((len = inputStream.read(bys)) != -1) {
            outputStream.write(bys, 0, len);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }
}
