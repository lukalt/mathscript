package me.lukas81298.mathscript.interpreter.ops;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.util.OperationExecutor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class RegexLineOperation implements LineOperation<Matcher> {

    private final Pattern pattern;
    private final OperationExecutor<BaseInterpreter, Matcher> function;

    public RegexLineOperation( String pattern, OperationExecutor<BaseInterpreter, Matcher> function ) {
        this.pattern = Pattern.compile( pattern );
        this.function = function;
    }

    public RegexLineOperation( String pattern, OperationExecutor<BaseInterpreter, Matcher> function, int flags ) {
        this.pattern = Pattern.compile( pattern, flags );
        this.function = function;
    }

    @Override
    public Matcher test( String input ) {
        Matcher matcher = this.pattern.matcher( input );
        if( matcher.matches() ) {
            return matcher;
        }
        return null;
    }

    @Override
    public void execute( BaseInterpreter baseInterpreter, Matcher matcher, String line ) throws ScriptException, ReturnValueException {
        this.function.accept( baseInterpreter, matcher );
    }
}
