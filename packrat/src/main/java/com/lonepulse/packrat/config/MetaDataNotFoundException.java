package com.lonepulse.packrat.config;

import com.lonepulse.packrat.PackratRuntimeException;

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
 * <p>This {@link RuntimeException} is thrown when a particular property is missing 
 * or empty in the manifest.</p> 
 * 
 * @version 1.1.0
 * <br><br> 
 * @since 1.1.0
 * <br><br> 
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class MetaDataNotFoundException extends PackratRuntimeException {


	private static final long serialVersionUID = 2314517192321917002L;
	

	/**
	 * <p>Takes the missing property and prints a detailed message.
	 *
	 * @param property
	 * 			the property which was missing
	 * 
	 * @since 1.1.0
	 */
	public MetaDataNotFoundException(String property) {
		
		super(new StringBuilder("Property <").append(property)
			  .append("> is missing in the manifest. ").toString());
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public MetaDataNotFoundException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(String, Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public MetaDataNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
