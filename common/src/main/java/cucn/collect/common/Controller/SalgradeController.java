package cucn.collect.common.Controller;


import cucn.collect.common.CommonParts.Result;
import cucn.collect.common.domain.JdbcTemplate.SalgradeMapper;
import cucn.collect.common.domain.JdbcTemplate.SalgradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/type")
public class SalgradeController {
    @Autowired
    private SalgradeRepository salgradeRepository;

    @RequestMapping("/type1")
    @ResponseBody
    public Result getAllSalgrade() {
        Map map = new HashMap();
        List<SalgradeMapper> lists = salgradeRepository.getAllSalgradeMapper();
        map.put("lists", lists);

        return Result.successData(map);
    }


    @RequestMapping("/type2")
    @ResponseBody
    public Result getAllSalgradeByGrade() {
        Map map = new HashMap();
        SalgradeMapper salgradeMapper = salgradeRepository.selectByGrade(new BigDecimal(1));
        System.out.println(salgradeMapper.toString());
        map.put("salgradeMapper", salgradeMapper);
        return Result.successData(map);
    }

    @RequestMapping("/type3")
    @ResponseBody
    public void Insert() {
        SalgradeMapper salgradeMapper = new SalgradeMapper();
        salgradeMapper.setGrade(new BigDecimal(7));
        salgradeMapper.setHisal(new BigDecimal(6));
        salgradeMapper.setLosal(new BigDecimal(6));
        salgradeRepository.Insert(salgradeMapper);
    }

    @RequestMapping("/type4")
    @ResponseBody
    public void update() {
        SalgradeMapper salgradeMapper = new SalgradeMapper();
        salgradeMapper.setGrade(new BigDecimal(7));
        salgradeMapper.setHisal(new BigDecimal(7));
        salgradeMapper.setLosal(new BigDecimal(7));
        salgradeRepository.update(salgradeMapper);
    }

    @RequestMapping("/type5")
    @ResponseBody
    public void delete() {
        salgradeRepository.delete(new BigDecimal(7));
    }


}
