package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;

import java.util.Scanner;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ReadFunctions {

    public static final class ReadLnFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            if ( arguments.length == 1 ) {
                System.out.print( Types.ensureType( arguments[0], String.class, false ) + ": " );
            }
            Scanner scanner = new Scanner( System.in );
            return scanner.nextLine();
        }

        @Override
        public Class<?> mapsTo() {
            return String.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i <= 1;
        }
    }

    public static final class ReadFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            if ( arguments.length == 1 ) {
                System.out.print( Types.ensureType( arguments[0], String.class, false ) + ": " );
            }
            Scanner scanner = new Scanner( System.in );
            return env.evalExpression( scanner.nextLine() );
        }

        @Override
        public Class<?> mapsTo() {
            return Object.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i <= 1;
        }
    }
}
