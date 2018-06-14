package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class PrintlnFunction implements Function {

    @Override
    public Object execute( BaseInterpreter env, Object... arguments ) {
        if(arguments.length == 0) {
            System.out.println();
            return null;
        }
        for ( Object argument : arguments ) {
            System.out.println( argument );
        }
        return null;
    }

}
