package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
import me.lukas81298.mathscript.util.SneakyThrow;

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
        this.register( PrintFunction.class, "print" );
        this.register( PrintlnFunction.class, "println" );
        this.register( ReadFunctions.ReadFunction.class, "read" );
        this.register( ReadFunctions.ReadLnFunction.class, "readln" );
        this.register( ParseFunctions.ToStringFunction.class, "tostring" );
        this.register( ParseFunctions.ParseDoubleFunction.class, "parsedouble" );
        this.register( ParseFunctions.ParseIntFunction.class, "parseint" );
        this.register( ParseFunctions.ParseFloatFunction.class, "parsefloat" );
        this.register( ParseFunctions.ParseLongFunction.class, "parselong" );

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
        this.register( FacFunction.class, "fac" );

        this.register( BasicMathFunction.SqrtFunction.class, "sqrt" );
        this.register( BasicMathFunction.SqrtFunction.class, "sqrt" );
        this.register( BasicMathFunction.ExpFunction.class, "exp" );
        this.register( BasicMathFunction.AbsFunction.class, "abs" );
        this.register( BasicMathFunction.SgnFunction.class, "sgn" );
        this.register( BasicMathFunction.LnFunction.class, "ln" );
        this.register( BasicMathFunction.LogFunction.class, "log" );
        this.register( MaxFunction.class, "max" );
        this.register( MinFunction.class, "min" );

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
        this.register( ListFunctions.ListPopFunction.class, "listpop" );
        this.register( ListFunctions.ListContainsFunction.class, "listcontains" );
        this.register( ListFunctions.ListGetFunction.class, "listget" );

        // string ops
        this.functionMap.put( "substring", new StringFunctions.SubstringFunction() );
        this.functionMap.put( "stringreverse", new StringFunctions.StringReverseFunction() );

        // matrix ops
        this.functionMap.put( "matrix", new MatrixFunctions.MatrixCreationFunction() );
        this.functionMap.put( "transpose", new BasicInfixFunction.TransposeFunction() );
        this.functionMap.put( "matrixswap", new MatrixFunctions.MatrixSwapFunction() );
    }

    public void register( Class<? extends Function> function, String... names ) {
        Function functionInst;
        try {
             functionInst = function.newInstance();
        } catch ( InstantiationException | IllegalAccessException e ) {
            SneakyThrow.throwSneaky( e );
            return;
        }
        for ( String name : names ) {
            this.functionMap.put( name.toLowerCase(), functionInst );
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
