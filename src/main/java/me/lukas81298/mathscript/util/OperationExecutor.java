package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;

/**
 * @author lukas
 * @since 14.06.2018
 */
public interface OperationExecutor<K, V> {

    void accept(K k, V v) throws ScriptException, ReturnValueException;

}
