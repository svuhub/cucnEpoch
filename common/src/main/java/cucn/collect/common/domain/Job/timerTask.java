package cucn.collect.common.domain.Job;

import java.util.Timer;
import java.util.TimerTask;
/*
* timertask实现多线程
* */
public class timerTask {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("1");
            }
        };
      //配置Timer规则
        Timer timer =new Timer();
        long dayly=0;
        long second=1000;
        timer.scheduleAtFixedRate(timerTask,dayly,second);

    }
}
