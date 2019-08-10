package cucn.collect.common.domain.Thread.SendMail;

import java.util.ArrayList;
import java.util.List;


class UserSendThread implements Runnable {
    public List<UserEntity> list;

    public UserSendThread(List<UserEntity> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (UserEntity u : list) {
            System.out.println(Thread.currentThread().getName() + "|" + u.toString());
        }

    }
}

public class SendAction {
    public static void main(String[] args) {

        /*
         * 发送步骤：
         * 1.初始化数据
         *
         * 2.定义每个线程分批发送的大小
         * 3.计算每个线程需要分批跑的数据
         * 4.分批发送
         *
         * */

        /*
         * 1.初始化数据
         * */
        List<UserEntity> list = initUser();
        /*
         *  2.定义每个线程分批发送的大小
         * */
        int userCount = 2;


        /*
         * 3.计算每个线程需要分批跑的数据
         *
         * */
        List<List<UserEntity>> splitUserList = ListUtils.splitList(list, userCount);
        int threadSaze = splitUserList.size();
        for (int i = 0; i < threadSaze; i++) {
            List<UserEntity> lists = splitUserList.get(i);
      /*      for (UserEntity u : lists) {

            }*/

            UserSendThread userSendThread = new UserSendThread(lists);
            Thread thread=new Thread(userSendThread,"线程"+i);
            thread.start();

        }


    }


    /*
     * 初始化数据
     * */
    static List<UserEntity> initUser() {
        List<UserEntity> list = new ArrayList<UserEntity>();
        for (int i = 0; i < 10; i++) {
            list.add(new UserEntity("userid" + i, "username" + i));
        }
        return list;
    }


}
