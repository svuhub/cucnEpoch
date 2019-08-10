package cucn.collect.common.domain.Thread.volitile;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.atomic.AtomicInteger;

class VolatitleM extends Thread {
 //   static int count = 0;
    private static AtomicInteger count=new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
          //  count++;
            count.incrementAndGet();
        }
        System.out.println(getName() + ".." + count);
    }
}


public class VolatileAutomic {
    public static void main(String[] args) {
        //创建多个线程
        VolatitleM[] volatitleMSList = new VolatitleM[10];
        for (int i = 0; i < volatitleMSList.length; i++) {
            volatitleMSList[i] = new VolatitleM();
        }
        for (VolatitleM volatitleM : volatitleMSList) {
            volatitleM.start();
        }

    }
}
