package cucn.collect.common.CommonParts.tuple;

/**
 * 元组，3个属性
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class TupleThree<A, B, C> extends TupleTwo<A, B> {

    private final C third;

    public TupleThree(A a, B b, C c) {
        super(a, b);
        this.third = c;
    }

    public C getThird() {
        return this.third;
    }
}
