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
 * <p>This {@link Exception} is thrown for <b>recoverable</b> errors which 
 * occur during the use of Packrat. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class PackratException extends Exception {

	
	private static final long serialVersionUID = 4860294447766799654L;
	

	/**
	 * See {@link Exception#Exception()}. 
	 */
	public PackratException() {
	}

	/**
	 * See {@link Exception#Exception(String)}. 
	 */
	public PackratException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * See {@link Exception#Exception(Throwable)}. 
	 */
	public PackratException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * See {@link Exception#Exception(String, Throwable)}. 
	 */
	public PackratException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
