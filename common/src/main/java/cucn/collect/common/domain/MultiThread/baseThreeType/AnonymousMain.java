package cucn.collect.common.domain.MultiThread.baseThreeType;

public class AnonymousMain {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 50; i++) {
                    System.out.println("子"+i);
                }
            }
        }).start();
    }
}
