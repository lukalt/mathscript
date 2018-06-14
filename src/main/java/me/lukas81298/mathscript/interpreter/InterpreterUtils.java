package me.lukas81298.mathscript.interpreter;

import java.util.ArrayList;
import java.util.List;

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

    /*
     * Parse manually as this language is not regular so we cannot split at the ,. This would cause f(a,g(b,c),d) to be split into [a,g(b,c),d)] which is not correct
     */
    public static String[] splitRealArguments( String input ) {
        List<String> out = new ArrayList<>();
        StringBuilder currentBuffer = new StringBuilder();

        final char[] chars = input.toCharArray();
        int i = 0;
        int toClose1 = 0, toClose2 = 0, toClose3 = 0;
        while ( i < chars.length ) {
            char c = chars[i];
            if( c == ',' && toClose1 == 0 && toClose2 == 0 && toClose3 == 0) {
                out.add( currentBuffer.toString() );
                currentBuffer.setLength( 0 ); // clear the string builder
            } else {
                switch ( c ) {
                    case '(':
                      //  System.out.println( "toClose++" );
                        toClose1++; break;
                    case '[':
                        toClose2++; break;
                    case '{':
                        toClose3++; break;
                    case ')':
                       // System.out.println( "toClose--" );
                        toClose1--; break;
                    case ']':
                        toClose2--; break;
                    case '}':
                        toClose3--; break;
                }
                currentBuffer.append( c );
            }
            i++;
        }
        out.add( currentBuffer.toString() );

        String[] outArray = new String[out.size()];
        for ( int j = 0; j < out.size(); j++ ) {
            outArray[j] = out.get( j );
        }
        return outArray;
    }


}
