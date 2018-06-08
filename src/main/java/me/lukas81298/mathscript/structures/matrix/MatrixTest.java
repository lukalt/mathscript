package me.lukas81298.mathscript.structures.matrix;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class MatrixTest {

    public static void main(String[] args) {
        Matrix<Integer> matrix = new Matrix<>(3, 3, 0);
        matrix.set(0, 0, 4);
        matrix.set(0, 1, 3);
        matrix.set(1, 0, 1);
        matrix.set(1, 1, 7);
        System.out.println(matrix.toString());
        System.out.println(matrix.multiply(matrix));

        final Matrix<Integer> parse = MatrixParser.parse("2 0 0 0\n0 3 0 0\n0 0 4 0\n0 0 0 5", MatrixParser.PARSE_INT);
        System.out.println(parse);
        System.out.println(parse.pow(3));

        System.out.println("RED:");
        final Matrix<Double> parse1 = MatrixParser.parse("2 0 0 0\n2 0 0 0\n0 0 0 1");
        System.out.println(parse1);
        System.out.println(parse1.rowEchelonForm());
    }
}
