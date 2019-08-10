package cucn.collect.common.domain.MultiThread.soldTickets;
//同步代码块的第二种写法

class synchronizedSoldII implements Runnable {
    private static int TICKETCOUNT = 100;
    private Object object = new Object();

    @Override
    public void run() {
        while (TICKETCOUNT > 0) {
            SOLD();


        }
    }

    private synchronized void SOLD() {
        if (TICKETCOUNT > 0) {
            System.out.println(Thread.currentThread().getName() + "售出了第" + (100 - TICKETCOUNT + 1) + "张票");
            TICKETCOUNT--;
        }
    }
}


public class synchronizedSoldTicketII {
    public static void main(String[] args) {
        //要工公用一个线程类实例
        soldTickets soldTickets = new soldTickets();
        //创建线程类
        Thread thread1 = new Thread(soldTickets);
        Thread thread2 = new Thread(soldTickets);
        thread1.start();
        thread2.start();//TODO:问题，出现了第101张票

    }
}
