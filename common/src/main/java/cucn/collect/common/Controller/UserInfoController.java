package cucn.collect.common.Controller;


import cucn.collect.common.domain.MyBatis.DTO.UserExtroInfo;
import cucn.collect.common.domain.MyBatis.Service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/*
* 新纪元接口测试
* */
@Controller
@RequestMapping("userInfo")
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /*
     * 查询所有
     * */
    @RequestMapping("/test01")
    @ResponseBody
    public void getAllInfo() {
        List<UserExtroInfo> userInfos = userInfoService.getAll();
        String name = null;
        String pin = null;
        String partyId = null;
        for (int i = 0; i < userInfos.size(); i++) {
            String mobile = null;
            String area = null;
            try {
                mobile = userInfos.get(i).getUei_bind_identity();
                area = userInfos.get(i).getUei_area();
                logger.info(mobile + "   " + area);
            } catch (Exception E) {
                logger.info("参数获取失败");
                E.printStackTrace();
                mobile="x";
                area="x";
            }
            try {
                Map<String, String> map = userInfoService.UserInfoByIdentity(mobile, area);
                name = map.get("name");
                pin = map.get("pin");
                partyId = map.get("partyId");
            }catch (Exception e){
                e.printStackTrace();
                logger.info("接口调用失败");
                name="x";
                pin = "x";
                partyId = "x";
            }
            try{
                userInfoService.insertInfo(name, pin, partyId,mobile);
                logger.info("插入成功"+i);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("插入失败"+name+pin+partyId);
            }

        }
    }


}
