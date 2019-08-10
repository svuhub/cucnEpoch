package cucn.collect.common.domain.MyBatis.Service;


import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import cucn.collect.common.domain.MyBatis.DTO.RoleInfo;
import cucn.collect.common.domain.MyBatis.mapper02.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleInfoService {
    @Autowired
    private RoleInfoMapper roleInfoMapper;
   /* @Autowired
    private AcountMapper acountMapper;
*/
    public List<RoleInfo> getAll(){
        return roleInfoMapper.getAll();
    }

    /*
    * 测试多数据源
    * */
    @Transactional()
    public Integer insertUser(RoleInfo roleInfo, MyAcount acount) {
        int a=roleInfoMapper.insert(roleInfo.getRid(),roleInfo.getRname());
      //  int b=acountMapper.insert(acount.getAr_payamont(),acount.getAr_payTime(),acount.getAr_payName());
        int c=a/0;
        return a;
    }


}
