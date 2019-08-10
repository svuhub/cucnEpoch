package cucn.collect.common.Controller;

import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import cucn.collect.common.domain.MyBatis.Service.AcountService;
import cucn.collect.common.domain.POIExcel.ExcelData;
import cucn.collect.common.domain.POIExcel.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("excel")
public class ExcelController {
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
    @Autowired
    private AcountService acountService;

    /*    @Resource
        private UserInfoService userInfoService;

        @RequestMapping("/test")
        public  RetResult<Integer> test(){
            int rowIndex = 0;
            List<UserInfo> list = userInfoService.selectAlla(0, 0);
            ExcelData data = new ExcelData();
            data.setName("hello");
            List<String> titles = new ArrayList();
            titles.add("ID");
            titles.add("userName");
            titles.add("password");
            data.setTitles(titles);

            List<List<Object>> rows = new ArrayList();
            for(int i = 0, length = list.size();i<length;i++){
                UserInfo userInfo = list.get(i);
                List<Object> row = new ArrayList();
                row.add(userInfo.getId());
                row.add(userInfo.getUserName());
                row.add(userInfo.getPassword());
                rows.add(row);
            }
            data.setRows(rows);
            try{
                rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
            }catch (Exception e){
                e.printStackTrace();
            }
            return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
        }
     */
    @RequestMapping("/test2")
    public void test2(HttpServletResponse response) {
        List<MyAcount> list = acountService.getAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("支付序号");
        titles.add("支付金额");
        titles.add("支付时间");
        titles.add("支付类型");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for (int i = 0, length = list.size(); i < length; i++) {
            MyAcount myAcount = list.get(i);
            List<Object> row = new ArrayList();
            row.add(myAcount.getAr_id());
            row.add(myAcount.getAr_payamont());
            row.add(myAcount.getAr_payTime());
            row.add(myAcount.getAr_payName());
            rows.add(row);
        }
        data.setRows(rows);
        try {
            ExcelUtils.exportExcel(response, "userInfo", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/test3")
    public void test3(HttpServletResponse response) {
        List<MyAcount> list = acountService.getAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("支付序号");
        titles.add("支付金额");
        titles.add("支付时间");
        titles.add("支付类型");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for (int i = 0, length = list.size(); i < length; i++) {
            MyAcount myAcount = list.get(i);
            List<Object> row = new ArrayList();
            row.add(myAcount.getAr_id());
            row.add(myAcount.getAr_payamont());
            row.add(myAcount.getAr_payTime());
            row.add(myAcount.getAr_payName());
            rows.add(row);
        }
        data.setRows(rows);
        try {
            ExcelUtils.exportExcel(response, "userInfo", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/test4")
    public void test4(HttpServletResponse response) {
        List<MyAcount> list = new ArrayList<>();
        for (int i = 0; i <= 100000; i++) {
            MyAcount acount = new MyAcount();
            acount.setAr_id(i);
            list.add(acount);
        }

        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("支付序号");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for (int i = 0, length = list.size(); i < length; i++) {
            MyAcount myAcount = list.get(i);
            List<Object> row = new ArrayList();
            row.add(myAcount.getAr_id());
            rows.add(row);
        }
        data.setRows(rows);
        try {
            logger.info("reponse:"+response.toString());
            ExcelUtils.exportExcel(response, "userInfo", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}