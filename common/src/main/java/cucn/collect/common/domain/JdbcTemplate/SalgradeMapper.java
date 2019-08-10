package cucn.collect.common.domain.JdbcTemplate;

import java.math.BigDecimal;

public class SalgradeMapper {
    private BigDecimal grade;
    private BigDecimal losal;
    private BigDecimal hisal;

    public SalgradeMapper() {
    }

    public SalgradeMapper(BigDecimal grade, BigDecimal losal, BigDecimal hisal) {
        this.grade = grade;
        this.losal = losal;
        this.hisal = hisal;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public BigDecimal getLosal() {
        return losal;
    }

    public void setLosal(BigDecimal losal) {
        this.losal = losal;
    }

    public BigDecimal getHisal() {
        return hisal;
    }

    public void setHisal(BigDecimal hisal) {
        this.hisal = hisal;
    }

    @Override
    public String toString() {
        return "SalgradeMapper{" +
                "grade=" + grade +
                ", losal=" + losal +
                ", hisal=" + hisal +
                '}';
    }
}
