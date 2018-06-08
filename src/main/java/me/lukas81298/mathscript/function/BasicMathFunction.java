package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class BasicMathFunction {


    public static final class PowFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
