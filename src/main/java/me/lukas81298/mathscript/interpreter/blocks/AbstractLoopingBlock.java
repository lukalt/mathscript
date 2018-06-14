package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.interpreter.InterpreterUtils;
import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class AbstractLoopingBlock extends AbstractBlock {

    public AbstractLoopingBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    protected void executeInnerBodyTillDone( int index ) throws ScriptException, ReturnValueException {
        while ( this.scanner.hasNextLine() ) {
            String line = InterpreterUtils.stripComment( this.scanner.nextLine().trim() );
            if ( line.toLowerCase().equals( "done" ) ) {
                this.scanner.jump( index );
                return;
            }
            this.executor.parseLine( line );
        }
        throw new ScriptException( "Unexpected end of file, missing done statement" );
    }

    protected void skipToDone() {
        int missingDoneStatements = 1;
        while ( scanner.hasNextLine() ) {
            String lowerCase = scanner.nextLine().toLowerCase().trim();
            if ( lowerCase.startsWith( "while " ) || lowerCase.startsWith( "foreach " ) || lowerCase.startsWith( "for " ) ) {
                missingDoneStatements++;
            } else if ( lowerCase.equals( "done" ) ) {
                missingDoneStatements--;
            }
            if ( missingDoneStatements <= 0 ) {
                break;
            }
        }
    }
}
