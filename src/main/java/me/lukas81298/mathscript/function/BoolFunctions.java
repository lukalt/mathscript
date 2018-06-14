package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class BoolFunctions {

    public final static class NegFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            return !Types.ensureType(arguments[0], Boolean.class, false);
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }
}
