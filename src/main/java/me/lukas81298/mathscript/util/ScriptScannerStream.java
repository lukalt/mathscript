package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.interpreter.ScriptException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class ScriptScannerStream implements ScriptScanner {

    private final Scanner delegate;
    private final List<String> buffer = new ArrayList<>(); // use an array list here for random access
    private int index = 0;

    public ScriptScannerStream( InputStream in ) {
        this.delegate = new Scanner( in );
    }

    @Override
    public boolean hasNextLine() {
        return index < this.buffer.size() || this.delegate.hasNextLine();
    }

    @Override
    public String nextLine() {
        if ( this.index < this.buffer.size() ) {
            String line = this.buffer.get( index );
            this.index++;
            return line;
        }
        String line = this.delegate.nextLine(); // read in new line and increment pointer
        this.buffer.add( line );
        this.index++;
        return line;
    }

    @Override
    public int index() {
        return this.index;
    }

    @Override
    public void branch( int rel ) throws ScriptException {
        this.jump( this.index + rel );
    }

    @Override
    public void jump( int index ) throws ScriptException {
        if( index < 0 || index >= this.buffer.size() ) {
            throw new ScriptException( "Invalid index " + index + " for buffer length " + this.buffer.size() );
        }
        this.index = index;
    }
}
