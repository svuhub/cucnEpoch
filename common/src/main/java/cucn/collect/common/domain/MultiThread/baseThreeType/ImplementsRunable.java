package cucn.collect.common.domain.MultiThread.baseThreeType;

 class ImplementsRunable implements Runnable{
     @Override
     public void run() {
         for (int i = 0; i <= 50; i++) {
             System.out.println("子"+i);
         }
     }
 }
