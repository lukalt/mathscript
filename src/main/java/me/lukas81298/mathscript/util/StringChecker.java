package me.lukas81298.mathscript.util;

import com.google.common.base.CharMatcher;

/**
 * @author lukas
 * @since 10.06.2018
 */
public class StringChecker {

    public static boolean isCorrectVariableName( String input ) {
        return CharMatcher.javaLetterOrDigit().or( CharMatcher.is( '_' ) ).matchesAllOf( input );
    }

}
