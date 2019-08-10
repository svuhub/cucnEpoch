package cucn.collect.common.domain.Thread.守护线程;

/*
*
* Java中有两种线程，一种是用户线程，另一种是守护线程。
 用户线程是指用户自定义创建的线程，主线程停止，用户线程不会停止
守护线程当进程不存在或主线程停止，守护线程也会被停止

*
* */
public class ProtectedThred {
    public static void main(String[] args) {
        /*
         * 创建一个子线程
         * */
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 5; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("子线程在执行" + i);
                }
            }
        });


        t1.setDaemon(true);//设置守护线程 添加 主线程死亡 子线程不会执行，在Start方法前调用
        t1.start();

        /*
         * 这里是主线程
         * */
        for (int i = 0; i <= 5; i++) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("主线程在执行" + i);
        }

    }
}
