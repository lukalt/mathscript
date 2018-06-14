package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.interpreter.ScriptException;

/**
 * @author lukas
 * @since 08.06.2018
 */
public interface ScriptFunction<T, R> {

    R apply( T t ) throws ScriptException;
}
