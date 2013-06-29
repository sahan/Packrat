package com.lonepulse.packrat;

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

import com.lonepulse.packrat.PropertyReader.PROPERTY;

/**
 * <p>This {@link RuntimeException} is thrown when a given property 
 * is not found or cannot be read from the <b>ickle.orm.properties</b> file. 
 * 
 * @version 1.1.0
 * <br><br> 
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class PropertyReadException extends PackratRuntimeException {

	
	private static final long serialVersionUID = 2295492366218411918L;
	

	/**
	 * <p>Provides additional information about the missing property.
	 * 
	 * @param property
	 * 			the missing {@link PROPERTY}
	 * 
	 * @since 1.1.0
	 */
	public PropertyReadException(PROPERTY property, Throwable rootCause) {
	
		this("Failed to read " + property.getKey() + " from file packrat.properties." +
			 " Please add the " + property.getKey() + " property with suitable value. ", rootCause);
	}
	
	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public PropertyReadException() {}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(String)}.
	 *
	 * @since 1.1.0
	 */
	public PropertyReadException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public PropertyReadException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(String, Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public PropertyReadException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
