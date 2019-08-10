package cucn.collect.common.CommonParts.tuple;

/**
 * 元组，2个属性
 * @param <A>
 * @param <B>
 */
public class TupleTwo<A, B> {

    private final A first;
    private final B second;

    public TupleTwo(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public A getFirst() {
        return this.first;
    }

    public B getSecond() {
        return this.second;
    }
}