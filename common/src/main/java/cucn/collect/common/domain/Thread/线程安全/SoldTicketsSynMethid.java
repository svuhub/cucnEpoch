package cucn.collect.common.domain.Thread.线程安全;

/*
 *
 * 使用同步函数解决一张票卖多次问题
 * */
class soldWindowSynM implements Runnable {
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

    /*
     * 直接用关键字修饰该方法即可
     *
     * */
    public synchronized void sale() {
        if (count > 0) {
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
            count--;
        }

    }

}


public class SoldTicketsSynMethid {

    public static void main(String[] args) {
        soldWindowSynM soldWindow = new soldWindowSynM();
        Thread t1 = new Thread(soldWindow, "1号口");
        Thread t2 = new Thread(soldWindow, "2号口");
        t1.start();
        t2.start();


    }
}