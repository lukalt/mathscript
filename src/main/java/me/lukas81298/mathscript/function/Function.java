package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 08.06.2018
 */
public interface Function {

    Object execute( BaseInterpreter env, Object... arguments) throws ScriptException;

    default Class<?> mapsTo() {
        return Void.class;
    }

    default boolean acceptsArgumentLength(int i) {
        return true;
    }

    default String getDescription() {
        return null;
    }

}
