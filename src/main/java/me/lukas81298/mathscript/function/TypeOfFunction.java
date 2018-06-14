package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.struct.InternalTuple;
import me.lukas81298.mathscript.struct.matrix.Matrix;

import java.util.List;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class TypeOfFunction implements Function {

    @Override
    public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
        Object o = arguments[0];
        if( o == null ) {
            return "null";
        }
        if( o instanceof List ) {
            return "(list)";
        }
        if( o instanceof InternalTuple ) {
            return "(" + ((InternalTuple)o).length() + "-tuple)";
        }
        if( o instanceof Matrix ) {
            Matrix m = (Matrix) o;
            return "(" + m.rows() + "x" + m.cols() + "-matrix)";
        }
        return "(" + o.getClass().getSimpleName().toLowerCase() + ")";
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
