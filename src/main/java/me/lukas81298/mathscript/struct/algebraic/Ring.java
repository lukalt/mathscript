package me.lukas81298.mathscript.struct.algebraic;

/**
 * @author lukas
 * @since 08.06.2018
 */
public interface Ring<T> {

    IntRing INT_RING = new IntRing();
    DoubleRing DOUBLE_RING = new DoubleRing();
    FloatRing FLOAT_RING = new FloatRing();
    LongRing LONG_RING = new LongRing();

    T add( T a1, T a2 );

    T multiply( T a1, T a2 );

    T zero();

    T one();

    default int compare( T a1, T a2 ) {
        throw new IllegalArgumentException();
    }

    default T sub( T a1, T a2 ) {
        throw new IllegalArgumentException("Sub not possible");
    }

    default T div( T a1, T a2 ) {
        throw new IllegalArgumentException("Cannot divide");
    }

    final class IntRing implements Ring<Integer> {

        @Override
        public Integer add(Integer a1, Integer a2) {
            return a1 + a2;
        }

        @Override
        public Integer multiply(Integer a1, Integer a2) {
            return a1 * a2;
        }

        @Override
        public Integer zero() {
            return 0;
        }

        @Override
        public Integer one() {
            return 1;
        }
    }

    final class DoubleRing implements Ring<Double> {

        @Override
        public Double add(Double a1, Double a2) {
            return a1 + a2;
        }

        @Override
        public Double multiply(Double a1, Double a2) {
            return a1 * a2;
        }

        @Override
        public Double zero() {
            return 0D;
        }

        @Override
        public Double one() {
            return 1D;
        }

        @Override
        public Double div(Double a1, Double a2) {
            return a1 / a2;
        }

        @Override
        public Double sub(Double a1, Double a2) {
            return a1 - a2;
        }

        @Override
        public int compare(Double a1, Double a2) {
            return Double.compare(a1, a2);
        }
    }

    final class LongRing implements Ring<Long> {

        @Override
        public Long add(Long a1, Long a2) {
            return a1 + a2;
        }

        @Override
        public Long multiply(Long a1, Long a2) {
            return a1 * a2;
        }

        @Override
        public Long zero() {
            return 0L;
        }

        @Override
        public Long one() {
            return 1L;
        }
    }

    final class FloatRing implements Ring<Float> {

        @Override
        public Float add(Float a1, Float a2) {
            return a1 + a2;
        }

        @Override
        public Float multiply(Float a1, Float a2) {
            return a1 * a2;
        }

        @Override
        public Float zero() {
            return 0F;
        }

        @Override
        public Float one() {
            return 1F;
        }

        @Override
        public Float div(Float a1, Float a2) {
            return a1 / a2;
        }
    }

    static <T> Ring<T> findRing( T t ) {
        if(t instanceof Long) {
            return (Ring<T>) LONG_RING;
        }
        if(t instanceof Double) {
            return (Ring<T>) DOUBLE_RING;
        }
        if(t instanceof Float) {
            return (Ring<T>) FLOAT_RING;
        }
        if(t instanceof Integer) {
            return (Ring<T>) INT_RING;
        }
        return null;
    }
}
