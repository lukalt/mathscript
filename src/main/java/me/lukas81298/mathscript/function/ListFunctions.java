package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.interpreter.ScriptException;
import me.lukas81298.mathscript.interpreter.BaseInterpreter;
import me.lukas81298.mathscript.struct.InternalArrayList;

import java.util.*;

/**
 * @author lukas
 * @since 08.06.2018
 */
@SuppressWarnings( "unchecked" )
public class ListFunctions {

    public static class ListFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) {
            return new InternalArrayList<>( Arrays.asList( arguments ) );
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }
    }

    public static class ListPopFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            if(list.isEmpty()) {
                return null;
            }
            return list.remove( 0 );
        }

        @Override
        public Class<?> mapsTo() {
            return Object.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static class ListContainsFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            Object o = Types.ensureNotNull( arguments[1] );
            return list.contains( o );
        }

        @Override
        public Class<?> mapsTo() {
            return Object.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public static class ListGetFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            int index = Types.ensureType( arguments[0], Number.class, false ).intValue();
            return list.get( index );
        }

        @Override
        public Class<?> mapsTo() {
            return Object.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public static class ListAddFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            list.add( arguments[1] );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public static class ListSortFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            Collections.sort( list );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static class ListCopyFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            List out = new InternalArrayList( list );
            return out;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static class ListDSortFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            Collections.sort( list );
            Collections.reverse( list );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static final class ListReverseFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            Collections.reverse( list );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static class ListShuffleFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            Collections.shuffle( list );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

    public static class ListRemoveFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List<Object> list = Types.ensureType( arguments[0], List.class, false );
            list.remove( arguments[1] );
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public static class ListClearFunction implements Function {

        @Override
        public Object execute( BaseInterpreter env, Object... arguments ) throws ScriptException {
            List list = Types.ensureType( arguments[0], List.class, false );
            list.clear();
            return list;
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 1;
        }
    }

}
