package cucn.collect.common.domain.MyBatis.Service;

import cucn.collect.common.domain.MyBatis.DTO.MyAcount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AcountServiceTest {
    @Autowired
    AcountService acountService;

    @Test
    public void getAll() {
        System.out.println(acountService.getAll());
    }
}