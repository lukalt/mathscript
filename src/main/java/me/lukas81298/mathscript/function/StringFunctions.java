package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class StringFunctions {

    public static final class StringReverseFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String src = Types.ensureType( arguments[0], String.class, false );
            char[] srcChars = src.toCharArray();
            char[] destChars = new char[srcChars.length];
            for ( int i = 0; i < srcChars.length; i++ ) {
                destChars[destChars.length - 1 - i] = srcChars[i];
            }
            return new String(destChars);
        }

        @Override
        public Class<?> mapsTo() {
            return String.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static final class SubstringFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String source = Types.ensureType(arguments[0], String.class, false);
            int i1 = Types.ensureType( arguments[1], Number.class, false).intValue();
            if(arguments.length == 3) {
                int i2 = Types.ensureType( arguments[1], Number.class, false).intValue();
                return source.substring( i1, i2 );
            } else {
                return source.substring( i1 );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return String.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2 || i == 3;
        }
    }

}
