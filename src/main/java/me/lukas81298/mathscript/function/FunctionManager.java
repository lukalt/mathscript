package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.util.SneakyThrow;

import java.util.*;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class FunctionManager {

    private final Map<String, Function> functionMap = new HashMap<>();
    private final Set<String> reservedFunctionNames = new HashSet<>( Arrays.asList( "if", "else", "fi", "function", "end function", "while", "let", "var", "define" ) );

    public FunctionManager() {

        try {
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
            this.register( MatrixFunctions.MatrixParseFunction.class, "matrixparse" );
        } catch ( Throwable t ) {
            SneakyThrow.throwSneaky( t );
        }
    }

    public Map<String, Function> getFunctions() {
        return this.functionMap;
    }

    public FunctionManager( Map<String, Function> functions ) {
        this.functionMap.putAll( functions );
    }

    public void register( Class<? extends Function> function, String... names ) throws ScriptException {
        Function functionInst;
        try {
             functionInst = function.newInstance();
        } catch ( InstantiationException | IllegalAccessException e ) {
            SneakyThrow.throwSneaky( e );
            return;
        }
        this.register( functionInst, names );
    }

    public void register( Function function, String... names ) throws ScriptException {
        for ( String name : names ) {
            if( reservedFunctionNames.contains( name.toLowerCase() ) ) {
                throw new ScriptException( "Cannot register function " + name + " because the name is reserved" );
            }
            this.functionMap.put( name.toLowerCase(), function );
        }
    }

    public boolean isFunctionPresent( String name ) {
        return this.functionMap.containsKey( name.toLowerCase() );
    }

    public Object executeFunction( BaseInterpreter executor, String name, Object... parameters ) throws ScriptException {
        //System.out.println( "Exec function " + name + "(" + Arrays.toString( parameters ) + ")" );
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
