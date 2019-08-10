package cucn.collect.common.domain.MyBatis.mapper03;


import cucn.collect.common.domain.MyBatis.DTO.UserExtroInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserExtroInfoMapper {
    /*
     * 查询所有
     * */

    @Select("SELECT * FROM `wx_user_extra_info` ")
    List<UserExtroInfo> getAllUser();

    @Select("SELECT * FROM `wx_user_extra_info` where LENGTH(uei_partyid)<1")
    List<UserExtroInfo> getAll();

    @Insert("update wx_user_extra_info set uei_name=#{uei_name},uei_pin=#{uei_pin},uei_partyid=#{uei_partyid} where uei_bind_identity=#{uei_bind_identity}")
    int insert(@Param("uei_name") String name, @Param("uei_pin") String pin, @Param("uei_partyid") String paytyId, @Param("uei_bind_identity") String mobile);






}
