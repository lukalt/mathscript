package me.lukas81298.mathscript.interpreter;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.function.Function;
import me.lukas81298.mathscript.function.FunctionManager;
import me.lukas81298.mathscript.interpreter.blocks.*;
import me.lukas81298.mathscript.interpreter.ops.EqualsLineOperation;
import me.lukas81298.mathscript.interpreter.ops.LineOperation;
import me.lukas81298.mathscript.interpreter.ops.PrefixLineOperation;
import me.lukas81298.mathscript.interpreter.ops.RegexLineOperation;
import me.lukas81298.mathscript.struct.InternalArrayList;
import me.lukas81298.mathscript.struct.InternalHashSet;
import me.lukas81298.mathscript.struct.InternalTuple;
import me.lukas81298.mathscript.struct.matrix.Matrix;
import me.lukas81298.mathscript.util.MapBuilder;
import me.lukas81298.mathscript.util.ScriptFunction;
import me.lukas81298.mathscript.util.ScriptScanner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class BaseInterpreter {

    private final static String VAR_PATTERN = "[A-Za-z_][A-Za-z0-1_]{0,127}";
    private final ScriptScanner scanner;

    private final Map<Pattern, ScriptFunction<Matcher, Object>> patterns = new LinkedHashMap<>();
    private final List<LineOperation> lineOperations = new LinkedList<>();

    private final Map<String, Object> directAssociatedValues = MapBuilder.<String, Object>create( "true", Boolean.TRUE )
            .map( "false", Boolean.FALSE ).map( "null", null ).build();
    private final Map<Class<? extends AbstractBlock>, AbstractBlock> blocks = new HashMap<>();

    private final FunctionManager functionManager;

    private final Map<String, Object> visibileVariables = new HashMap<>();

    public BaseInterpreter( ScriptScanner scriptScanner ) {
        this( scriptScanner, null, null );
    }

    public BaseInterpreter( ScriptScanner scanner, Map<String, Object> variables, Map<String, Function> functions ) {
        this.scanner = scanner;
        if ( functions == null ) {
            this.functionManager = new FunctionManager();
        } else {
            this.functionManager = new FunctionManager( functions );
        }

        this.blocks.put( FunctionBlock.class, new FunctionBlock( scanner, this ) );
        this.blocks.put( ForBlock.class, new ForBlock( scanner, this ) );
        this.blocks.put( ForEachBlock.class, new ForEachBlock( scanner, this ) );
        this.blocks.put( WhileBlock.class, new WhileBlock( scanner, this ) );
        this.blocks.put( IfOptElseBlock.class, new IfOptElseBlock( scanner, this ) );

        if ( variables != null ) {
            this.visibileVariables.putAll( variables );
        }

        this.registerPattern( "\\[([0-9]+)\\.\\.([0-9]+)\\]", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) {
                List<Integer> list = new InternalArrayList<>();
                int i = Integer.parseInt( matcher.group( 1 ) ), j = Integer.parseInt( matcher.group( 2 ) );
                for ( ; i <= j; i++ ) {
                    list.add( i );
                }
                return list;
            }
        } );
        this.registerPattern( "\\[(.*)\\]", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {

                String elements = matcher.group( 1 );
                final String[] split = InterpreterUtils.splitRealArguments( elements );
                List<Object> list = new InternalArrayList<>();
                for ( String s : split ) {
                    list.add( evalExpression( s ) );
                }
                return list;
            }
        } );
        this.registerPattern( "([_A-Za-z][_A-Za-z0-9]{0,127})\\((.*)\\)", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher functionMatcher ) throws ScriptException {
                String functionName = functionMatcher.group( 1 );
                String[] argsRaw = InterpreterUtils.splitRealArguments( functionMatcher.group( 2 ) );
                Object[] argsTypes;
                if ( argsRaw[0].trim().isEmpty() ) {
                    argsTypes = new Object[0];
                } else {
                    argsTypes = new Object[argsRaw.length];
                    for ( int i = 0; i < argsRaw.length; i++ ) {
                        argsTypes[i] = evalExpression( argsRaw[i] );
                    }
                }
                return executeFunction( functionName, argsTypes );
            }
        } );
        this.registerPattern( "\\((.*)\\)", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {
                String elements = matcher.group( 1 );
                final String[] split = InterpreterUtils.splitRealArguments( elements );
                InternalTuple tuple = new InternalTuple( split.length );
                for ( int i = 0; i < split.length; i++ ) {
                    tuple.set( i, evalExpression( split[i] ) );
                }
                return tuple;
            }
        } );
        this.registerPattern( "\\{(.*)\\}", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {
                String elements = matcher.group( 1 );
                final String[] split = InterpreterUtils.splitRealArguments( elements );
                Set<Object> set = new InternalHashSet<>();
                for ( String s : split ) {
                    set.add( evalExpression( s ) );
                }
                return set;
            }
        } );

        this.registerPattern( "(.*)\\[(.*)\\]", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {
                Object object = Types.ensureNotNull( evalExpression( matcher.group( 1 ) ) );
                Number number = Types.ensureType( evalExpression( matcher.group( 2 ) ), Number.class, false );
                if ( object instanceof List ) {
                    return ( (List) object ).get( number.intValue() );
                }
                if ( object instanceof InternalTuple ) {
                    return ( (InternalTuple) object ).get( number.intValue() );
                }
                if ( object instanceof Matrix ) {
                    List list = new InternalArrayList();
                    for ( Object o : ( (Matrix) object ).getRow( number.intValue() ) ) {
                        list.add( o );
                    }
                    return list;
                }
                throw new ScriptException( "Cannot get field " + number + " from " + object.getClass().getSimpleName() );
            }
        } );
        this.registerPattern( "(.+) *(\\+|-|\\^|<=|<|>=|>|==|!=) *(.+)", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher infixMatcher ) throws ScriptException {
                String left = infixMatcher.group( 1 );
                String op = infixMatcher.group( 2 );
                String right = infixMatcher.group( 3 );
                // System.out.println( "2nd: " + left + " |" + op + "| " + right );
                return executeFunction( op, evalExpression( left ), evalExpression( right ) );
            }
        } );

        this.registerPattern( "(.+) *(\\*|%|\\/) *(.*)", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher infixMatcher ) throws ScriptException {
                String left = infixMatcher.group( 1 );
                String op = infixMatcher.group( 2 );
                String right = infixMatcher.group( 3 );
                // System.out.println( "1st: " + left + " |" + op + "| " + right );
                return executeFunction( op, evalExpression( left ), evalExpression( right ) );
            }
        } );

        this.registerLineOperation( new RegexLineOperation( "((let|var|define) )?(" + VAR_PATTERN + ") *= *(.+)", ( k, matcher ) -> {
            String varName = matcher.group( 3 );
            Object value = evalExpression( matcher.group( 4 ) );
            visibileVariables.put( varName, value );
        } ) );
        this.registerLineOperation( new EqualsLineOperation( "return", ( k, l ) -> {
            throw new ReturnValueException( null );
        } ) );
        this.registerLineOperation( new PrefixLineOperation( "if", ( k, l ) -> {
            getBlock( IfOptElseBlock.class ).parseIfBlock( l );
        } ) );
        this.registerLineOperation( new PrefixLineOperation( "while", ( k, l ) -> {
            getBlock( WhileBlock.class ).parseWhileBlock( l );
        } ) );
        this.registerLineOperation( new PrefixLineOperation( "return", ( k, l ) -> {
            throw new ReturnValueException( evalExpression( l ) );
        } ) );
        this.registerLineOperation( new RegexLineOperation( "function (" + VAR_PATTERN + ")\\((.*)\\)", ( k, matcher ) -> {
            getBlock( FunctionBlock.class ).parseFunction( matcher.group( 1 ), matcher.group( 2 ) );
        } ) );
        this.registerLineOperation( new RegexLineOperation( "(" + VAR_PATTERN + ") *([+\\-*/])= *(.*)", ( k, matcher ) -> {
            Object oldValue = this.visibileVariables.get( matcher.group( 1 ) );
            this.visibileVariables.put( matcher.group( 1 ), executeFunction( matcher.group( 2 ), oldValue, evalExpression( matcher.group( 3 ) ) ) );
        } ) );
        this.registerLineOperation( new RegexLineOperation( "(" + VAR_PATTERN + ") *([+\\-]{2})", ( k, matcher ) -> {
            Object oldValue = this.visibileVariables.get( matcher.group( 1 ) );
            this.visibileVariables.put( matcher.group( 1 ), executeFunction( matcher.group( 2 ).substring( 0, 1 ), oldValue, 1 ) );
        } ) );
        this.registerLineOperation( new RegexLineOperation( "foreach () in (.*)", ( k, matcher ) -> {
            getBlock( ForEachBlock.class ).parseForEachBlock( matcher.group( 1 ), Types.ensureType( evalExpression( matcher.group( 2 ) ), Iterable.class, false ) );
        }, Pattern.MULTILINE ) );
        this.registerLineOperation( new RegexLineOperation( "for +([A-Za-z_][A-Za-z0-1_]{0,127}) +(step (.*) +)?from +(.*) +to +(.*)", ( k, matcher ) -> {
            if ( matcher.group( 2 ) != null ) { // with step
                getBlock( ForBlock.class ).parseForBlock( matcher.group( 1 ), Types.ensureType( evalExpression( matcher.group( 3 ) ), Number.class, false ).intValue(),
                        Types.ensureType( evalExpression( matcher.group( 4 ) ), Number.class, false ).intValue(),
                        Types.ensureType( evalExpression( matcher.group( 5 ) ), Number.class, false ).intValue() );
            } else {
                getBlock( ForBlock.class ).parseForBlock( matcher.group( 1 ), 1,
                        Types.ensureType( evalExpression( matcher.group( 4 ) ), Number.class, false ).intValue(),
                        Types.ensureType( evalExpression( matcher.group( 5 ) ), Number.class, false ).intValue() );
            }
        } ) );

    }

    public void registerLineOperation( LineOperation op ) {
        this.lineOperations.add( op );
    }

    public <K extends AbstractBlock> K getBlock( Class<K> clazz ) throws ScriptException {
        final K k = clazz.cast( this.blocks.get( clazz ) );
        if ( k == null ) {
            throw new ScriptException( "No block interpreter present for class " + clazz.getSimpleName() );
        }
        return k;
    }

    public Map<String, Object> getVariables() {
        return visibileVariables;
    }

    public FunctionManager getFunctionManager() {
        return functionManager;
    }

    private void registerPattern( String pattern, ScriptFunction<Matcher, Object> function ) {
        this.patterns.put( Pattern.compile( pattern ), function );
    }

    public Object execute() throws ScriptException {
        while ( this.scanner.hasNextLine() ) {
            String line = this.scanner.nextLine();
            try {
                this.parseLine( line );
            } catch ( ReturnValueException e ) {
                return e.getResult();
            }
        }
        return null;
    }


    public void parseLine( String line ) throws ScriptException, ReturnValueException {
        if ( line.equals( "/*" ) ) {
            do {
                if ( !scanner.hasNextLine() ) {
                    throw new ScriptException( "Unexpected end of file, missing */" );
                }
                line = scanner.nextLine().trim();
            } while ( !line.equalsIgnoreCase( "*/" ) );
            if ( !scanner.hasNextLine() ) {
                return;
            }
            line = scanner.nextLine().trim();
        }
        line = InterpreterUtils.stripComment( line ).trim();
        if ( !line.isEmpty() ) {
            for ( LineOperation op : this.lineOperations ) {
                Object z = op.test( line );
                if ( z != null ) {
                    //noinspection unchecked
                    op.execute( this, z, line );
                    return;
                }
            }
            evalExpression( line );
        }
    }

    private Object executeFunction( String name, Object... args ) throws ScriptException {
        return this.functionManager.executeFunction( this, name, args );
    }

    public Object evalExpression( String s ) throws ScriptException {
        s = s.trim();
        Number number = Types.checkIfNumber( s );
        if ( number != null ) {
            return number;
        }
        String lowerRegion = s.toLowerCase();
        if ( this.directAssociatedValues.containsKey( lowerRegion ) ) { // map may contain null values as well
            return this.directAssociatedValues.get( lowerRegion );
        }
        if ( s.startsWith( "\"" ) && s.endsWith( "\"" ) ) {
            String m = InterpreterUtils.validateStringCandidate( s );
            if ( m != null ) {
                return m;
            }
        }

        for ( Map.Entry<Pattern, ScriptFunction<Matcher, Object>> entry : this.patterns.entrySet() ) {
            Matcher matcher = entry.getKey().matcher( s );
            if ( matcher.matches() ) {
                return entry.getValue().apply( matcher );
            }
        }

        if ( s.startsWith( "\"" ) && s.endsWith( "\"" ) ) { // todo maybe comment out?
            return s.length() == 2 ? "" : s.substring( 1, s.length() - 1 );
        }

        if ( visibileVariables.containsKey( s ) ) {
            return visibileVariables.get( s );
        }

        if ( s.endsWith( "!" ) ) {
            s = s.substring( 0, s.length() - 1 );
            return executeFunction( "fac", evalExpression( s ) );
        }
        if ( s.startsWith( "!" ) ) {
            s = s.substring( 1 );
            return executeFunction( "neg", evalExpression( s ) );
        }
        throw new ScriptException( "Invalid statement or undefined variable " + s );
    }

}
