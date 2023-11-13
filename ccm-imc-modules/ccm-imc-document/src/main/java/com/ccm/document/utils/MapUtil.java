

package com.ccm.document.utils;


import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;

/**
 * Map utils.
 *
 * @author kekai.huang
 */
public class MapUtil {
    
    /**
     * Null-safe check if the specified Dictionary is empty.
     *
     * <p>Null returns true.
     *
     * @param map the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }
    
    /**
     * Null-safe check if the specified Dictionary is empty.
     *
     * <p>Null returns true.
     *
     * @param coll the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Dictionary coll) {
        return (coll == null || coll.isEmpty());
    }
    
    /**
     * Null-safe check if the specified Dictionary is not empty.
     *
     * <p>Null returns false.
     *
     * @param map the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
    
    /**
     * Null-safe check if the specified Dictionary is not empty.
     *
     * <p>Null returns false.
     *
     * @param coll the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Dictionary coll) {
        return !isEmpty(coll);
    }
    
    /**
     * Put into map if value is not null.
     *
     * @param target target map
     * @param key    key
     * @param value  value
     */
    public static void putIfValNoNull(Map target, Object key, Object value) {
        Objects.requireNonNull(key, "key");
        if (value != null) {
            target.put(key, value);
        }
    }

    /**
     * ComputeIfAbsent lazy load.
     *
     * @param target          target Map data.
     * @param key             map key.
     * @param mappingFunction function which is need to be executed.
     * @param param1          function's parameter value1.
     * @param param2          function's parameter value1.
     * @return
     */
    public static <K, C, V, T> V computeIfAbsent(Map<K, V> target, K key, BiFunction<C, T, V> mappingFunction, C param1,
                                                 T param2) {

        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        Objects.requireNonNull(param1, "param1");
        Objects.requireNonNull(param2, "param2");

        V val = target.get(key);
        if (val == null) {
            V ret = mappingFunction.apply(param1, param2);
            target.put(key, ret);
            return ret;
        }
        return val;
    }
}
