package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.interpreter.ReturnValueException;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;

import java.util.Iterator;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class ForEachBlock extends AbstractLoopingBlock {

    public ForEachBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    public void parseForEachBlock( String variable, Iterable iterable ) throws ScriptException, ReturnValueException {
        Object oldVal = this.executor.getVariables().get( variable );
        int index = this.scanner.index(); // store the pc of the while statement
        Iterator it = iterable.iterator();
        while ( it.hasNext() ) {
            this.executor.getVariables().put( variable, it.next() );
            this.executeInnerBodyTillDone( index );
        }
        this.skipToDone();
        this.executor.getVariables().put( variable, oldVal ); // restore old state
    }

}
