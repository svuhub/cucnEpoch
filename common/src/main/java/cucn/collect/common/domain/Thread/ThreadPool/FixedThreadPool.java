package cucn.collect.common.domain.Thread.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
* 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
*
* */
public class FixedThreadPool {
    public static void main(String[] args) {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final  int temp=i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + ",i:" + temp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }
}
