package me.lukas81298.mathscript.util;

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
}
