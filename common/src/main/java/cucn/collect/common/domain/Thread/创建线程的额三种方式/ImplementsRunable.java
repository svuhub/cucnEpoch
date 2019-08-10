package cucn.collect.common.domain.Thread.创建线程的额三种方式;
/*
* 二、实现Runable
* */
public class ImplementsRunable {
    public static void main(String[] args) {
        impleMethos impleMethos=new impleMethos();
        Thread thread=new Thread(impleMethos);
            thread.start();
    }
}

class impleMethos implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i< 10; i++) {
            System.out.println("i:" + i);
        }

    }
}
