package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class ForBlock extends AbstractLoopingBlock {

    public ForBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    public void parseForBlock( String variable, int step, int from, int to ) throws ScriptException, ReturnValueException {
        Object oldVal = this.executor.getVariables().get( variable );
        int index = this.scanner.index(); // store the pc of the while statement
        for ( int i = from; i <= to; i += step ) {
            this.executor.getVariables().put( variable, i );
            this.executeInnerBodyTillDone( index );
        }
        this.skipToDone();
        this.executor.getVariables().put( variable, oldVal ); // restore old state
    }

}
