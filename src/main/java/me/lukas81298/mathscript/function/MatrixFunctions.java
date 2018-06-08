package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
import me.lukas81298.mathscript.structures.matrix.Matrix;
import me.lukas81298.mathscript.structures.matrix.Ring;

import java.util.List;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class MatrixFunctions {

    public static final class MatrixAddFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return null;
        }

        @Override
        public Class<?> mapsTo() {
            return null;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 3;
        }
    }

    public static final class MatrixSwapFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            Matrix m = Types.ensureType( arguments[0], Matrix.class, false );
            int i = Types.ensureType( arguments[1], Number.class, false ).intValue(), j = Types.ensureType( arguments[2], Number.class, false ).intValue();
            return m.swap( i, j );
        }

        @Override
        public Class<?> mapsTo() {
            return Matrix.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 3;
        }
    }

    public static final class MatrixCreationFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            if ( arguments.length == 0 ) {
                return new Matrix<>( 0, 0, null );
            }
            int rows = arguments.length;
            int cols = -1;
            for ( int i = 0; i < arguments.length; i++ ) {
                List list = Types.ensureType( arguments[i], List.class, false );
                if ( cols == -1 ) {
                    cols = list.size();
                } else if ( list.size() != cols ) {
                    throw new ScriptException( "Expected column with length of " + cols + " but got " + list.size() );
                }
            }
            Ring ring = Ring.findRing( ( (List) arguments[0] ).get( 0 ) );
            Matrix<Object> matrix = new Matrix<>( rows, cols, ring.zero(), ring );
            for ( int i = 0; i < arguments.length; i++ ) {
                List list = (List) arguments[i];
                for ( int j = 0; j < cols; j++ ) {
                    matrix.set( i, j, list.get( j ) );
                }
            }
            return matrix;
        }

        @Override
        public Class<?> mapsTo() {
            return Matrix.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return true;
        }
    }

    public final class MatrixIdFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            int size = Types.ensureType( arguments[0], Integer.class, false );
            if ( arguments.length == 3 ) {
                Object zero = arguments[1];
                Object one = arguments[2];
                return Matrix.identity( size, zero, one );
            } else {
                return Matrix.identityD( size );
            }
        }

        @Override
        public Class<?> mapsTo() {
            return Matrix.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1 || i == 3;
        }
    }
}
