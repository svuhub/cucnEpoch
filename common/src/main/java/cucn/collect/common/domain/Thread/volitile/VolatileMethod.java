package cucn.collect.common.domain.Thread.volitile;

/*
 *volatile是保证变量在多个线程之间可见
 * 原因:线程之间是不可见的，读取的是副本，没有及时读取到主内存结果。
解决办法使用Volatile关键字将解决线程之间可见性, 强制线程每次读取该值的时候都去“主内存”中取值

 * */
class volatileMt implements Runnable {
    volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("开始执行子线程....");
        while (flag) {
        }
        System.out.println("线程停止");

    }

    void turnFlag(boolean flag) {
        this.flag = false;
    }

}


public class VolatileMethod {
    public static void main(String[] args) {
        volatileMt volatileMt = new volatileMt();
        Thread thread = new Thread(volatileMt);
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        volatileMt.turnFlag(false);


    }
}
