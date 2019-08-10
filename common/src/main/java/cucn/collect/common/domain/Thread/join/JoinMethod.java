package cucn.collect.common.domain.Thread.join;

public class JoinMethod {

    /*
    * join作用是让其他线程变为等待, 	t1.join();// 让其他线程变为等待，直到当前t1线程执行完毕，才释放。
    *
    * */
    public static void main(String[] args) {
        /*
        * 子线程
        * */
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=100;i++){
                    System.out.println("t1"+i);
                }
            }
        });
        t1.start();
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<=100;i++){
                    System.out.println("t2"+i);
                }
            }
        });
        t2.start();
        Thread t3=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<=100;i++){
                    System.out.println("t3"+i);
                }
            }
        });
        t3.start();
    }
}
