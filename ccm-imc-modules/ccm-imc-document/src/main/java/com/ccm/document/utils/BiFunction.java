

package com.ccm.document.utils;

/**
 * Represents a function that accepts two arguments and produces a result. The following utility functions are extracted
 * from org.apache.commons.lang3.
 *
 * @author kekai.huang
 */
public interface BiFunction<T, U, R> {
    
    /**
     * Applies this function to the two given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(T t, U u);
}
