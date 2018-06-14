package me.lukas81298.mathscript.struct.matrix;

import me.lukas81298.mathscript.struct.algebraic.Ring;

import java.util.function.Function;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class MatrixParser {

    public final static Function<String, Integer> PARSE_INT = string -> Integer.parseInt(string);
    public final static Function<String, Double> PARSE_DOUBLE = string -> Double.parseDouble(string);
    public final static Function<String, Long> PARSE_LONG = string -> Long.parseLong(string);
    public final static Function<String, Float> PARSE_FLOAT = string -> Float.parseFloat(string);
    public final static Function<String, Short> PARSE_SHORT = string -> Short.parseShort(string);

    public static <T> Matrix<T> parse(String s, Function<String, T> function) {
        return parse(s.split("\\|"), function);
    }

    public static Matrix<Double> parse(String s) {
        return parse(s, PARSE_DOUBLE);
    }

    public static <T> Matrix<T> parse(String[] s, Function<String, T> function) {
        if(s.length == 0) {
            throw new IllegalArgumentException("Cannot build a matrix from an empty string");
        }
        int rows = s.length, columns = s[0].split(" ").length;
        T refObj = function.apply(s[0].split(" ")[0]);
        Ring<T> ring = Ring.findRing(refObj);
        Matrix<T> matrix = new Matrix<>(rows, columns, ring.zero(), ring);
        for(int i = 0; i < s.length; i++) {
            final String[] split = s[i].trim().split(" ");
            if(split.length != columns) {
                throw new IllegalArgumentException("Row " + s[i] + " did not match column count of " + columns);
            }
            for(int j = 0; j < split.length; j++) {
                matrix.set(i, j, function.apply(split[j]));
            }
        }
        return matrix;
    }

}
