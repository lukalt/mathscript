package me.lukas81298.mathscript.parser;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.function.FunctionManager;
import me.lukas81298.mathscript.struct.InternalArrayList;
import me.lukas81298.mathscript.struct.InternalHashSet;
import me.lukas81298.mathscript.struct.Tuple;
import me.lukas81298.mathscript.util.ScriptFunction;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ScriptExecutor {

    private final Scanner scanner;
    private final Pattern letPattern = Pattern.compile( "(let|var|define) ([A-Za-z_][A-Za-z0-1_]{0,127}) *= *(.+)" );
    private final Pattern ifPattern = Pattern.compile( "if ?(.*)" );

    private final Map<Pattern, ScriptFunction<Matcher, Object>> patterns = new LinkedHashMap<>();

    private final FunctionManager functionManager = new FunctionManager();

    private final Map<String, Object> scopedDefinedVariables = new HashMap<>();

    public ScriptExecutor( InputStream inputStream ) {
        this.scanner = new Scanner( inputStream );

        this.registerPattern( "\\[(.*)\\]", new ScriptFunction<Matcher, Object>() {
            @Override
            public Object apply( Matcher matcher ) throws ScriptException {
                String elements = matcher.group( 1 );
                final String[] split = elements.split( "," );
                List<Object> list = new InternalArrayList<>();
                for ( String s : split ) {
                    list.add( parseExpression( s ) );
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
                        argsTypes[i] = parseExpression( argsRaw[i] );
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
                    tuple.set( i, parseExpression( split[i] ) );
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
                    set.add( parseExpression( s ) );
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
                return executeFunction( op, parseExpression( left ), parseExpression( right ) );
            }
        } );

    }

    private void registerPattern( String pattern, ScriptFunction<Matcher, Object> function ) {
        this.patterns.put( Pattern.compile( pattern ), function );
    }

    public void parse() throws ScriptException {
        this.parse( State.NORMAL );
    }

    private void parse( State state ) throws ScriptException {
        if ( !scanner.hasNextLine() ) {
            return;
        }
        String line = scanner.nextLine().trim();
        int firstComment = line.indexOf( "//" );
        if ( firstComment >= 0 ) {
            line = line.substring( 0, firstComment ).trim();
        }
        if ( !line.isEmpty() ) {
            if ( line.equalsIgnoreCase( "endif" ) || line.equalsIgnoreCase( "fi" ) ) {
                state = State.NORMAL;
            } else if ( state == State.IF && line.equalsIgnoreCase( "else" ) ) {
                String l = scanner.nextLine().trim();
                while ( !( l.equalsIgnoreCase( "endif" ) || l.equalsIgnoreCase( "fi" ) ) ) {
                    l = scanner.nextLine().trim();
                }
            } else {
                Matcher matcher = letPattern.matcher( line );
                if ( matcher.find() ) {
                    String varName = matcher.group( 2 );
                    Object value = parseExpression( matcher.group( 3 ) );
                    scopedDefinedVariables.put( varName, value );
                } else {
                    Matcher ifMatched = ifPattern.matcher( line );
                    if ( ifMatched.matches() ) {
                        if ( Objects.equals( true, parseExpression( ifMatched.group( 1 ) ) ) ) {
                            state = State.IF;
                        } else {
                            String nextLine = scanner.nextLine().trim();
                            while ( !nextLine.equalsIgnoreCase( "else" ) && !nextLine.equalsIgnoreCase( "endif" ) && !nextLine.equalsIgnoreCase( "fi" ) ) {
                                nextLine = scanner.nextLine().trim();
                            }
                            if ( nextLine.equalsIgnoreCase( "else" ) ) {
                                state = State.ELSE;
                            } else {
                                state = State.NORMAL;
                            }
                        }
                    } else {
                        parseExpression( line );
                    }
                }
            }
        }

        this.parse( state );
    }

    public Map<String, Object> getVariables() {
        return this.scopedDefinedVariables;
    }

    private Object executeFunction( String name, Object... args ) throws ScriptException {
        return this.functionManager.executeFunction( this, name, args );
    }

    public Object parseExpression( String s ) throws ScriptException {
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

        for ( Map.Entry<Pattern, ScriptFunction<Matcher, Object>> entry : this.patterns.entrySet() ) {
            Matcher matcher = entry.getKey().matcher( s );
            if ( matcher.matches() ) {
                return entry.getValue().apply( matcher );
            }
        }

        if ( s.startsWith( "\"" ) && s.endsWith( "\"" ) ) {
            return s.length() == 2 ? "" : s.substring( 1, s.length() - 1 );
        }


        if ( scopedDefinedVariables.containsKey( s ) ) {
            return scopedDefinedVariables.get( s );
        }

        if ( s.endsWith( "!" ) ) {
            s = s.substring( 0, s.length() - 1 );
            return executeFunction( "fac", parseExpression( s ) );
        }
        if(s.startsWith( "!" )) {
            s = s.substring( 1 );
            return executeFunction( "neg", parseExpression( s ) );
        }

        throw new ScriptException( "Invalid statement or undefined variable " + s );
    }

}
