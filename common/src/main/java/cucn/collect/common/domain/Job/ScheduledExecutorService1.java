package cucn.collect.common.domain.Job;

/*
* 最理想的实现方式
* */
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorService1 {
    public static void main(String[] args) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        };
        java.util.concurrent.ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,1, 1, TimeUnit.SECONDS);
    }
}
