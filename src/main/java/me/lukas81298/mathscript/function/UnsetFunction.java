package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class UnsetFunction implements Function {

    @Override
    public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
        String s = Types.ensureType(arguments[0], String.class, false);
        env.getVariables().remove( s );
        return null;
    }

    @Override
    public Class<?> mapsTo() {
        return Void.class;
    }

    @Override
    public boolean acceptsArgumentLength( int i ) {
        return i == 1;
    }
}
