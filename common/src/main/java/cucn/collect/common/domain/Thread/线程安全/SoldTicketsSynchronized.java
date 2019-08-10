package cucn.collect.common.domain.Thread.线程安全;

/*
* 使用synchronized同步代码块解决一张票被卖多次的情况
*
* */
class soldWindowSyn implements Runnable {
    private int count = 100;
    /*
     * 定义一把对象锁
     * */
    private static Object oj = new Object();

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                // TODO: handle exception
            }
            sale();

        }


    }

    public void sale() {
        /*
         * 只锁可能异常的部分
         * */
        synchronized (oj) {
            if (count > 0) {
                System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
                count--;
            }

        }

    }

}


public class SoldTicketsSynchronized {

    public static void main(String[] args) {
        soldWindowSyn soldWindow = new soldWindowSyn();
        Thread t1 = new Thread(soldWindow);
        Thread t2 = new Thread(soldWindow);
        t1.start();
        t2.start();


    }
}