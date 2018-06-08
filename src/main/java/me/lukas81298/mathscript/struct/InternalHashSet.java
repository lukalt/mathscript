package me.lukas81298.mathscript.struct;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class InternalHashSet<E> extends HashSet<E> {

    @Override
    public String toString() {
        boolean b = false;
        StringBuilder stringBuilder = new StringBuilder( "{" );
        for ( Object datum : this ) {
            if ( b ) {
                stringBuilder.append( "," );
            } else {
                b = true;
            }
            stringBuilder.append( Objects.toString( datum ) );
        }
        stringBuilder.append( "}" );
        return stringBuilder.toString();
    }

}
