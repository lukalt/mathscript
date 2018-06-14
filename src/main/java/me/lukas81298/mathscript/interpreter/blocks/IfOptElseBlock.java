package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;

import java.util.Objects;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class IfOptElseBlock extends AbstractBlock {

    public IfOptElseBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    public void parseIfBlock( String condition ) throws ScriptException, ReturnValueException {
        // todo implement else if
        if ( Objects.equals( Boolean.TRUE, this.executor.evalExpression( condition ) ) ) { // eval expression and check if it is true
            boolean inElse = false;
            while ( this.scanner.hasNextLine() ) {
                String line = scanner.nextLine().trim();
                String lowerCase = line.toLowerCase();
                if ( lowerCase.equals( "else" ) ) {
                    inElse = true;
                    continue;
                }
                if ( lowerCase.equals( "fi" ) ) {
                    return;
                }
                if ( !inElse ) {
                    this.executor.parseLine( line );
                }
            }
        } else {
            boolean inElse = false;
            while ( this.scanner.hasNextLine() ) {
                String line = scanner.nextLine().trim();
                String lowerCase = line.toLowerCase();
                if ( lowerCase.equals( "else" ) ) {
                    inElse = true;
                    continue;
                }
                if ( lowerCase.equals( "fi" ) ) {
                    return;
                }
                if ( inElse ) {
                    this.executor.parseLine( line );
                }
            }
        }

    }
}
