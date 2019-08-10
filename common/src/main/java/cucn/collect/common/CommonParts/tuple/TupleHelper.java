package cucn.collect.common.CommonParts.tuple;

/**
 * 元组帮助类
 */
public final class TupleHelper {

    private TupleHelper() {
    }

    public static <A, B> TupleTwo<A, B> tuple(A a, B b) {
        return new TupleTwo(a, b);
    }

    public static <A, B, C> TupleThree<A, B, C> tuple(A a, B b, C c) {
        return new TupleThree(a, b, c);
    }

    public static <A, B, C, D> TupleFour<A, B, C, D> tuple(A a, B b, C c, D d) {
        return new TupleFour(a, b, c, d);
    }
}


