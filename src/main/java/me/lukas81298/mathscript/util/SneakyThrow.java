package me.lukas81298.mathscript.util;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class SneakyThrow {

    public static <T> T throwSneaky( Throwable t ) {
        throw new RuntimeException( t );
    }
}
