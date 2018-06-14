package me.lukas81298.mathscript.interpreter.blocks;

import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.ScriptScanner;

/**
 * @author lukas
 * @since 14.06.2018
 */
public abstract class AbstractBlock {

    protected final ScriptScanner scanner;
    protected final BaseInterpreter executor;

    public AbstractBlock( ScriptScanner scanner, BaseInterpreter executor ) {
        this.scanner = scanner;
        this.executor = executor;
    }

}
