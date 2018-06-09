package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class MinFunction implements Function {

    @Override
    public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
        if ( arguments.length == 1 ) {
            Iterable collection = Types.ensureType( arguments[0], Iterable.class, false );
            List list = new ArrayList();
            for ( Object o : collection ) {
                list.add( o );
            }
            if(list.isEmpty()) {
                throw new ScriptException( "Cannot get minimum of empty collection" );
            }
            Collections.sort( list );
            return list.get( 0 );
        }
        Object[] objects = new Object[arguments.length];
        int i = 0;
        for ( Object argument : arguments ) {
            if ( !( argument instanceof Comparable ) ) {
                throw new ScriptException( argument + " is not comparable" );
            }
            objects[i] = argument;
            i++;
        }
        Arrays.sort(objects);
        return objects[0];
    }

    @Override
    public Class<?> mapsTo() {
        return Object.class;
    }

    @Override
    public boolean acceptsArgumentLength( int i ) {
        return i > 0;
    }
}
