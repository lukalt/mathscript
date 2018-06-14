package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

import java.util.Objects;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class ParseFunctions {

    public final static class ParseFloatFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String s = Types.ensureType(arguments[0], String.class, false);
            try {
                return Float.parseFloat( s );
            } catch ( NumberFormatException e ) {
                throw new ScriptException( "Could not parse " + s + " as a float" );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return Float.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public final static class ParseDoubleFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String s = Types.ensureType(arguments[0], String.class, false);
            try {
                return Double.parseDouble( s );
            } catch ( NumberFormatException e ) {
                throw new ScriptException( "Could not parse " + s + " as a double" );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return Double.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public final static class ParseIntFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String s = Types.ensureType(arguments[0], String.class, false);
            try {
                return Integer.parseInt( s );
            } catch ( NumberFormatException e ) {
                throw new ScriptException( "Could not parse " + s + " as an integer" );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return Integer.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public final static class ParseLongFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            String s = Types.ensureType(arguments[0], String.class, false);
            try {
                return Long.parseLong( s );
            } catch ( NumberFormatException e ) {
                throw new ScriptException( "Could not parse " + s + " as a long" );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return Long.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public final static class ToStringFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) {
            return Objects.toString(arguments[0]);
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
}
