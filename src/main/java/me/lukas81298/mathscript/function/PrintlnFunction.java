package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.parser.ScriptExecutor;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class PrintlnFunction implements Function {

    @Override
    public Object execute( ScriptExecutor env, Object... arguments ) {
        for ( Object argument : arguments ) {
            System.out.println( argument );
        }
        return null;
    }

}
