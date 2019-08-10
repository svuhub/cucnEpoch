package cucn.collect.common.domain.MyBatis.mapper;

import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface AcountMapper {
    /*
     * 根据id查询
     * */
    @Select("select * from acountrecord where ar_id=#{ar_id}")
    MyAcount findById(@Param("ar_id") Integer id);

    /*
     * 查询所有
     * */
    @Select("select * from acountrecord")
    List<MyAcount> getAll();

    /*
     * 插入
     * */
    @Insert("INSERT INTO myacount.acountrecord( ar_payamont, ar_payTime, ar_payName) VALUES ( #{ar_payamont}, #{ar_payTime}, #{ar_payName})")
    int insert(@Param("ar_payamont") BigDecimal ar_payamont, @Param("ar_payTime") Date ar_payTime, @Param("ar_payName") String ar_payName);

    /*
     * 根据消费账目查询
     * */
    @Select("select * from acountrecord where ar_payName=#{ar_payName}")
    List<MyAcount> getInfoByName(@Param("ar_payName") String name);
}
