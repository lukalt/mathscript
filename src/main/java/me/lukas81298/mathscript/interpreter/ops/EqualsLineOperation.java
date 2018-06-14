package me.lukas81298.mathscript.interpreter.ops;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.util.OperationExecutor;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class EqualsLineOperation implements LineOperation<Boolean> {

    private final String match;
    private final OperationExecutor<BaseInterpreter, String> function;

    public EqualsLineOperation( String match, OperationExecutor<BaseInterpreter, String> function ) {
        this.match = match.toLowerCase();
        this.function = function;
    }

    @Override
    public Boolean test( String input ) {
        return input.toLowerCase().equals( match )? Boolean.TRUE : null;
    }

    @Override
    public void execute( BaseInterpreter baseInterpreter, Boolean b, String line ) throws ScriptException, ReturnValueException {
        this.function.accept( baseInterpreter, line );
    }

}
