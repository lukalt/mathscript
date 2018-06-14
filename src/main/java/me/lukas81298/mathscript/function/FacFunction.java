package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class FacFunction implements Function {

    @Override
    public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
        return fac( Types.ensureType( arguments[0], Number.class, false ).longValue() );
    }

    private long fac( long i ) {
        if ( i <= 1 ) {
            return 1;
        }
        return i * fac( i - 1L );
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
