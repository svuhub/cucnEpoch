package cucn.collect.common.domain.MyBatis.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import cucn.collect.common.domain.MyBatis.mapper.AcountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AcountService {
    @Autowired
    private AcountMapper acountMapper;

    public Integer insertUser(MyAcount myAcount) {
        return acountMapper.insert(myAcount.getAr_payamont(), new Date(), myAcount.getAr_payName());
    }

    public MyAcount selectById(int id) {

        return acountMapper.findById(id);
    }

    public List<MyAcount> getAll() {

        return acountMapper.getAll();
    }

    public List<MyAcount> getInfoByName(String name) {

        return acountMapper.getInfoByName(name);
    }

    /*
     * pagehelper的分页
     * */
    public PageInfo<MyAcount> selectAllByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<MyAcount> myAcountList = acountMapper.getAll();
        PageInfo<MyAcount> pageInfoMyacount = new PageInfo<MyAcount>(myAcountList);
        return pageInfoMyacount;
    }

}
