package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
import me.lukas81298.mathscript.struct.InternalArrayList;
import me.lukas81298.mathscript.structures.matrix.Matrix;
import me.lukas81298.mathscript.util.NumberTypeOperations;

import java.util.Collection;
import java.util.List;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class BasicInfixFunction {

    public final static class AddFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            final Object arg0 = arguments[0];
            final Object arg1 = arguments[1];
            if ( arg0 instanceof String || arg1 instanceof String ) {
                return arg0 + "" + arg1;
            }
            if ( arg0 instanceof Matrix && arg1 instanceof Matrix ) {
                Matrix m1 = (Matrix) arg0, m2 = (Matrix) arg1;
                if ( !m1.hasSameSize( m2 ) ) {
                    throw new ScriptException( "Cannot add a " + m1.rows() + "x" + m1.cols() + " matrix and a " + m2.rows() + "x" + m2.cols() + " matrix" );
                }
                return m1.addClone( m2 );
            }
            if( arg0 instanceof List && arg1 instanceof List ) {
                List out = new InternalArrayList();
                out.addAll( (Collection) arg0 );
                out.addAll( (Collection) arg1 );
                return out;
            }

            Number left = Types.ensureType( arg0, Number.class, false );
            Number right = Types.ensureType( arg1, Number.class, false );
            return NumberTypeOperations.addNumbers( left, right );
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
            return NumberTypeOperations.substractNumbers( left, right );
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
            return NumberTypeOperations.multiplyNumbers( left, right );
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
            return NumberTypeOperations.divideNumbers( left, right );
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

    public final static class ModFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            Number left = Types.ensureType( arguments[0], Number.class, false );
            Number right = Types.ensureType( arguments[1], Number.class, false );
            return NumberTypeOperations.modNumbers( left, right );
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
