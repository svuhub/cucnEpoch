package cucn.collect.common.domain.MyBatis.DTO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class MyAcount implements Serializable {
    private Integer ar_id;
    private BigDecimal ar_payamont;
    private Date ar_payTime;
    private String ar_payName;

}
