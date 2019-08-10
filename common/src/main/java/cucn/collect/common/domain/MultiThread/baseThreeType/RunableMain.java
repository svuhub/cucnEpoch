package cucn.collect.common.domain.MultiThread.baseThreeType;



public class RunableMain {
    public static void main(String[] args) {
        ImplementsRunable implementsRunable = new ImplementsRunable();
        Thread thread = new Thread(implementsRunable);
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();

    }
}
