package me.lukas81298.mathscript.struct;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class InternalTuple implements Iterable {

    private final Object[] data;

    public InternalTuple( int length ) {
        this.data = new Object[length];
    }

    public Object get( int i ) {
        return data[i];
    }

    public void set( int i, Object o ) {
        this.data[i] = o;
    }

    public int length() {
        return this.data.length;
    }

    @Override
    public String toString() {
        boolean b = false;
        StringBuilder stringBuilder = new StringBuilder( "(" );
        for ( Object datum : this.data ) {
            if ( b ) {
                stringBuilder.append( "," );
            } else {
                b = true;
            }
            stringBuilder.append( Objects.toString( datum ) );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return Arrays.asList( this.data ).iterator();
    }

}
