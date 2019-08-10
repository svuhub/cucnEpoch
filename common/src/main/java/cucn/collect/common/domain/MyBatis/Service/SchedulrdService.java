package cucn.collect.common.domain.MyBatis.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulrdService {
    @Scheduled(cron = "*/50000000 * * * * ?")
    public String TestPrint() {
        System.out.println("1");
        return "Scheduled";

    }

}
