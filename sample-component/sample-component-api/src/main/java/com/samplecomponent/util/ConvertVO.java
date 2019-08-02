package com.samplecomponent.util;

/**
 * Interface that all services should implements
 *  
 * @param <E>
 * @param <R>
 */
public interface ConvertVO<E, R> {
	
	R convert(E entity);
}
