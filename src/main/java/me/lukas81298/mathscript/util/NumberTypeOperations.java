package me.lukas81298.mathscript.util;

import me.lukas81298.mathscript.parser.ScriptException;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class NumberTypeOperations {

    public static Number addNumbers( Number a, Number b ) throws ScriptException {
        if( a instanceof Long ) {
            if ( b instanceof Long ) {
                return a.longValue() + b.longValue();
            }
            if ( b instanceof Integer ) {
                return a.longValue() + b.intValue();
            }
            if ( b instanceof Double ) {
                return a.longValue() + b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.longValue() + b.floatValue();
            }
        } else if( a instanceof Integer ) {
            if ( b instanceof Integer ) {
                return a.intValue() + b.intValue();
            }
            if ( b instanceof Long ) {
                return a.intValue() + b.longValue();
            }
            if ( b instanceof Double ) {
                return a.intValue() + b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.intValue() + b.floatValue();
            }
        } else if( a instanceof Double ) {
            if ( b instanceof Double ) {
                return a.doubleValue() + b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.doubleValue() + b.floatValue();
            }
            if ( b instanceof Integer ) {
                return a.doubleValue() + b.intValue();
            }
            if ( b instanceof Long ) {
                return a.doubleValue() + b.longValue();
            }
        } else if( a instanceof Float ) {
            if ( b instanceof Float ) {
                return a.floatValue() + b.floatValue();
            }
            if( b instanceof Double ) {
                return a.floatValue() + b.doubleValue();
            }
            if( b instanceof Long ) {
                return a.floatValue() + b.longValue();
            }
            if( b instanceof Integer ) {
                return a.floatValue() + b.intValue();
            }
        }

        return a.doubleValue() + b.doubleValue();
    }
    
    public static Number substractNumbers( Number a, Number b ) throws ScriptException {
        if( a instanceof Long ) {
            if ( b instanceof Long ) {
                return a.longValue() - b.longValue();
            }
            if ( b instanceof Integer ) {
                return a.longValue() - b.intValue();
            }
            if ( b instanceof Double ) {
                return a.longValue() - b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.longValue() - b.floatValue();
            }
        } else if( a instanceof Integer ) {
            if ( b instanceof Integer ) {
                return a.intValue() - b.intValue();
            }
            if ( b instanceof Long ) {
                return a.intValue() - b.longValue();
            }
            if ( b instanceof Double ) {
                return a.intValue() - b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.intValue() - b.floatValue();
            }
        } else if( a instanceof Double ) {
            if ( b instanceof Double ) {
                return a.doubleValue() - b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.doubleValue() - b.floatValue();
            }
            if ( b instanceof Integer ) {
                return a.doubleValue() - b.intValue();
            }
            if ( b instanceof Long ) {
                return a.doubleValue() - b.longValue();
            }
        } else if( a instanceof Float ) {
            if ( b instanceof Float ) {
                return a.floatValue() - b.floatValue();
            }
            if( b instanceof Double ) {
                return a.floatValue() - b.doubleValue();
            }
            if( b instanceof Long ) {
                return a.floatValue() - b.longValue();
            }
            if( b instanceof Integer ) {
                return a.floatValue() - b.intValue();
            }
        }

        return a.doubleValue() - b.doubleValue();
    }
    
    public static Number multiplyNumbers( Number a, Number b ) {
        if( a instanceof Long ) {
            if ( b instanceof Long ) {
                return a.longValue() * b.longValue();
            }
            if ( b instanceof Integer ) {
                return a.longValue() * b.intValue();
            }
            if ( b instanceof Double ) {
                return a.longValue() * b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.longValue() * b.floatValue();
            }
        } else if( a instanceof Integer ) {
            if ( b instanceof Integer ) {
                return a.intValue() * b.intValue();
            }
            if ( b instanceof Long ) {
                return a.intValue() * b.longValue();
            }
            if ( b instanceof Double ) {
                return a.intValue() * b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.intValue() * b.floatValue();
            }
        } else if( a instanceof Double ) {
            if ( b instanceof Double ) {
                return a.doubleValue() * b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.doubleValue() * b.floatValue();
            }
            if ( b instanceof Integer ) {
                return a.doubleValue() * b.intValue();
            }
            if ( b instanceof Long ) {
                return a.doubleValue() * b.longValue();
            }
        } else if( a instanceof Float ) {
            if ( b instanceof Float ) {
                return a.floatValue() * b.floatValue();
            }
            if( b instanceof Double ) {
                return a.floatValue() * b.doubleValue();
            }
            if( b instanceof Long ) {
                return a.floatValue() * b.longValue();
            }
            if( b instanceof Integer ) {
                return a.floatValue() * b.intValue();
            }
        }

        return a.doubleValue() * b.doubleValue();
    }
    
    public static boolean isZero(Number b) {
        if(b instanceof Long) {
            return b.longValue() == 0;
        } else if(b instanceof Double) {
            return b.doubleValue() == 0;
        } else if(b instanceof Float) {
            return b.floatValue() == 0;
        } else {
            return b.intValue() == 0;
        }
    }
    
    public static Number divideNumbers( Number a, Number b ) throws ScriptException {
        if(isZero( b )) {
            throw new ScriptException( "Cannot divide by 0, Tried to divide " + a + "/" + b );
        }
        if( a instanceof Long ) {
            if ( b instanceof Long ) {
                return a.longValue() / b.longValue();
            }
            if ( b instanceof Integer ) {
                return a.longValue() / b.intValue();
            }
            if ( b instanceof Double ) {
                return a.longValue() / b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.longValue() / b.floatValue();
            }
        } else if( a instanceof Integer ) {
            if ( b instanceof Integer ) {
                return a.intValue() / b.intValue();
            }
            if ( b instanceof Long ) {
                return a.intValue() / b.longValue();
            }
            if ( b instanceof Double ) {
                return a.intValue() / b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.intValue() / b.floatValue();
            }
        } else if( a instanceof Double ) {
            if ( b instanceof Double ) {
                return a.doubleValue() / b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.doubleValue() / b.floatValue();
            }
            if ( b instanceof Integer ) {
                return a.doubleValue() / b.intValue();
            }
            if ( b instanceof Long ) {
                return a.doubleValue() / b.longValue();
            }
        } else if( a instanceof Float ) {
            if ( b instanceof Float ) {
                return a.floatValue() / b.floatValue();
            }
            if( b instanceof Double ) {
                return a.floatValue() / b.doubleValue();
            }
            if( b instanceof Long ) {
                return a.floatValue() / b.longValue();
            }
            if( b instanceof Integer ) {
                return a.floatValue() / b.intValue();
            }
        }

        return a.doubleValue() / b.doubleValue();
    }
    
    public static Number modNumbers( Number a, Number b ) throws ScriptException {
        if(isZero( b )) {
            throw new ScriptException( "Cannot modulo by 0, Tried to calculate " + a + "%" + b );
        }
        if( a instanceof Long ) {
            if ( b instanceof Long ) {
                return a.longValue() % b.longValue();
            }
            if ( b instanceof Integer ) {
                return a.longValue() % b.intValue();
            }
            if ( b instanceof Double ) {
                return a.longValue() % b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.longValue() % b.floatValue();
            }
        } else if( a instanceof Integer ) {
            if ( b instanceof Integer ) {
                return a.intValue() % b.intValue();
            }
            if ( b instanceof Long ) {
                return a.intValue() % b.longValue();
            }
            if ( b instanceof Double ) {
                return a.intValue() % b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.intValue() % b.floatValue();
            }
        } else if( a instanceof Double ) {
            if ( b instanceof Double ) {
                return a.doubleValue() % b.doubleValue();
            }
            if ( b instanceof Float ) {
                return a.doubleValue() % b.floatValue();
            }
            if ( b instanceof Integer ) {
                return a.doubleValue() % b.intValue();
            }
            if ( b instanceof Long ) {
                return a.doubleValue() % b.longValue();
            }
        } else if( a instanceof Float ) {
            if ( b instanceof Float ) {
                return a.floatValue() % b.floatValue();
            }
            if( b instanceof Double ) {
                return a.floatValue() % b.doubleValue();
            }
            if( b instanceof Long ) {
                return a.floatValue() % b.longValue();
            }
            if( b instanceof Integer ) {
                return a.floatValue() % b.intValue();
            }
        }

        return a.doubleValue() % b.doubleValue();
    }
    
}
