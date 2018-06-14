package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.interpreter.ScriptException;

/**
 * @author lukas
 * @since 09.06.2018
 */
public interface ScriptScanner {

    boolean hasNextLine();

    String nextLine();

    int index();

    void branch( int index ) throws ScriptException;

    void jump( int index ) throws ScriptException;
}
