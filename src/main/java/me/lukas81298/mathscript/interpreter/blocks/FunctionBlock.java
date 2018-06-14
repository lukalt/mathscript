package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.function.udf.UserDefinedFunction;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;
import me.lukas81298.mathscript.util.StringChecker;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class FunctionBlock extends AbstractBlock {

    public FunctionBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    private String[] parseArgumentNames( String raw ) throws ScriptException {
        String[] split = raw.split( "," );
        String[] o = new String[split.length];
        int i = 0;
        for ( String s : split ) {
            s = s.trim();
            if ( s.isEmpty() || !StringChecker.isCorrectVariableName( s ) ) {
                throw new ScriptException( "Invalid variable name " + s );
            }
            o[i] = s;
            i++;
        }
        return o;
    }

    public void parseFunction( String functionName, String rawArguments ) throws ScriptException {
        if ( this.executor.getFunctionManager().isFunctionPresent( functionName ) ) {
            throw new ScriptException( "Function " + functionName + " is already registered" );
        }
        functionName = functionName.toLowerCase();
        String[] argumentNames = parseArgumentNames( rawArguments );
        List<String> scanned = new LinkedList<>();
        while ( this.scanner.hasNextLine() ) {
            String line = this.scanner.nextLine();
            if ( line.trim().toLowerCase().equals( "end function" ) ) {
                this.executor.getFunctionManager().register( new UserDefinedFunction( functionName, scanned.toArray( new String[0] ), argumentNames, this.executor.getFunctionManager().getFunctions() ), functionName );
                return;
            } else {
                scanned.add( line );
            }
        }
        throw new ScriptException( "Unexpected end of file, expected 'end function'" );
    }


}
