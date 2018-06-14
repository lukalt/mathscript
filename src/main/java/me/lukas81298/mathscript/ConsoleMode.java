package me.lukas81298.mathscript;

import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScannerStream;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ConsoleMode {

    public static void main( String[] args ) {
        BaseInterpreter executor = new BaseInterpreter( new ScriptScannerStream( System.in ) );
        while ( true ) {
            try {
                executor.execute();
            } catch ( ScriptException e ) {
                e.printStackTrace();
            }
        }
    }
}
