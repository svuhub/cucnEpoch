package cucn.collect.common.domain.MyBatis.mapper02;


import cucn.collect.common.domain.MyBatis.DTO.RoleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleInfoMapper {
    @Select("select * from role")
    List<RoleInfo> getAll();

    @Insert("INSERT INTO role( rid, rname) VALUES ( #{rid}, #{rname})")
    int insert(@Param("rid") int rid, @Param("rname") String rname);

}
