package cucn.collect.common.CommonParts.sms_identify_code;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Kaptcha配置
 * 验证码必备组件 缺少项目无法启动
 */
@Configuration
public class KaptchaConfiguration {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

//        //验证码生成器，默认为DefaultKaptcha
//        //kaptcha.producer.impl
//        //验证码样式引擎，默认为WaterRipple
//        //kaptcha.obscurificator.impl
//        //验证码文本字符渲染，默认为DefaultWordRenderer
//        //kaptcha.word.impl
//        //=====================================================================
//        //尺寸
//        //验证码图片宽度，默认为200
//        properties.setProperty("kaptcha.image.width", "110");
//        //验证码图片高度，默认为50
//        properties.setProperty("kaptcha.image.height", "38");
//        //=====================================================================
//        //边框
//        //是否有边框，默认为yes，可配置yes、no
//        properties.setProperty("kaptcha.border", "yes");
//        //边框颜色，默认Color.BLACK
//        properties.setProperty("kaptcha.border.color", "230,230,230");
//        //边框粗细，默认为1
//        //kaptcha.border.thickness"
//        //=====================================================================
//        //背景
//        //验证码背景生成器，默认为DefaultBackground
//        //kaptcha.background.impl
//        //验证码背景颜色渐进，默认为Color.LIGHT_GRAY
//        properties.setProperty("kaptcha.background.clear.from", "255,255,255");
//        //验证码背景颜色渐进，默认为Color.WHITE
//        properties.setProperty("kaptcha.background.clear.to", "255,255,255");
//        //=====================================================================
//        //噪点
//        //验证码噪点生成对象，默认为DefaultNoise
//        //kaptcha.noise.impl
//        //验证码噪点颜色，默认为Color.BLACK
//        properties.setProperty("kaptcha.noise.color", "0,150,136");
//        //=====================================================================
//        //文本
//        //验证码文本生成器，默认为DefaultTextCreator
//        //kaptcha.textproducer.impl
//        //验证码文本字符内容范围，默认为abcde2345678gfynmnpwx
//        //kaptcha.textproducer.char.string
//        //验证码文本字符长度，默认为5
//        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        //验证码文本字体样式，默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
//        //properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
//        //验证码文本字符大小，默认为40
//        properties.setProperty("kaptcha.textproducer.font.size", "30");
//        //验证码文本字符颜色，默认为Color.BLACK
//        properties.setProperty("kaptcha.textproducer.font.color", "118,118,118");
//        //验证码文本字符间距，默认为2
//        //kaptcha.textproducer.char.space

        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "5");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.textproducer.char.string","1234567890");
        properties.put("kaptcha.image.height","34");
        properties.put("kaptcha.textproducer.font.size","30");
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "38");
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}

