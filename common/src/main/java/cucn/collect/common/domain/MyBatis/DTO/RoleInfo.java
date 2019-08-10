package cucn.collect.common.domain.MyBatis.DTO;

import lombok.Data;
@Data
public class RoleInfo {
    @RoleMapping("ID")
    private int rid;
    @RoleMapping("名称")
    private String rname;

}
