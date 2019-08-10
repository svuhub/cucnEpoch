package cucn.collect.common.domain.Thread.SemaphoreDemo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/*
* Semaphore是一种基于计数的信号量。它可以设定一个阈值，基于此，多个线程竞争获取许可信号，做自己的申请后归还，
* 超过阈值后，线程申请许可信号将会被阻塞。
*availablePermits函数用来获取当前可用的资源数量
wc.acquire(); //申请资源
wc.release();// 释放资源

* */
class Parent implements Runnable {
    private String name;
    private Semaphore wc;
    public Parent(String name,Semaphore wc){
        this.name=name;
        this.wc=wc;
    }

    @Override
    public void run() {
        try {
            // 剩下的资源(剩下的茅坑)
            int availablePermits = wc.availablePermits();
            if (availablePermits > 0) {
                System.out.println(name+"天助我也,终于有茅坑了...");
            } else {
                System.out.println(name+"怎么没有茅坑了...");
            }
            //申请茅坑 如果资源达到3次，就等待
            wc.acquire();
            System.out.println(name+"终于轮我上厕所了..爽啊");
            Thread.sleep(new Random().nextInt(1000)); // 模拟上厕所时间。
            System.out.println(name+"厕所上完了...");
            wc.release();

        } catch (Exception e) {

        }


    }
}


public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <=10; i++) {
            Parent parent = new Parent("第"+i+"个人,",semaphore);
            new Thread(parent).start();
        }

    }
}
