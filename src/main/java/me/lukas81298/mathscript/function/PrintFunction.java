package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class PrintFunction implements Function {

    @Override
    public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
        System.out.print( arguments[0] );
        return arguments[0];
    }

    @Override
    public boolean acceptsArgumentLength( int i ) {
        return i == 1;
    }

    @Override
    public Class<?> mapsTo() {
        return String.class;
    }

    @Override
    public String getDescription() {
        return "Prints the given object to the console and returns the printed string.";
    }
}
