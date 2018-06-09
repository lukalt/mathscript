package me.lukas81298.mathscript.function;

import me.lukas81298.mathscript.Types;
import me.lukas81298.mathscript.parser.ScriptException;
import me.lukas81298.mathscript.parser.ScriptExecutor;

import java.util.Objects;

/**
 * @author lukas
 * @since 09.06.2018
 */
public class CompareInfixFunctions {

    public final static class LowerEqFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return Types.ensureType( arguments[0], Number.class, false ).doubleValue() <= Types.ensureType( arguments[1], Number.class, false ).doubleValue();
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }

    }

    public final static class GreaterEqFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return Types.ensureType( arguments[0], Number.class, false ).doubleValue() >= Types.ensureType( arguments[1], Number.class, false ).doubleValue();
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }

    }

    public final static class LowerFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return Types.ensureType( arguments[0], Number.class, false ).doubleValue() < Types.ensureType( arguments[1], Number.class, false ).doubleValue();
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }

    }

    public final static class GreaterFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) throws ScriptException {
            return Types.ensureType( arguments[0], Number.class, false ).doubleValue() > Types.ensureType( arguments[1], Number.class, false ).doubleValue();
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }

    }

    public final static class NotEqualsFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) {
            Object o1 = arguments[0];
            Object o2 = arguments[1];
            return !Objects.equals( o1, o2 );
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }

    public final static class EqualsFunction implements Function {

        @Override
        public Object execute( ScriptExecutor env, Object... arguments ) {
            Object o1 = arguments[0];
            Object o2 = arguments[1];
            return Objects.equals( o1, o2 );
        }

        @Override
        public Class<?> mapsTo() {
            return Boolean.class;
        }

        @Override
        public boolean acceptsArgumentLength( int i ) {
            return i == 2;
        }
    }
}
