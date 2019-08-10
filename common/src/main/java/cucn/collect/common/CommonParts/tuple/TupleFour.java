package cucn.collect.common.CommonParts.tuple;

/**
 * 元组，4个属性
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class TupleFour<A, B, C, D> extends TupleThree<A, B, C> {

    private final D fourth;

    public TupleFour(A a, B b, C c, D d) {
        super(a, b, c);
        this.fourth = d;
    }

    public D getFourth() {
        return this.fourth;
    }
}
