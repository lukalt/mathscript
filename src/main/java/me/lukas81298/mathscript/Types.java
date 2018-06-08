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

    public static Number addNumbers( Number a, Number b ) {

        if ( a instanceof Long && b instanceof Long ) {
            return a.longValue() + b.longValue();
        }
        if ( a instanceof Long && b instanceof Integer ) {
            return a.longValue() + b.intValue();
        }
        if( a instanceof Long && b instanceof Double ) {
            return a.longValue() + b.doubleValue();
        }
        if( a instanceof Long && b instanceof Float ) {
            return a.longValue() + b.floatValue();
        }

        if ( a instanceof Integer && b instanceof Integer ) {
            return a.intValue() + b.intValue();
        }
        if( a instanceof Integer && b instanceof Long ) {
            return a.intValue() + b.longValue();
        }
        if( a instanceof Integer && b instanceof Double ) {
            return a.intValue() + b.doubleValue();
        }
        if( a instanceof Integer && b instanceof Float ) {
            return a.intValue() + b.floatValue();
        }

        if ( a instanceof Double && b instanceof Double ) {
            return a.doubleValue() + b.doubleValue();
        }
        if( a instanceof Double && b instanceof Float ) {
            return a.doubleValue() + b.floatValue();
        }
        if( a instanceof Double && b instanceof Integer )  {
            return a.doubleValue() + b.intValue();
        }
        if( a instanceof Double && b instanceof Long ) {
            return a.doubleValue() + b.longValue();
        }

        if ( a instanceof Float && b instanceof Float ) {
            return a.floatValue() + b.floatValue();
        }
        if( a instanceof Float && b instanceof Double ) {
            return a.floatValue() + b.doubleValue();
        }
        if( a instanceof Float && b instanceof Long ) {
            return a.floatValue() + b.longValue();
        }
        if( a instanceof Float && b instanceof Integer ) {
            return a.floatValue() + b.intValue();
        }

        return a.doubleValue() + b.doubleValue();
    }
    
    public static Number substractNumbers( Number a, Number b ) {

        if ( a instanceof Long && b instanceof Long ) {
            return a.longValue() - b.longValue();
        }
        if ( a instanceof Long && b instanceof Integer ) {
            return a.longValue() - b.intValue();
        }
        if( a instanceof Long && b instanceof Double ) {
            return a.longValue() - b.doubleValue();
        }
        if( a instanceof Long && b instanceof Float ) {
            return a.longValue() - b.floatValue();
        }

        if ( a instanceof Integer && b instanceof Integer ) {
            return a.intValue() - b.intValue();
        }
        if( a instanceof Integer && b instanceof Long ) {
            return a.intValue() - b.longValue();
        }
        if( a instanceof Integer && b instanceof Double ) {
            return a.intValue() - b.doubleValue();
        }
        if( a instanceof Integer && b instanceof Float ) {
            return a.intValue() - b.floatValue();
        }

        if ( a instanceof Double && b instanceof Double ) {
            return a.doubleValue() - b.doubleValue();
        }
        if( a instanceof Double && b instanceof Float ) {
            return a.doubleValue() - b.floatValue();
        }
        if( a instanceof Double && b instanceof Integer )  {
            return a.doubleValue() - b.intValue();
        }
        if( a instanceof Double && b instanceof Long ) {
            return a.doubleValue() - b.longValue();
        }

        if ( a instanceof Float && b instanceof Float ) {
            return a.floatValue() - b.floatValue();
        }
        if( a instanceof Float && b instanceof Double ) {
            return a.floatValue() - b.doubleValue();
        }
        if( a instanceof Float && b instanceof Long ) {
            return a.floatValue() - b.longValue();
        }
        if( a instanceof Float && b instanceof Integer ) {
            return a.floatValue() - b.intValue();
        }

        return a.doubleValue() - b.doubleValue();
    }

    public static Number multiplyNumbers( Number a, Number b ) {

        if ( a instanceof Long && b instanceof Long ) {
            return a.longValue() * b.longValue();
        }
        if ( a instanceof Long && b instanceof Integer ) {
            return a.longValue() * b.intValue();
        }
        if( a instanceof Long && b instanceof Double ) {
            return a.longValue() * b.doubleValue();
        }
        if( a instanceof Long && b instanceof Float ) {
            return a.longValue() * b.floatValue();
        }

        if ( a instanceof Integer && b instanceof Integer ) {
            return a.intValue() * b.intValue();
        }
        if( a instanceof Integer && b instanceof Long ) {
            return a.intValue() * b.longValue();
        }
        if( a instanceof Integer && b instanceof Double ) {
            return a.intValue() * b.doubleValue();
        }
        if( a instanceof Integer && b instanceof Float ) {
            return a.intValue() * b.floatValue();
        }

        if ( a instanceof Double && b instanceof Double ) {
            return a.doubleValue() * b.doubleValue();
        }
        if( a instanceof Double && b instanceof Float ) {
            return a.doubleValue() * b.floatValue();
        }
        if( a instanceof Double && b instanceof Integer )  {
            return a.doubleValue() * b.intValue();
        }
        if( a instanceof Double && b instanceof Long ) {
            return a.doubleValue() * b.longValue();
        }

        if ( a instanceof Float && b instanceof Float ) {
            return a.floatValue() * b.floatValue();
        }
        if( a instanceof Float && b instanceof Double ) {
            return a.floatValue() * b.doubleValue();
        }
        if( a instanceof Float && b instanceof Long ) {
            return a.floatValue() * b.longValue();
        }
        if( a instanceof Float && b instanceof Integer ) {
            return a.floatValue() * b.intValue();
        }

        return a.doubleValue() * b.doubleValue();
    }
    
    public static Number divideNumbers( Number a, Number b ) {

        if ( a instanceof Long && b instanceof Long ) {
            return a.longValue() / b.longValue();
        }
        if ( a instanceof Long && b instanceof Integer ) {
            return a.longValue() / b.intValue();
        }
        if( a instanceof Long && b instanceof Double ) {
            return a.longValue() / b.doubleValue();
        }
        if( a instanceof Long && b instanceof Float ) {
            return a.longValue() / b.floatValue();
        }

        if ( a instanceof Integer && b instanceof Integer ) {
            return a.intValue() / b.intValue();
        }
        if( a instanceof Integer && b instanceof Long ) {
            return a.intValue() / b.longValue();
        }
        if( a instanceof Integer && b instanceof Double ) {
            return a.intValue() / b.doubleValue();
        }
        if( a instanceof Integer && b instanceof Float ) {
            return a.intValue() / b.floatValue();
        }

        if ( a instanceof Double && b instanceof Double ) {
            return a.doubleValue() / b.doubleValue();
        }
        if( a instanceof Double && b instanceof Float ) {
            return a.doubleValue() / b.floatValue();
        }
        if( a instanceof Double && b instanceof Integer )  {
            return a.doubleValue() / b.intValue();
        }
        if( a instanceof Double && b instanceof Long ) {
            return a.doubleValue() / b.longValue();
        }

        if ( a instanceof Float && b instanceof Float ) {
            return a.floatValue() / b.floatValue();
        }
        if( a instanceof Float && b instanceof Double ) {
            return a.floatValue() / b.doubleValue();
        }
        if( a instanceof Float && b instanceof Long ) {
            return a.floatValue() / b.longValue();
        }
        if( a instanceof Float && b instanceof Integer ) {
            return a.floatValue() / b.intValue();
        }

        return a.doubleValue() / b.doubleValue();
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

