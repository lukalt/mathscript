package me.lukas81298.mathscript;

import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScannerStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class Main {

    public static void main( String[] args ) {
        try {
            new BaseInterpreter( new ScriptScannerStream( new FileInputStream( new File( "functionTest.ms" ) ) ) ).execute();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( ScriptException e ) {
            e.printStackTrace();
            System.out.println( "Error executing script: " + e.getMessage() );
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }
}
