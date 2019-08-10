package cucn.collect.common.domain.MultiThread.baseThreeType;

public class CreateThread extends Thread {
    /*
     * 里面存放需要多线程执行的方法体
     * */
    public void run() {
        //如打印50
        for (int i = 0; i <= 50; i++) {
            System.out.println("子"+i);
        }
    }
}
