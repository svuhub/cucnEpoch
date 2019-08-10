package cucn.collect.common.domain.SignIn;

import cucn.collect.common.CommonParts.RandomString;
import cucn.collect.common.CommonParts.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignService signService;
    /*
     * 1.签到
     * 2.用户当月签到状态
     * 3.第一天5 第二天10 第三天15 第四天20 第五天20
     * 4.用户昨日是否签到
     * */

    @RequestMapping("/signIndex")
    public String signIndex() {
        return "signIndex";
    }

    /*
     * 当天签到实际操作
     * */
    @RequestMapping("doSign")
    @ResponseBody
    public boolean doSign() {
        String openid = RandomString.generateString(5);
        LocalDate date = LocalDate.now();
        return signService.doSign(openid, date);
    }

    /*
     *获取用户某天是否签到
     * */
    @RequestMapping("isSign")
    @ResponseBody
    public boolean isSign() {
        String openid = RandomString.generateString(5);
        LocalDate date = LocalDate.now();
        return signService.doSign(openid, date);
    }

    /*
     *获取用户签到次数
     * */
    @RequestMapping("countSign")
    @ResponseBody
    public long countSign() {
        String openid = RandomString.generateString(5);
        LocalDate date = LocalDate.now();
        return signService.countsActive(openid, date);
    }

    /*
     * 获取用户近5日的签到情况
     * */
    @RequestMapping("getFiveDaysSign")
    @ResponseBody
    public List<Long> getFiveDaysSign() {
        String openid = /*RandomString.generateString(5)*/"157RS";
        LocalDate date = LocalDate.now();
        return signService.getDaysSign(openid, date);
    }


}
