package me.lukas81298.mathscript.function.udf;

import me.lukas81298.mathscript.function.Function;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScannerArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 10.06.2018
 */
public class UserDefinedFunction implements Function {

    private final String name;
    private final String[] functionBody;
    private final String[] argumentNames;
    private final Map<String, Function> inheritedFunctions;

    public UserDefinedFunction( String name, String[] functionBody, String[] argumentNames, Map<String, Function> inheritedFunctions ) {
        this.name = name;
        this.functionBody = functionBody;
        this.argumentNames = argumentNames;
        this.inheritedFunctions = inheritedFunctions;
    }

    @Override
    public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
        Map<String, Object> args = new HashMap<>();
        for ( int i = 0; i < this.argumentNames.length; i++ ) {
            args.put( argumentNames[i], arguments[i] );
        }

        BaseInterpreter baseInterpreter = new BaseInterpreter( new ScriptScannerArray( functionBody ), args, inheritedFunctions );
        return baseInterpreter.execute();
    }

    @Override
    public Class<?> mapsTo() {
        return Object.class;
    }

    @Override
    public boolean acceptsArgumentLength( int i ) {
        return this.argumentNames.length == i;
    }

}
