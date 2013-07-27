package com.lonepulse.packrat.util;

/*
 * #%L
 * Packrat
 * %%
 * Copyright (C) 2013 Lonepulse
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.Collection;
import java.util.Map;

/**
 * <p>Provides some common operations in processing method parameters.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class ParamUtils {

	/**
	 * <p>Constructor visibility restricted; instantiation is nonsensical.
	 * 
	 * @since 1.1.0
	 */
	private ParamUtils() {}
	
	/**
	 * <p>Throws an {@link IllegalArgumentException} if the provided <i>instance</i> 
	 * argument is {@code null}. The exception message will contain the type of the 
	 * parameter which was {@code null}.
	 * 
	 * @param type
	 * 			the {@link Class} of the argument to check for {@code null} conditions
	 * 
	 * @param instance
	 * 			the argument to check for {@code null} conditions
	 * 
	 * @throws IllegalArgumentException
	 * 			if the supplied instance argument is null
	 * 
	 * @since 1.1.0
	 */
	public static final <T> void throwIfNull(Class<T> type, T instance) {
		
		if(instance == null) {
			
			StringBuilder builder = new StringBuilder()
			.append("The supplied ")
			.append(type.getName())
			.append(" parameter was null. ");
			
			throw new IllegalArgumentException(builder.toString());
		}
	}
	
	/**
	 * <p>Throws an {@link IllegalArgumentException} if the provided <i>instance</i> 
	 * argument is {@code null} or empty.</p>
	 * 
	 * <p><b>Note that multidimensional arrays are not covered.</b></p>
	 * 
	 * @param type
	 * 			the {@link Class} of the argument to check for {@code null} conditions
	 * 
	 * @param instance
	 * 			the argument to check for {@code null} conditions
	 * 
	 * @throws IllegalArgumentException
	 * 			if the supplied instance argument is null
	 * 
	 * @since 1.1.0
	 */
	public static final <T> void throwIfNullOrEmpty(Object manyOfSomething) {
		
		throwIfNull(manyOfSomething);
		
		if(manyOfSomething instanceof CharSequence) {
			
			if(((CharSequence)manyOfSomething).toString().isEmpty())
				throw new IllegalArgumentException("The supplied CharSequence parameter is empty. ");
		}
		else if(manyOfSomething instanceof Collection<?>) {
			
			if(((Collection<?>)manyOfSomething).isEmpty())
				throw new IllegalArgumentException("The supplied Collection parameter is empty. ");
		}
		else if(manyOfSomething instanceof Map<?, ?>) {
			
			if(((Map<?, ?>)manyOfSomething).entrySet().isEmpty())
				throw new IllegalArgumentException("The supplied Map parameter has no entries. ");
		}
		else if(manyOfSomething instanceof char[]) {
			
			if(((char[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied char array parameter is empty. ");
		}
		else if(manyOfSomething instanceof byte[]) {
			
			if(((byte[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied byte array parameter is empty. ");
		}
		else if(manyOfSomething instanceof short[]) {
			
			if(((short[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied short array parameter is empty. ");
		}
		else if(manyOfSomething instanceof int[]) {
			
			if(((int[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied int array parameter is empty. ");
		}
		else if(manyOfSomething instanceof long[]) {
			
			if(((long[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied long array parameter is empty. ");
		}
		else if(manyOfSomething instanceof float[]) {
			
			if(((float[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied float array parameter is empty. ");
		}
		else if(manyOfSomething instanceof double[]) {
			
			if(((double[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied double array parameter is empty. ");
		}
		else if(manyOfSomething instanceof boolean[]) {
			
			if(((boolean[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied boolean array parameter is empty. ");
		}
		else if(manyOfSomething instanceof Object[]) {
			
			if(((Object[])manyOfSomething).length == 0)
				throw new IllegalArgumentException("The supplied Object array parameter is empty. ");
		}
	}
	
	/**
	 * <p>Throws an {@link IllegalArgumentException} if the provided <i>instance</i> 
	 * argument is {@code null}. 
	 * 
	 * @param type
	 * 			the {@link Class} of the argument to check for {@code null} conditions
	 * 
	 * @param instance
	 * 			the argument to check for {@code null} conditions
	 * 
	 * @throws IllegalArgumentException
	 * 			if the supplied instance argument is null
	 * 
	 * @since 1.1.0
	 */
	public static final <T> void throwIfNull(T instance) {
		
		if(instance == null) {
			
			throw new IllegalArgumentException("The supplied parameter was null. ");
		}
	}
}
