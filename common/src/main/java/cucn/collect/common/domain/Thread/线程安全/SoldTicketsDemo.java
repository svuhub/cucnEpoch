package cucn.collect.common.domain.Thread.线程安全;

/*
 * 开两个线程卖100张票。模拟可能出现的问题
 *结果出现了卖101张票的而情况
 * */
class soldWindow implements Runnable {
    private int count = 100;

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
        if (count > 0) {
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
            count--;
        }

    }

}


public class SoldTicketsDemo {

    public static void main(String[] args) {
        soldWindow soldWindow = new soldWindow();
        Thread t1 = new Thread(soldWindow,"1号口");
        Thread t2 = new Thread(soldWindow,"2号口");
        t1.start();
        t2.start();


    }
}
