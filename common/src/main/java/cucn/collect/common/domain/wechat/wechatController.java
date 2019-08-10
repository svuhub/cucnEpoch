package cucn.collect.common.domain.wechat;

import com.alibaba.fastjson.JSONObject;
import cucn.collect.common.CommonParts.utils.HttpClientUtil;
import cucn.collect.common.domain.wechat.CheckUtil;
import cucn.collect.common.domain.wechat.TextMessage;
import cucn.collect.common.domain.wechat.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Slf4j
/*@Controller*/
public class wechatController {
    private static final String ROBOT = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

    @RequestMapping(value = "/dispatCherServlet", method = RequestMethod.GET)
    @ResponseBody
    public String dispatCherServletGet(String signature, String timestamp, String nonce, String echostr) {
        // 1.验签
        boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
        // 2.如果是微信来源 返回 随机数echostr
        if (!checkSignature) {
            return null;
        }
        return echostr;
    }

    @RequestMapping(value = "/dispatCherServlet", method = RequestMethod.POST)
    public void dispatCherServletPost(HttpServletRequest reqest, HttpServletResponse response) throws Exception {
        reqest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> mapResult = XmlUtils.parseXml(reqest);
        if (mapResult == null) {
            return;
        }
        String msgType = mapResult.get("MsgType");
        PrintWriter writer = response.getWriter();
        switch (msgType) {
            case "text":
                // 获取消息内容
                String content = mapResult.get("Content");
                // 发送消息
                String toUserName = mapResult.get("ToUserName");
                // 来自消息
                String fromUserName = mapResult.get("FromUserName");
                //机器人
                String requestResultJson = HttpClientUtil.doGet(ROBOT + content);
                JSONObject jsonObject = new JSONObject().parseObject(requestResultJson);
                String result = jsonObject.getString("result");
                String msg = null;
                if (result.equals("0")) {
                    msg = jsonObject.getString("content");
                } else {
                    msg = "111！";
                }
                String resultTestMsg = setTextMess(msg, toUserName, fromUserName);
                writer.print(resultTestMsg);
                break;

            default:
                break;
        }
        writer.close();

    }

    public String setTextMess(String content, String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(fromUserName);
        textMessage.setToUserName(toUserName);
        textMessage.setContent(content);
        textMessage.setMsgType("text");
        textMessage.setCreateTime(new Date().getTime());
        String messageToXml = XmlUtils.messageToXml(textMessage);
        log.info("####setTextMess()###messageToXml:" + messageToXml);
        return messageToXml;
    }
}


