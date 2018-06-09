package me.lukas81298.mathscript.util;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class ScriptScannerStream implements ScriptScanner {

    private final Scanner delegate;

    public ScriptScannerStream( InputStream in ) {
        this.delegate = new Scanner( in );
    }

    @Override
    public boolean hasNextLine() {
        return this.delegate.hasNextLine();
    }

    @Override
    public String nextLine() {
        return this.delegate.nextLine();
    }
}
