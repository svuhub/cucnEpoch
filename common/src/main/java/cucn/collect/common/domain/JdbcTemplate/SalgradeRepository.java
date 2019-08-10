package cucn.collect.common.domain.JdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalgradeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //JDBCtemplate 查询集合
    public List<SalgradeMapper> getAllSalgradeMapper() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM `salgrade`;");
        List<SalgradeMapper> salgradeList = new ArrayList<>();
        while (sqlRowSet.next()) {
            salgradeList.add(new SalgradeMapper(
                    sqlRowSet.getBigDecimal("grade"),
                    sqlRowSet.getBigDecimal("losal"),
                    sqlRowSet.getBigDecimal("hisal")
            ));

        }
        return salgradeList;
    }

    //JDBCtemplate查询单个
    public SalgradeMapper selectByGrade(BigDecimal grade) {
        return jdbcTemplate.queryForObject("SELECT * FROM `salgrade` where grade=?", new RowMapper<SalgradeMapper>() {
            @Override
            public SalgradeMapper mapRow(ResultSet sqlRowSet, int rowNum) throws SQLException {
                return new SalgradeMapper(
                        sqlRowSet.getBigDecimal("grade"),
                        sqlRowSet.getBigDecimal("losal"),
                        sqlRowSet.getBigDecimal("hisal"));
            }
        }, grade);
    }

    //JDBCtemplate新增一条数据
    public int Insert(SalgradeMapper salgradeMapper) {
        String sql = "insert into salgrade(grade,losal,hisal) values(?,?,?)";
        return jdbcTemplate.update(sql, salgradeMapper.getGrade(), salgradeMapper.getHisal(), salgradeMapper.getLosal());

    }

    //JDBCtemplate更新一条数据
    public int update(SalgradeMapper salgradeMapper) {
        String sql = "update salgrade set losal=?,hisal=? where grade=?";
        return jdbcTemplate.update(sql, salgradeMapper.getHisal(), salgradeMapper.getLosal(), salgradeMapper.getGrade());

    }

    //JDBCtemplate删除一条数据
    public int delete(BigDecimal grade) {
        String sql = "delete from salgrade where grade=?";
        return jdbcTemplate.update(sql, grade);

    }
}
