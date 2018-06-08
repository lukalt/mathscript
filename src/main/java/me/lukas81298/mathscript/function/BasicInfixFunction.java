package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
import me.lukas81298.mathscript.structures.matrix.Matrix;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class BasicInfixFunction {

    public final static class AddFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            if ( arguments[0] instanceof String || arguments[1] instanceof String ) {
                return arguments[0] + "" + arguments[1];
            }
            if ( arguments[0] instanceof Matrix && arguments[1] instanceof Matrix ) {
                Matrix m1 = (Matrix) arguments[0], m2 = (Matrix) arguments[1];
                if ( !m1.hasSameSize( m2 ) ) {
                    throw new ScriptException( "Cannot add a " + m1.rows() + "x" + m1.cols() + " matrix and a " + m2.rows() + "x" + m2.cols() + " matrix" );
                }
                return m1.addClone( m2 );
            }

            Number left = Types.ensureType( arguments[0], Number.class, false );
            Number right = Types.ensureType( arguments[1], Number.class, false );
            return Types.addNumbers( left, right );
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

    public final static class SubFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            Number left = Types.ensureType( arguments[0], Number.class, false );
            Number right = Types.ensureType( arguments[1], Number.class, false );
            return Types.substractNumbers( left, right );
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

    public final static class MulFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            if ( arguments[0] != null && arguments[1] != null && arguments[0] instanceof Matrix && arguments[1] instanceof Matrix ) {
                return ((Matrix) arguments[0]).multiplyClone( (Matrix) arguments[1] );
            }
            Number left = Types.ensureType( arguments[0], Number.class, false );
            Number right = Types.ensureType( arguments[1], Number.class, false );
            return Types.multiplyNumbers( left, right );
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

    public final static class DivFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            Number left = Types.ensureType( arguments[0], Number.class, false );
            Number right = Types.ensureType( arguments[1], Number.class, false );
            return Types.divideNumbers( left, right );
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

    public final static class TransposeFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return Types.ensureType( arguments[0], Matrix.class, false ).transpose();
        }

        @Override
        public Class<?> mapsTo() {
            return Matrix.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }
}
