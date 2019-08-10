package cucn.collect.common.domain.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public interface ISalgrade {
    List<SalgradeMapper> getAllSalgradeMapper();

    SalgradeMapper selectByGrade(BigDecimal grade);

    int Insert(SalgradeMapper salgradeMapper);

    int update(SalgradeMapper salgradeMapper);

    int delete(BigDecimal grade);
}
