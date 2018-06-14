package me.lukas81298.mathscript.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 14.06.2018
 */
public class MapBuilder<K, V> {

    private final Map<K, V> delegate = new HashMap<>();

    public static <K,V> MapBuilder<K, V> create(K k, V v) {
        return new MapBuilder<K, V>().map( k, v );
    }

    public MapBuilder<K, V> map(K k, V v) {
        delegate.put( k, v );
        return this;
    }

    public Map<K, V> build() {
        return this.delegate;
    }
}
