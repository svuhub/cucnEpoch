package cucn.collect.common.Controller;


import cucn.collect.common.CommonParts.CookieKeys;
import cucn.collect.common.CommonParts.JSONConstans;
import cucn.collect.common.CommonParts.Result;
import cucn.collect.common.CommonParts.SnowFlake;

import cucn.collect.common.CommonParts.utils.RedisKeys;
import cucn.collect.common.domain.JdbcTemplate.SalgradeRepository;

import cucn.collect.common.domain.MyBatis.Service.SchedulrdService;
import cucn.collect.common.domain.Test.StringService;
import cucn.collect.common.filters.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/test")
@Slf4j
public class ControllerForTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerForTest.class);

    @Value("${sectionNbr}")
    private String sectionNbr;
    @Autowired
    private StringService stringService;

    @Autowired
    private SchedulrdService schedulrdService;

    @Autowired
    private SalgradeRepository salgradeRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/type1")
    @ResponseBody
    public String result1() {
        String message = "aaa";
        try {
            File file = new File("a.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.append(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Hello World!";
    }

    @RequestMapping("/type2")
    @ResponseBody
    public Map result2() {
        Map map = new HashMap();
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer", "Roger Federer",
                "Andy Murray", "Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players = Arrays.asList(atp);
        for (String player : players) {
            System.out.print(player + "; ");
        }
        players.forEach((player) -> System.out.print(player + "; "));
        players.forEach(System.out::println);
        map.put("players", players);
        map.put("test", 1);
        return map;
    }

    @RequestMapping("/type3")
    @ResponseBody
    public Result result3() {
        Map map = new HashMap();
        //通过value引入配置文件的值
        String numbers = sectionNbr;
        //通过逗号分割成字符串
        String[] numberArrays = numbers.split(",");
        //数组转成集合
        List<String> numberList = Arrays.asList(numberArrays);
        //labada表达式的变例
        numberList.forEach(System.out::println);
        map.put("list", numberList);
        return Result.successData(map);
    }

    @RequestMapping("type4")
    public void result4(HttpServletResponse response) throws IOException {
        response.sendRedirect("Http://www.baidu.com");
        //return new ModelAndView("redirect:http://4399.natapp1.cc/wx/star/starInfo-detail?param=" + dates);
        //response.sendRedirect("http://4399.natapp1.cc/wx/star/starInfo-detail?param="+dates);
    }


    @RequestMapping(value = {"/type5", "/type5/{source}"})
    @ResponseBody
    public String type666(@PathVariable(value = "source", required = false) String source) {
        if (source != null) {
            return source;
        } else {
            return "default";
        }

    }

    @RequestMapping("/type6")
    @ResponseBody
    public boolean type7() {
        String s = "[ErrorCode]1234[ErrorMsg]异常了互斥:com.alibaba.fastjson.JSONException: syntax error, pos 1, json : <?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ContractRoot><tcpCont><ActionCode></ActionCode><RspTime>2019-04-04T15:15:07.128+0800</RspTime><TransactionID>JS00000001201904046419472701</TransactionID><response><RspCode>9074</RspCode><RspDesc>远程服务器连接超时，请检查HTTP服务是否正常 ( call PPM_查询销售品详情 error. address=http://132.252.129.148/ppm-service/service/cpc_ppm_qryOfferDetail  ,Not use proxy. ) StackTrace:java.net.SocketTimeoutException: Read timed out\n" +
                "\tat java.net.SocketInputStream.socketRead0(Native Method)\n" +
                "\tat java.net.SocketInputStream.socketRead(SocketInputStream.java:116)\n" +
                "\tat java.net.SocketInputStream.read(SocketInputStream.java:171)\n" +
                "\tat java.net.SocketInputStream.read(SocketInputStream.java:141)\n" +
                "\tat java.io.BufferedInputStream.fill(BufferedInputStre</RspDesc><RspType>9</RspType></response></tcpCont></ContractRoot>\n" +
                "\tat com.alibaba.fastjson.parser.DefaultJSONParser.parse(DefaultJSONParser.java:1394)\n" +
                "\tat com.alibaba.fastjson.parser.DefaultJSONParser.parse(DefaultJSONParser.java:1301)\n" +
                "\tat com.alibaba.fastjson.JSON.parse(JSON.java:148)\n" +
                "\tat com.alibaba.fastjson.JSON.parse(JSON.java:139)\n" +
                "\tat com.alibaba.fastjson.JSON.parseObject(JSON.java:212)\n" +
                "\tat cn.js189.uoc.bsssmart.service.impl.UocRoleIdService.getRoleIdBy(UocRoleIdService.java:42)\n" +
                "\tat cn.js189.uoc.bsssmart.service.impl.BssSmartOrderServiceImpl.addMulProduct(BssSmartOrderServiceImpl.java:220)\n" +
                "\tat cn.js189.uoc.bsssmart.service.impl.BssSmartOrderServiceImpl.orderProductGenerateOrder(BssSmartOrderServiceImpl.java:114)\n" +
                "\tat cn.js189.uoc.bsssmart.SmartBssProductController.orderProductCheck(SmartBssProductController.java:330)\n" +
                "\tat cn.js189.uoc.bsssmart.SmartBssProductController.check(SmartBssProductController.java:145)\n" +
                "\tat cn.js189.uoc.service.BusinessService.check(BusinessService.java:82)\n" +
                "\tat cn.js189.uoc.facade.BusinessServiceImpl.check(BusinessServiceImpl.java:245)\n" +
                "\tat com.alibaba.dubbo.common.bytecode.Wrapper1.invokeMethod(Wrapper1.java)\n" +
                "\tat com.alibaba.dubbo.rpc.proxy.javassist.JavassistPr";
        return stringService.checkResult(s);
    }

    @GetMapping("/type7")
    @ResponseBody
    public String type8() {
        logger.info("被访问了一次");
        Long trafficId = SnowFlake.instant().nextId();
        int i = 1 / 0;
        return trafficId.toString();
    }

    @RequestMapping("/type8")
    @ResponseBody
    public Boolean type9() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginTime = LocalDateTime.of(2018, 8, 22, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2018, 8, 22, 5, 0, 0);
        if (now.isAfter(endTime)) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping("/type9")
    @ResponseBody
    public String type10() {
        return schedulrdService.TestPrint();
    }

    @RequestMapping("/type10")
    @ResponseBody
    public String type11() {
        String url = "appcode=test&deviceno=18966778899&requestdate=20170512103121&signkey=123456&targeturl=https://momall.telefen.com/ProvinceWechat";
        String md5Password = DigestUtils.md5DigestAsHex(url.getBytes());
        return md5Password;
    }


    /**
     * 其他渠道号码接入，存入Cookie中
     */
    @RequestMapping("/type11")
    public void saveDeviceNum(@RequestParam(value = "accessNumber", required = false)
                                          String accessNumber, HttpServletResponse response) throws UnsupportedEncodingException {
        if(!StringUtils.isNotBlank(accessNumber)){
            accessNumber="1";
        }
        Cookie cookie = new Cookie(CookieKeys.OTHER_CHANNEL_DEVICE_NUMBER, accessNumber);
        URLEncoder.encode(CookieKeys.OTHER_CHANNEL_DEVICE_NUMBER, "utf-8");
        cookie.setMaxAge(300);//TODO:有效期时间
        response.addCookie(cookie);
    }

    /**
     * 从Cookie中取设备号码
     */
    @RequestMapping("/type12")
    @ResponseBody
    public Result getDeviceNum(HttpServletRequest request) {
        String deviceNum = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieKeys.OTHER_CHANNEL_DEVICE_NUMBER)) {
                    deviceNum = cookie.getValue();
                }
            }
        }
        return Result.successData(deviceNum);
    }


    @RequestMapping("/test13")
    @ResponseBody
    public void test13(HttpSession session, String phoneNum) {
        session.setAttribute("phoneNum", phoneNum);
    }

    @RequestMapping("/test14")
    @ResponseBody
    public String test14(HttpSession session) {
        session.setAttribute("name1", "petter1");
        return RequestHolder.getCurrentDeviceNum() + RequestHolder.getCurrentDeviceName();
    }


    @RequestMapping("type15")
    @ResponseBody
    public String type15(HttpSession session) {
        String openid = "xx";
        //初始化 存
        stringRedisTemplate.opsForList().leftPushAll(openid, Arrays.asList(JSONConstans.arrays));
        //第一个
        if (true) {
            stringRedisTemplate.opsForList().rightPop(openid);
        } else {
            stringRedisTemplate.opsForList().rightPop(openid);
            stringRedisTemplate.opsForList().rightPop(openid);
        }


        if (true) {
            stringRedisTemplate.opsForList().rightPop(openid);
            stringRedisTemplate.opsForList().size(openid);
        }

        return "1";
    }


    @RequestMapping("/test16")
    @ResponseBody
    public void test16() {
        redisTemplate.opsForHash().increment("HASH", "COUNT", 1);
    }


    @RequestMapping("/test17")
    @ResponseBody
    public Map test17() {
        Map map = new HashMap();
        Object n = redisTemplate.opsForHash().get("HASH", "COUNT");
        Object b = redisTemplate.opsForHash().get("HASH", "COUNT1");
        map.put("n", n);
        map.put("b", b);
        return map;

    }

    @RequestMapping("/test18")
    @ResponseBody
    public void test18(String channel, String openId) {
        String currentDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String exist = redisTemplate.opsForValue().getAndSet(RedisKeys.BIZMOBILE_VISIT_COUNTUV_USERS + currentDay + channel + openId, "1");
        redisTemplate.expire(RedisKeys.BIZMOBILE_VISIT_COUNTUV_USERS + currentDay + channel + openId, 24, TimeUnit.HOURS);
        if (StringUtils.isBlank(exist)) {
            redisTemplate.opsForHash().increment(RedisKeys.BIZMOBILE_VISIT_COUNTUV + currentDay, channel, 1);
        }
    }

    @RequestMapping(value="/test20",method = RequestMethod.GET)
    @ResponseBody
    public String test19(String channel, String openId) {
        String key = String.format("%s:%s", "userId", "actionKey");
        return key
                ;
    }

    @RequestMapping(value="/test20",method = RequestMethod.POST)
    @ResponseBody
    public String test20(String channel, String openId) {
        for(int i=0;i<100;i++){
            stringRedisTemplate.opsForValue().set("key99"+i,"0");
        }
        return "0";
    }


}
