package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;
import me.lukas81298.mathscript.struct.InternalArrayList;

import java.util.*;

/**
 * @author lukas
 * @since 08.06.2018
 */
public class ListFunctions {

    public static class ListFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) {
            return new InternalArrayList<>( Arrays.asList( arguments ) );
        }

        @Override
        public Class<?> mapsTo() {
            return List.class;
        }
    }

    public static class ListAddFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            List<Object> list = Types.ensureType( arguments[0], List.class, false );
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
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
