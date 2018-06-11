package me.lukas81298.mathscript.parser;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.function.Function;
import me.lukas81298.mathscript.function.FunctionManager;
import me.lukas81298.mathscript.function.udf.UserDefinedFunction;
import me.lukas81298.mathscript.struct.InternalArrayList;
import me.lukas81298.mathscript.struct.InternalHashSet;
import me.lukas81298.mathscript.struct.Tuple;
import me.lukas81298.mathscript.util.ScriptFunction;
import me.lukas81298.mathscript.util.ScriptScanner;
import me.lukas81298.mathscript.util.StringChecker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ScriptExecutor {

    private final static String VAR_PATTERN = "[A-Za-z_][A-Za-z0-1_]{0,127}";
    private final ScriptScanner scanner;
    private final Pattern letPattern = Pattern.compile( "(let|var|define) (" + VAR_PATTERN + ") *= *(.+)" );
    private final Pattern suffixFunctionPattern = Pattern.compile( "(" + VAR_PATTERN + ") *([+\\-*/])= *(.*)" );
    private final Pattern incrementFunctionPattern = Pattern.compile( "(" + VAR_PATTERN + ") *([+\\-]{2})" );
    private final Pattern forEachPattern = Pattern.compile( "foreach ([A-Za-z_][A-Za-z0-1_]{0,127}) in (.*)", Pattern.MULTILINE );
    private final Pattern forPattern = Pattern.compile( "for +([A-Za-z_][A-Za-z0-1_]{0,127}) +(step (.*) +)?from +(.*) +to +(.*)" );
    private final Pattern functionPattern = Pattern.compile( "function (" + VAR_PATTERN + ")\\((.*)\\)" );

    private final Map<Pattern, ScriptFunction<Matcher, Object>> patterns = new LinkedHashMap<>();

    private final FunctionManager functionManager;

    private final Map<String, Object> scopedDefinedVariables = new HashMap<>();

    public ScriptExecutor( ScriptScanner scriptScanner ) {
        this( scriptScanner, null, null );
    }

    public ScriptExecutor( ScriptScanner scanner, Map<String, Object> variables, Map<String, Function> functions ) {
        this.scanner = scanner;
        if ( functions == null ) {
            this.functionManager = new FunctionManager();
        } else {
            this.functionManager = new FunctionManager( functions );
        }
        if ( variables != null ) {
            this.scopedDefinedVariables.putAll( variables );
        }
        this.registerPattern( "\\[([0-9]+)\\.\\.([0-9]+)\\]", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {
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
                final String[] split = elements.split( "," );
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
                String[] argsRaw = functionMatcher.group( 2 ).split( "," );
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
                final String[] split = elements.split( "," );
                Tuple tuple = new Tuple( split.length );
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
                final String[] split = elements.split( "," );
                Set<Object> set = new InternalHashSet<>();
                for ( String s : split ) {
                    set.add( evalExpression( s ) );
                }
                return set;
            }
        } );

        this.registerPattern( "(.+)[ ]*((\\+|-|\\*|\\.|%|\\^|\\/|<=|<|>=|>|==|!=)[ ]*(.+))+", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher infixMatcher ) throws ScriptException {
                String left = infixMatcher.group( 1 );
                String op = infixMatcher.group( 3 );
                String right = infixMatcher.group( 4 );
                return executeFunction( op, evalExpression( left ), evalExpression( right ) );
            }
        } );

    }

    private void registerPattern( String pattern, ScriptFunction<Matcher, Object> function ) {
        this.patterns.put( Pattern.compile( pattern ), function );
    }


    public Object execute() throws ScriptException {
        while ( this.scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            try {
                parseLine( line );
            } catch ( ReturnValueException e ) {
                return e.getResult();
            }
        }
        return null;
    }

    private void parseWhileBlock( String condition ) throws ScriptException, ReturnValueException {
        int index = this.scanner.index(); // store the pc of the while statement
        while ( Objects.equals( Boolean.TRUE, evalExpression( condition ) ) ) {
            executeInnerBodyTillDone( index );
        }
        this.skipToDone(); // skip everything till we see the next done statement
    }

    private void parseForEachBlock( String variable, Iterable iterable ) throws ScriptException, ReturnValueException {
        Object oldVal = this.scopedDefinedVariables.get( variable );
        int index = this.scanner.index(); // store the pc of the while statement
        Iterator it = iterable.iterator();
        while ( it.hasNext() ) {
            this.scopedDefinedVariables.put( variable, it.next() );
            this.executeInnerBodyTillDone( index );
        }
        this.skipToDone();
        this.scopedDefinedVariables.put( variable, oldVal ); // restore old state
    }

    private void parseForBlock( String variable, int step, int from, int to ) throws ScriptException, ReturnValueException {
        Object oldVal = this.scopedDefinedVariables.get( variable );
        int index = this.scanner.index(); // store the pc of the while statement
        for ( int i = from; i <= to; i += step ) {
            this.scopedDefinedVariables.put( variable, i );
            this.executeInnerBodyTillDone( index );
        }
        this.skipToDone();
        this.scopedDefinedVariables.put( variable, oldVal ); // restore old state
    }

    private void executeInnerBodyTillDone( int index ) throws ScriptException, ReturnValueException {
        while ( this.scanner.hasNextLine() ) {
            String line = stripComment( this.scanner.nextLine().trim() );
            if ( line.toLowerCase().equals( "done" ) ) {
                this.scanner.jump( index );
                return;
            }
            parseLine( line );
        }
        throw new ScriptException( "Unexpected end of file, missing done statement" );
    }

    private void skipToDone() {
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

    private void parseIfBlock( String condition ) throws ScriptException, ReturnValueException {
        if ( Objects.equals( Boolean.TRUE, evalExpression( condition ) ) ) { // eval expression and check if it is true
            boolean inElse = false;
            while ( this.scanner.hasNextLine() ) {
                String line = scanner.nextLine().trim();
                String lowerCase = line.toLowerCase();
                if ( lowerCase.equals( "else" ) ) {
                    inElse = true;
                    continue;
                }
                if ( lowerCase.equals( "fi" ) ) {
                    return;
                }
                if ( !inElse ) {
                    parseLine( line );
                }
            }
        } else {
            boolean inElse = false;
            while ( this.scanner.hasNextLine() ) {
                String line = scanner.nextLine().trim();
                String lowerCase = line.toLowerCase();
                if ( lowerCase.equals( "else" ) ) {
                    inElse = true;
                    continue;
                }
                if ( lowerCase.equals( "fi" ) ) {
                    return;
                }
                if ( inElse ) {
                    parseLine( line );
                }
            }
        }

    }

    private String stripComment( String line ) {
        int firstComment = line.indexOf( "//" );
        if ( firstComment >= 0 ) {
            line = line.substring( 0, firstComment ).trim();
        }
        return line;
    }

    private void parseLine( String line ) throws ScriptException, ReturnValueException {
        //  System.out.println( "Parse line " + line );
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
        line = stripComment( line ).trim();
        if ( !line.isEmpty() ) {
            Matcher matcher = letPattern.matcher( line );
            if ( matcher.find() ) {
                String varName = matcher.group( 2 );
                Object value = evalExpression( matcher.group( 3 ) );
                scopedDefinedVariables.put( varName, value );
            } else {
                String lowerLine = line.toLowerCase();
                if ( lowerLine.startsWith( "if " ) ) {
                    parseIfBlock( line.substring( "if ".length() ) );
                } else if ( lowerLine.startsWith( "while " ) ) {
                    parseWhileBlock( line.substring( "while ".length() ) );
                } else if ( lowerLine.equals( "return" ) ) {
                    throw new ReturnValueException( null );
                } else if ( lowerLine.startsWith( "return " ) ) {
                    throw new ReturnValueException( evalExpression( line.substring( "return ".length() ) ) );
                } else if ( ( matcher = functionPattern.matcher( line ) ).matches() ) {
                    parseFunction( matcher.group( 1 ), matcher.group( 2 ) );
                } else if ( ( matcher = suffixFunctionPattern.matcher( line ) ).matches() ) {
                    Object oldValue = this.scopedDefinedVariables.get( matcher.group( 1 ) );
                    this.scopedDefinedVariables.put( matcher.group( 1 ), executeFunction( matcher.group( 2 ), oldValue, evalExpression( matcher.group( 3 ) ) ) );
                } else if ( ( matcher = incrementFunctionPattern.matcher( line ) ).matches() ) {
                    Object oldValue = this.scopedDefinedVariables.get( matcher.group( 1 ) );
                    this.scopedDefinedVariables.put( matcher.group( 1 ), executeFunction( matcher.group( 2 ).substring( 0, 1 ), oldValue, 1 ) );
                } else if ( ( matcher = this.forEachPattern.matcher( line ) ).matches() ) {
                    parseForEachBlock( matcher.group( 1 ), Types.ensureType( evalExpression( matcher.group( 2 ) ), Iterable.class, false ) );
                } else if ( ( matcher = this.forPattern.matcher( line ) ).matches() ) {
                    if ( matcher.group( 2 ) != null ) { // with step
                        parseForBlock( matcher.group( 1 ), Types.ensureType( evalExpression( matcher.group( 3 ) ), Number.class, false ).intValue(),
                                Types.ensureType( evalExpression( matcher.group( 4 ) ), Number.class, false ).intValue(),
                                Types.ensureType( evalExpression( matcher.group( 5 ) ), Number.class, false ).intValue() );
                    } else {
                        parseForBlock( matcher.group( 1 ), 1,
                                Types.ensureType( evalExpression( matcher.group( 4 ) ), Number.class, false ).intValue(),
                                Types.ensureType( evalExpression( matcher.group( 5 ) ), Number.class, false ).intValue() );
                    }
                } else {
                    evalExpression( line );
                }
            }

        }
    }

    private String[] parseArgumentNames( String raw ) throws ScriptException {
        String[] split = raw.split( "," );
        String[] o = new String[split.length];
        int i = 0;
        for ( String s : split ) {
            s = s.trim();
            if ( s.isEmpty() || !StringChecker.isCorrectVariableName( s ) ) {
                throw new ScriptException( "Invalid variable name " + s );
            }
            o[i] = s;
            i++;
        }
        return o;
    }

    private void parseFunction( String functionName, String rawArguments ) throws ScriptException {
        if ( this.functionManager.isFunctionPresent( functionName ) ) {
            throw new ScriptException( "Function " + functionName + " is already registered" );
        }
        functionName = functionName.toLowerCase();
        String[] argumentNames = parseArgumentNames( rawArguments );
        List<String> scanned = new LinkedList<>();
        while ( this.scanner.hasNextLine() ) {
            String line = this.scanner.nextLine();
            if ( line.trim().toLowerCase().equals( "end function" ) ) {
                this.functionManager.register( new UserDefinedFunction( functionName, scanned.toArray( new String[0] ), argumentNames, functionManager.getFunctions() ), functionName );
                return;
            } else {
                scanned.add( line );
            }
        }
        throw new ScriptException( "Unexpected end of file, expected 'end function'" );
    }

    public Map<String, Object> getVariables() {
        return this.scopedDefinedVariables;
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
        if ( s.equalsIgnoreCase( "true" ) ) {
            return Boolean.TRUE;
        }
        if ( s.equalsIgnoreCase( "false" ) ) {
            return Boolean.FALSE;
        }
        if ( s.equalsIgnoreCase( "null" ) || s.equalsIgnoreCase( "nil" ) || s.equalsIgnoreCase( "undefined" ) ) {
            return null;
        }

        if ( s.startsWith( "\"" ) && s.endsWith( "\"" ) ) {
            return s.length() == 2 ? "" : s.substring( 1, s.length() - 1 );
        }

        for ( Map.Entry<Pattern, ScriptFunction<Matcher, Object>> entry : this.patterns.entrySet() ) {
            Matcher matcher = entry.getKey().matcher( s );
            if ( matcher.matches() ) {
                return entry.getValue().apply( matcher );
            }
        }

        if ( scopedDefinedVariables.containsKey( s ) ) {
            return scopedDefinedVariables.get( s );
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
