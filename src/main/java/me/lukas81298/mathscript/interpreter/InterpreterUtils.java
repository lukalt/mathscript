package me.lukas81298.mathscript.interpreter;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class InterpreterUtils {

    public static String validateStringCandidate( String s ) throws ScriptException {
        if( s.length() == 1 ) { // something we cannot parse
            throw new ScriptException( "Cannot parse a single \"" );
        }
        if( s.length() == 2 ) {
            return ""; // empty string
        }
        char lastChar = ' ';
        int i = 1;
        char[] asArray = s.toCharArray();
        boolean isValidString = true;
        while ( i < s.length() - 1 ) {
            char next = asArray[i];
            if(next == '"' && lastChar != '\\') {
                isValidString = false;
                break;
            }
            lastChar = next;
            i++;
        }
        if( isValidString ) {
            return s.substring( 1, s.length() - 1 ).replace( "\\\"","\"" );
        }
        return null;
    }

    public static String stripComment( String line ) {
        int firstComment = line.indexOf( "//" );
        if ( firstComment >= 0 ) {
            line = line.substring( 0, firstComment ).trim();
        }
        return line;
    }


}
