package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class BasicMathFunction {

    public static final class ExpFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            return Math.exp( Types.ensureType( arguments[0], Number.class, false ).doubleValue() );
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

    public static final class LnFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            return Math.log( Types.ensureType( arguments[0], Number.class, false ).doubleValue() );
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

    public static final class LogFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            double base = 10;
            double value;
            if(arguments.length == 2) {
                base = Types.ensureType( arguments[0], Number.class, false ).doubleValue();
                value = Types.ensureType( arguments[1], Number.class, false ).doubleValue();
                if( base <= 1 ) {
                    throw new ScriptException( "Logarithm base must be greater than 1 but got " + base );
                }
            } else {
                value = Types.ensureType( arguments[0], Number.class, false ).doubleValue();
            }
            return Math.log10( Types.ensureType( value, Number.class, false ).doubleValue() ) / Math.log10( base );
        }

        @Override
        public Class<?> mapsTo() {
            return Double.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1 || i == 2;
        }
    }

    public static final class SgnFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            Number number = Types.ensureType( arguments[0], Integer.class, false );
            if(number.intValue() == 0) {
                return 0;
            } else if(number.intValue() > 0) {
                return 1;
            }
            return -1;
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

    public static final class AbsFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            Number n = Types.ensureType( arguments[0], Number.class, false );
            if( n instanceof Long ) {
                return Math.abs( n.longValue() );
            } else if( n instanceof Double ) {
                return Math.abs( n.doubleValue() );
            } else if( n instanceof Float ) {
                return Math.abs( n.floatValue() );
            }
            return Math.abs( n.intValue() );
        }

        @Override
        public Class<?> mapsTo() {
            return Number.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static final class PowFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            return Math.pow( Types.ensureType( arguments[0], Number.class, false ).doubleValue(), Types.ensureType( arguments[1], Number.class, false ).doubleValue() );
        }

        @Override
        public Class<?> mapsTo() {
            return Number.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public static final class SqrtFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            double d = Types.ensureType( arguments[0], Number.class, false ).doubleValue();
            return Math.sqrt( d );
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
}
