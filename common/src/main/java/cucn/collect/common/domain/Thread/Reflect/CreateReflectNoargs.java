package cucn.collect.common.domain.Thread.Reflect;

/*
* newInstance方法通过无参构造反射给private属性复制
*
* */
public class CreateReflectNoargs {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
            Class<?> forName= Class.forName("cucn.collect.common.domain.Thread.Reflect.User");
        //通过无参构造给对象复制
        Object newInstance = forName.newInstance();
    }
}
