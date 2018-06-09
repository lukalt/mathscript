package me.lukas81298.mathscript;

import me.lukas81298.mathscript.parser.ScriptException;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class Types {

    public static <T> T ensureType( Object object, Class<T> expected ) throws ScriptException {
        return ensureType( object, expected, true );
    }

    public static <T> T ensureType( Object object, Class<T> expected, boolean allowNull ) throws ScriptException {
        if ( object == null ) {
            if ( allowNull ) {
                return null;
            } else {
                throw new ScriptException( "Expected " + expected.getSimpleName() + " but got null" );
            }
        }
        if ( !expected.isInstance( object ) ) {
            throw new ScriptException( "Unexpected type " + object.getClass().getSimpleName() + ", expected " + expected.getSimpleName() );
        }
        return expected.cast( object );
    }

    public static <T> T ensureNotNull( T t ) throws ScriptException {
        if( t == null ) {
            throw new ScriptException( "Argument cannot be null" );
        }
        return t;
    }

    public static Number checkIfNumber( String s ) {
        s = s.trim();
        if ( s.length() > 1 ) {
            if ( s.endsWith( "L" ) || s.endsWith( "l" ) ) {
                try {
                    return Long.parseLong( s.substring( 0, s.length() - 1 ) );
                } catch ( NumberFormatException ignored ) {
                    return null;
                }
            } else if ( s.endsWith( "F" ) || s.endsWith( "f" ) ) {
                try {
                    return Float.parseFloat( s.substring( 0, s.length() - 1 ) );
                } catch ( NumberFormatException ignored ) {
                    return null;
                }
            } else if ( s.endsWith( "D" ) || s.endsWith( "d" ) || s.contains( "." ) ) {
                try {
                    String substring = s.substring( 0, s.endsWith( "D" ) || s.endsWith( "d" ) ? (s.length() - 1) : s.length() );
                    if( substring.startsWith( "." ) ) {
                        substring = "0" + substring;
                    }
                    return Double.parseDouble( substring );
                } catch ( NumberFormatException ignored ) {
                    return null;
                }
            }
        }
        try {
            return Integer.parseInt( s );
        } catch ( NumberFormatException ignored ) {
            return null;
        }
    }

}

