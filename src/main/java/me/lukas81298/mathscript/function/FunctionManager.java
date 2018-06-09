package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class FunctionManager {

    private final Map<String, Function> functionMap = new HashMap<>();

    public FunctionManager() {

        // i/o
        this.functionMap.put( "print", new PrintFunction() );
        this.functionMap.put( "println", new PrintlnFunction() );
        this.functionMap.put( "read", new ReadFunctions.ReadFunction() );
        this.functionMap.put( "readln", new ReadFunctions.ReadLnFunction() );

        this.register( new ParseFunctions.ToStringFunction(), "tostring" );
        this.register( new ParseFunctions.ParseDoubleFunction(), "parsedouble" );
        this.register( new ParseFunctions.ParseIntFunction(), "parseint" );
        this.register( new ParseFunctions.ParseFloatFunction(), "parsefloat" );
        this.register( new ParseFunctions.ParseLongFunction(), "parselong" );

        // comparisons
        this.functionMap.put( "==", new CompareInfixFunctions.EqualsFunction() );
        this.functionMap.put( "!=", new CompareInfixFunctions.NotEqualsFunction() );
        this.functionMap.put( "<=", new CompareInfixFunctions.LowerEqFunction() );
        this.functionMap.put( "<", new CompareInfixFunctions.LowerFunction() );
        this.functionMap.put( ">=", new CompareInfixFunctions.GreaterEqFunction() );
        this.functionMap.put( ">", new CompareInfixFunctions.GreaterFunction() );

        // booleans
        this.functionMap.put( "neg", new BoolFunctions.NegFunction() );

        // simple math
        this.functionMap.put( "+", new BasicInfixFunction.AddFunction() );
        this.functionMap.put( "-", new BasicInfixFunction.SubFunction() );
        this.functionMap.put( "*", new BasicInfixFunction.MulFunction() );
        this.functionMap.put( "/", new BasicInfixFunction.DivFunction() );
        this.functionMap.put( "%", new BasicInfixFunction.ModFunction() );
        this.functionMap.put( "pow", new BasicMathFunction.PowFunction() );
        this.functionMap.put( "^", new BasicMathFunction.PowFunction() );
        this.functionMap.put( "sqrt", new BasicMathFunction.SqrtFunction() );
        this.functionMap.put( "fac", new FacFunction() );

        // generic functions
        this.functionMap.put( "length", new LengthFunction() );
        this.functionMap.put( "unset", new UnsetFunction() );
        this.functionMap.put( "typeof", new TypeOfFunction() );

        // list ops
        this.functionMap.put( "list", new ListFunctions.ListFunction() );
        this.functionMap.put( "listadd", new ListFunctions.ListAddFunction() );
        this.functionMap.put( "listrem", new ListFunctions.ListRemoveFunction() );
        this.functionMap.put( "listclear", new ListFunctions.ListClearFunction() );
        this.functionMap.put( "listshuffle", new ListFunctions.ListShuffleFunction() );
        this.functionMap.put( "listsort", new ListFunctions.ListSortFunction() );
        this.functionMap.put( "listdsort", new ListFunctions.ListDSortFunction() );
        this.functionMap.put( "listrev", new ListFunctions.ListReverseFunction() );
        this.functionMap.put( "listcopy", new ListFunctions.ListCopyFunction() );

        // string ops
        this.functionMap.put( "substring", new StringFunctions.SubstringFunction() );
        this.functionMap.put( "stringreverse", new StringFunctions.StringReverseFunction() );

        // matrix ops
        this.functionMap.put( "matrix", new MatrixFunctions.MatrixCreationFunction() );
        this.functionMap.put( "transpose", new BasicInfixFunction.TransposeFunction() );
        this.functionMap.put( "matrixswap", new MatrixFunctions.MatrixSwapFunction() );
    }

    public void register( Function function, String... names ) {
        for ( String name : names ) {
            this.functionMap.put( name.toLowerCase(), function );
        }
    }

    public Object executeFunction( ScriptExecutor executor, String name, Object... parameters ) throws ScriptException {
        name = name.toLowerCase();
        Function f = this.functionMap.get( name );
        if ( f == null ) {
            throw new ScriptException( "Undefined function " + name );
        }
        if ( !f.acceptsArgumentLength( parameters.length ) ) {
            throw new ScriptException( "Cannot run function " + name + " with " + parameters.length + " parameters" );
        }
        return f.execute( executor, parameters );
    }

}
