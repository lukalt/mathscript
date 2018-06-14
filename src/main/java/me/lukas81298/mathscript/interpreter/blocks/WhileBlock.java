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
public class WhileBlock extends AbstractLoopingBlock {

    public WhileBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        super( scanner, executor );
    }

    public void parseWhileBlock( String condition ) throws ScriptException, ReturnValueException {
        int index = this.scanner.index(); // store the pc of the while statement
        while ( Objects.equals( Boolean.TRUE, this.executor.evalExpression( condition ) ) ) {
            this.executeInnerBodyTillDone( index );
        }
        this.skipToDone(); // skip everything till we see the next done statement
    }

}
