package cucn.collect.common.domain.Thread.Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * 通过满参构造给私有属性复制
 * */
public class CreateReflectFullargs {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> forName = Class.forName("cucn.collect.common.domain.Thread.Reflect.User");
        Constructor<?> constructor = forName.getConstructor(String.class, String.class);
        User user=(User) constructor.newInstance("tom","18");
        System.out.println(user);

    }
}
