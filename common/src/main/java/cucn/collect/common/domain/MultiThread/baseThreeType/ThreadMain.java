package cucn.collect.common.domain.MultiThread.baseThreeType;


import cucn.collect.common.domain.MultiThread.baseThreeType.CreateThread;

public class ThreadMain {
    public static void main(String[] args) {
        CreateThread createThread = new CreateThread();
        createThread.start();
        try {

            createThread.sleep(1000); //TODO:休眠
            createThread.getId();           //获取线程ID
            createThread.setName("");       //设置线程名称
            createThread.getName();           //获取线程名称
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <= 50; i++) {
            System.out.println("主"+i);//TODO:结果先开始执行主线程，再开始执行子线程，启动线程调用start方法。run()方法相当于正常调用
                                        //TODO:使用多线程程序是并行的
        }
    }


}
