package cucn.collect.common.domain.Thread.创建线程的额三种方式;
/*
* 无线程
* 6978ms
* */
public class Normal {
    public static void main(String[] args) {
        long a=System.currentTimeMillis();
        for(int i=0;i<=1000000;i++){
            System.out.println("i:" + i);
        }
        long b=System.currentTimeMillis();
        System.out.println(b-a);
    }
}
