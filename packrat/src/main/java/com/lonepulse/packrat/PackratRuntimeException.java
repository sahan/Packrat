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

/**
 * <p>This {@link RuntimeException} is thrown for <b>unrecoverable</b> which 
 * occur during the use of Packrat. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class PackratRuntimeException extends RuntimeException {

	
	private static final long serialVersionUID = 2527063431323851126L;
	
	
	/**
	 * See {@link RuntimeException#RuntimeException()}. 
	 */
	public PackratRuntimeException() {
	}

	/**
	 * See {@link RuntimeException#RuntimeException(String))}. 
	 */
	public PackratRuntimeException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * See {@link RuntimeException#RuntimeException(Throwable)}. 
	 */
	public PackratRuntimeException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * See {@link RuntimeException#RuntimeException(String, Throwable)}. 
	 */
	public PackratRuntimeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
