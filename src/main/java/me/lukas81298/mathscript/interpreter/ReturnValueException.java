package me.lukas81298.mathscript.interpreter;

/**
 * @author lukas
 * @since 10.06.2018
 */
public class ReturnValueException extends Exception {

    private final Object result;

    public ReturnValueException( Object result ) {
        this.result = result;
    }

    public Object getResult() {
        return this.result;
    }

}
