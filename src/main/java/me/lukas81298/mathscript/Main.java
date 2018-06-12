package me.lukas81298.mathscript;

import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
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
            new ScriptExecutor( new ScriptScannerStream( new FileInputStream( new File( "dotBeforeDash.ms" ) ) ) ).execute();
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
