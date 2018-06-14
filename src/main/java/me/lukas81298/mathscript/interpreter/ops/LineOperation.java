package me.lukas81298.mathscript.interpreter.ops;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;

/**
 * @author lukas
 * @since 14.06.2018
 */
public interface LineOperation<K> {

    K test( String input ) throws ScriptException, ReturnValueException;

    void execute( BaseInterpreter baseInterpreter, K k, String line ) throws ScriptException, ReturnValueException;

}
