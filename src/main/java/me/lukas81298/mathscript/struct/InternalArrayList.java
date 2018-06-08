package me.lukas81298.mathscript.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author lukas
 * @since 08.06.2018
 */
public final class InternalArrayList<E> extends ArrayList<E> {

    public InternalArrayList() {
    }

    public InternalArrayList( Collection<E> c ) {
        super( c );
    }

    @Override
    public String toString() {
        boolean b = false;
        StringBuilder stringBuilder = new StringBuilder( "[" );
        for ( Object datum : this ) {
            if ( b ) {
                stringBuilder.append( "," );
            } else {
                b = true;
            }
            stringBuilder.append( Objects.toString( datum ) );
        }
        stringBuilder.append( "]" );
        return stringBuilder.toString();
    }
}