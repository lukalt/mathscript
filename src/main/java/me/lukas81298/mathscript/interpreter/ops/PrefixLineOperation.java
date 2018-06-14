package me.lukas81298.mathscript.interpreter.ops;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.util.OperationExecutor;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class PrefixLineOperation implements LineOperation<String> {

    private final String prefix;
    private final OperationExecutor<BaseInterpreter, String> function;

    public PrefixLineOperation( String prefix, OperationExecutor<BaseInterpreter, String> function ) {
        this.prefix = prefix;
        this.function = function;
    }

    @Override
    public String test( String input ) {
        if ( input.toLowerCase().startsWith( prefix + " " ) ) {
            return input.substring( prefix.length() );
        }
        return null;
    }

    @Override
    public void execute( BaseInterpreter baseInterpreter, String remaining, String line ) throws ScriptException, ReturnValueException {
        this.function.accept( baseInterpreter, remaining );
    }
}
