package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.parser.ScriptException;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class ScriptScannerArray implements ScriptScanner {

    private final String[] array;
    private int index = 0;

    public ScriptScannerArray( String[] array ) {
        this.array = array;
    }

    @Override
    public boolean hasNextLine() {
        return this.index < array.length;
    }

    @Override
    public String nextLine() {
        String s = this.array[index];
        index++;
        return s;
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
        if(index < 0 || index >= this.array.length ) {
            throw new ScriptException( "Cannot jump to " + index + ", capacity is " + this.array.length );
        }
        this.index = index;
    }
}
