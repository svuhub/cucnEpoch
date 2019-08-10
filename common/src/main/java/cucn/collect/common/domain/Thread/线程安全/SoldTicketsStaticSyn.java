package cucn.collect.common.domain.Thread.线程安全;

/*
 * 使用静态同步函数
 * */
class soldWindowStatic implements Runnable {
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
        synchronized (soldWindowStatic.class) {
            if(count>0){
                System.out.println(Thread.currentThread().getName() + ",出售 第" + (100 - count + 1) + "张票.");
            }
            count--;

        }

    }

}


public class SoldTicketsStaticSyn {

    public static void main(String[] args) {
        soldWindowStatic soldWindow = new soldWindowStatic();
        Thread t1 = new Thread(soldWindow, "1号口");
        Thread t2 = new Thread(soldWindow, "2号口");
        t1.start();
        t2.start();


    }
}