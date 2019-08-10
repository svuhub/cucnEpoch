package cucn.collect.common.domain.Thread.创建线程的额三种方式;

/*
 * 一、继承Thread
 * */
public class ExtendsThread {
    public static void main(String[] args) {
        CreateThread createThread = new CreateThread();
        System.out.println("-----多线程创建启动-----");
        createThread.start();
        System.out.println("-----多线程创建结束-----");

    }
}
class CreateThread extends Thread {
    public void run() {
        for (int i = 0; i <= 1000; i++) {
        }
    }
}
