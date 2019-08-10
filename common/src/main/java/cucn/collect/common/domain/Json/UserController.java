package cucn.collect.common.domain.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cucn.collect.common.CommonParts.JSONConstans;
import cucn.collect.common.CommonParts.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
* 1 public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray
2 public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
3 public static final T parseObject(String text, Class clazz); // 把JSON文本parse为JavaBean
4 public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray
5 public static final List parseArray(String text, Class clazz); //把JSON文本parse成JavaBean集合
6 public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
7 public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
8 public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
* */

@Controller
@RequestMapping("/jsontest")
public class UserController {

    // 把JSON文本parse为JSONObject或者JSONArray
    @RequestMapping("type1")
    @ResponseBody
    public Result type1() {
        Map map = new HashMap();
        JSONObject jsonObject = (JSONObject) JSON.parse(JSONConstans.USERINFO);
        //fastjson.jsonObject jsonobject转对象
        User user = JSON.toJavaObject(jsonObject, User.class);
        map.put("jsonObject", jsonObject);
        map.put("user", user);
        return Result.successData(map);
    }

    // 把JSON文本parse为JavaBean
    @RequestMapping("type2")
    @ResponseBody
    public Result type2() {
        Map map = new HashMap();
        User user = JSON.parseObject(JSONConstans.USERINFO, User.class);
        map.put("user", user);
        return Result.successData(map);
    }


    // 把JSON文本parse成JavaBean集合
    @RequestMapping("type3")
    @ResponseBody
    public /*String*/ Result type3() {
        Map map = new HashMap();
        List<User> users = JSONArray.parseArray(JSONConstans.USERINFOS, User.class);
        //preetyFormat为true 返回的是带格式的json
        String jsonArray = JSON.toJSONString(users, false);
        // return jsonArray;
        map.put("users", users);
        map.put("jsonArray", jsonArray);
        return Result.successData(map);
    }

    // 将JavaBean序列化为JSON文本
    @RequestMapping("type4")
    @ResponseBody
    public Result type4() {
        Map map = new HashMap();
        User user = new User();
        user.setAreaCode("025");
        user.setType("2");
        user.setChannel("10001");
        user.setPartyid("112233");
        user.setPhone("17751780934");
        //对象转json字符串
        String jsonArray = JSON.toJSONString(user);
        //对象转json
        Object object = JSON.toJSON(user);

        map.put("jsonArray", jsonArray);
        map.put("object", object);
        return Result.successData(map);
    }


}
