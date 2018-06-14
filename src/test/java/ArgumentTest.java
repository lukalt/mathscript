import me.lukas81298.mathscript.interpreter.InterpreterUtils;

import java.util.Arrays;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class ArgumentTest {

    public static void main(String[] args) {
        for ( String s : InterpreterUtils.splitRealArguments( "list(1,2,3),list(4,5,6)" ) ) {
            System.out.println( s );
        }
    }
}
