package me.lukas81298.mathscript;

import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ConsoleMode {

    public static void main( String[] args ) {
        ScriptExecutor executor = new ScriptExecutor( System.in );
        while ( true ) {
            try {
                executor.parse();
            } catch ( ScriptException e ) {
                e.printStackTrace();
            }
        }
    }
}
