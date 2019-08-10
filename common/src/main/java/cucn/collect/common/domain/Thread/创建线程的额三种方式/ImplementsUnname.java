package cucn.collect.common.domain.Thread.创建线程的额三种方式;

/*
 * 三、匿名内部类方式
 * */
public class ImplementsUnname {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("i:" + i);
                }

            }
        });
        thread.start();
    }


}
