package cucn.collect.common.Controller;


import com.github.pagehelper.PageInfo;
import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import cucn.collect.common.domain.MyBatis.DTO.RoleInfo;
import cucn.collect.common.domain.MyBatis.DTO.UserExtroInfo;
import cucn.collect.common.domain.MyBatis.Service.AcountService;
import cucn.collect.common.domain.MyBatis.Service.RoleInfoService;
import cucn.collect.common.domain.MyBatis.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mybatis")
public class MyBatisController {
    @Autowired
    private AcountService acountService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/insert")
    public Integer insertUser(BigDecimal a, String b) {
        MyAcount myAcount = new MyAcount();
        myAcount.setAr_payamont(a);
        myAcount.setAr_payName(b);
        Integer c = acountService.insertUser(myAcount);
        return c;
    }

    @RequestMapping("/selectById")
    public MyAcount selectById(int id) {
        return acountService.selectById(id);
    }

    @RequestMapping("/selectAllSource1")
    public List<MyAcount> getAll() {
        return acountService.getAll();
    }

    @RequestMapping("/selectAllSource2")
    public List<RoleInfo> getAllSource2() {
        return roleInfoService.getAll();
    }

    @RequestMapping("/selectAllSource3")
    public List<UserExtroInfo> getAllSource3() {
        return userInfoService.getAllUser();
    }

    @RequestMapping("/selectByName")
    public List<MyAcount> selectByName(String name) {
        return acountService.getInfoByName(name);
    }

    @RequestMapping("/selectAllSource1Bypage")
    public PageInfo<MyAcount> selectAllSource1Bypage() {
        return acountService.selectAllByPage(20,11);
    }


    /*
     * lambda
     * 筛选不符合条件的项(写法一)
     * */
    @RequestMapping("/lbdStream01")
    @ResponseBody
    public Object acounts() {
        List<MyAcount> myAcountList = acountService.getAll();
        //遍历输出
        // myAcountList.forEach((MyAcount acont)->System.out.println(acont.getAr_id()));
        Stream<MyAcount> stream = myAcountList.stream();
        myAcountList = stream.filter(p -> p.getAr_payamont().intValue() > 3 && p.getAr_id() > 20).collect(Collectors.toList());
        return myAcountList;
    }

    /*
     * lambda
     * 筛选不符合条件的项（写法二）
     * */
    @RequestMapping("/lbdStream02")
    @ResponseBody
    public Object acountsII() {
        List<MyAcount> myAcountList = acountService.getAll();
        /*  Stream<MyAcount> stream = myAcountList.stream();*/
        myAcountList = myAcountList.stream().filter(p -> p.getAr_payamont().intValue() > 30).collect(Collectors.toList());
        return myAcountList;
    }

    /*
     * lambda
     * 根据字段groupby
     * */
    @RequestMapping("/lbdStream03")
    @ResponseBody
    public Object acountsIII() {
        List<Map<String, Object>> resultDtos = new ArrayList<>();
        List<MyAcount> myAcountList = acountService.getAll();
        Stream<MyAcount> stream = myAcountList.stream();
        Map<String, List<MyAcount>> count = stream.collect(Collectors.groupingBy(MyAcount::getAr_payName));
        Set<Map.Entry<String, List<MyAcount>>> entrySet = count.entrySet();
        for (Map.Entry<String, List<MyAcount>> entry : entrySet) {
            Map<String, Object> node = new HashMap<>();
            node.put("name", entry.getKey());
            node.put("arr", entry.getValue());
            resultDtos.add(node);
        }
        return resultDtos;
    }

    /*
     * lambda
     * 排序（写法一）
     * */
    @RequestMapping("/lbdStream04")
    @ResponseBody
    public Object acountsVI() {
        List<MyAcount> myAcountList = acountService.getAll();
        Collections.sort(myAcountList, new Comparator<MyAcount>() {
            @Override
            public int compare(MyAcount o1, MyAcount o2) {

                /*
                 * o1>o2正序
                 * o2>o1逆序
                 *
                 * */
                return o2.getAr_payamont().intValue() - o1.getAr_payamont().intValue();
            }
        });
        return myAcountList;
    }

    /*
     * lambda
     * 排序(排序方法目前未知)
     * */
    @RequestMapping("/lbdStream05")
    @ResponseBody
    public Object acountsVII() {
        List<MyAcount> myAcountList = acountService.getAll();
        Collections.sort(myAcountList, (o1, o2) -> {
            String key1 = o1.getAr_id().toString();
            String key2 = o2.getAr_id().toString();
            return key1.compareTo(key2);
        });
        return myAcountList;
    }

    /*
     * lambda
     * 金额大于10的有多少条
     * */
    @RequestMapping("/lbdStream06")
    @ResponseBody
    public Object acountsVIII() {
        List<MyAcount> myAcountList = acountService.getAll();
        int Size = myAcountList.stream().filter(s -> s.getAr_payamont().intValue() > 10).collect(Collectors.toList()).size();
        return Size;
    }


}
