package me.lukas81298.mathscript.structures.matrix;

import me.lukas81298.mathscript.util.IntTuple;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author lukas
 * @since 08.06.2018
 */
@SuppressWarnings("ALL")
public class Matrix<T> {

    public static Matrix<Integer> identityI(int size) {
        return identity(size, 0, 1);
    }

    public static Matrix<Double> identityD(int size) {
        return identity(size, 0D, 1D);
    }

    public static Matrix<Float> identityF(int size) {
        return identity(size, 0F, 1F);
    }

    public static Matrix<Long> identityL(int size) {
        return identity(size, 0L, 1L);
    }

    public static Matrix<Short> identityS(int size) {
        return identity(size, (short) 0, (short) 1);
    }

    public static <K> Matrix<K> identity(int size, K zero, K one) {
        Matrix<K> matrix = new Matrix<K>(size, size, zero);
        for(int i = 0; i < size; i++) {
            matrix.set(i, i, one);
        }
        return matrix;
    }

    private final int rows, columns;
    private T[][] data;
    private final Ring<T> ring;

    public Matrix(int rows, int columns, T defaultValue) {
        this(rows, columns, defaultValue, Ring.findRing(defaultValue));
    }

    public Matrix(int rows, int columns, T defaultValue, Ring ring) {
        this.ring = ring;
        this.rows = rows;
        this.columns = columns;
        this.data = (T[][]) new Object[rows][];
        for(int i = 0; i < this.data.length; i++) {
            this.data[i] = (T[]) new Object[columns];
            Arrays.fill(this.data[i], defaultValue);
        }
    }

    private Matrix(int rows, int columns, Ring ring, T[][] data) {
        this.rows = rows;
        this.columns = columns;
        this.ring = ring;
        this.data = data;
    }

    public boolean hasSameSize(Matrix<T> t) {
        return rows == t.rows && columns == t.columns;
    }

    public int rows() {
        return this.rows;
    }

    public int cols() {
        return this.columns;
    }


    public void set(int row, int column, T value) {
        this.data[row][column] = value;
    }

    public T get(int row, int column) {
        return this.data[row][column];
    }

    public Matrix<T> addClone(Matrix<T> m) {
        return this.clone().addClone(m);
    }

    public Matrix<T> add(Matrix<T> m) {
        if(m.rows != rows || m.columns != columns) {
            throw new IllegalArgumentException("Could not add a " + rows + "x" + columns + " matrix and a " + m.rows + "x" + m.columns + " matrix");
        }
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                set(i, j, ring.add(get(i, j), m.get(i, j)));
            }
        }
        return this;
    }

    public Matrix<T> multiplyClone(Matrix<T> m) {
        return this.clone().multiply(m);
    }

    public Matrix<T> multiply(Matrix<T> m) {
        if(columns != m.rows) {
            throw new IllegalArgumentException("Cannot multiply a " + rows + "x" + columns + " matrix with a " + m.rows + "x" + m.columns + " matrix");
        }
        Matrix<T> out = new Matrix<>(this.rows, m.columns, ring.zero(), ring);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < m.columns; j++) {
                T val = this.ring.zero();
                for(int k = 0; k < this.columns; k++) {
                    val = this.ring.add(val, this.ring.multiply(get(i, k), m.get(k, j)));
                }
                out.set(i, j, val);
            }
        }
        return out;
    }

    public Matrix<T> transpose() {
        Matrix<T> matrix = new Matrix<>(columns, rows, ring.zero(), ring);
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                matrix.set(i, j, get(j, i));
            }
        }
        return matrix;
    }

    public Matrix<T> pow(int i) {
        if(i <= 1) {
            return this;
        }
        return this.multiply(this).pow(i - 1);
    }

    public Matrix<T> clone() {
        return new Matrix<>(rows, columns, ring, data);
    }

    public T[] getRow(int i) {
        return this.data[i];
    }

    public T[] getColumn(int i) {
        T[] d = (T[]) new Object[columns];
        for(int j = 0; j < this.rows; j++) {
            d[j] = this.data[j][i];
        }
        return d;
    }

    public Matrix<T> reduce() {
        for(int i = 0; i < this.rows - 1; i++) {
            for(int k = i + 1; k < this.rows; k++) {
                this.set(k, i, this.ring.div(this.get(k, i), this.get(i, i)));
                for(int j = i + 1; j < this.rows; j++) {
                    this.set(k, j, this.ring.sub(this.get(k, j), this.ring.multiply(this.get(k, i), this.get(i, j))));
                }
            }
        }
        return this;
    }

    private IntTuple min( int i, int j, Function<Integer, Integer> function) {
        int min = 0;
        int key = 0;
        for(int k = i; k < j; k++) {
            int val = function.apply(k);
            if(val > min) {
                min = val;
                key = k;
            }
        }
        return new IntTuple(min, key);
    }

    public Matrix<T> rowEchelonForm() {
        for(int i = 0; i < rows; i++) {
            IntTuple tuple = min(0, rows, k -> ech(k));
            int l = tuple.a;
            int k;
            if(l < columns) {
                k = tuple.b;
            } else {
                return this;
            }
            this.swap(i, k);
            for(int h = i + 1; h < rows; h++) {
                this.add(h, i, ring.sub(ring.zero(), get(h, l)));
            }
        }
        return this;
    }

    private boolean isZeroRow(int i) {
        for(T t : this.data[i]) {
            if(!t.equals(ring.zero())) {
                return false;
            }
        }
        return true;
    }

    private int ech(int i) {
        if(this.isZeroRow(i)) {
            return this.columns + i;
        }
        T max = null;
        int maxIndex = 0;
        int j = 0;
        for(T t : this.data[i]) {
            if(max == null || this.ring.compare(t, max) > 0) {
                max = t;
                maxIndex = j;
            }
            j++;
        }
        return maxIndex;
    }

    public Matrix<T> mul(T factor) {
        for ( int i = 0; i < this.data.length; i++ ) {
            for ( int j = 0; j < this.data[i].length; j++ ) {
                this.data[i][j] = this.ring.multiply( factor, this.data[i][j] );
            }
        }
        return this;
    }

    public Matrix<T> add(int target, int source, T factor) {
        for(int i = 0; i < this.data[source].length; i++) {
            this.data[target][i] = ring.add(this.data[target][i], ring.multiply(this.data[source][i], factor));
        }
        return this;
    }

    public Matrix<T> mul(int row, T t) {
        for(int i = 0; i < this.data[row].length; i++) {
            this.data[row][i] = this.ring.multiply(t, this.data[row][i]);
        }
        return this;
    }

    public Matrix<T> swap(int row1, int row2) {
        T[] temp = this.data[row1];
        this.data[row1] = this.data[row2];
        this.data[row2] = temp;
        return this;
    }

    public int rank() {
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < this.rows; i++) {
            T[] row = this.data[i];
            for(int i1 = 0; i1 < row.length; i1++) {
                if(i1 > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(row[i1]);
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

}
