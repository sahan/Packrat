package com.lonepulse.packrat.sql;

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


import com.lonepulse.packrat.PackratRuntimeException;

/**
 * <p>This exception is thrown for unrecoverable errors in generating SQL 
 * statements by reading the metadata of models.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class SQLException extends PackratRuntimeException {


	private static final long serialVersionUID = 563212701694328603L;
	

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException()}.
	 *
	 * @since 1.1.0
	 */
	public SQLException() {}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(String)}.
	 *
	 * @since 1.1.0
	 */
	public SQLException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public SQLException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link PackratRuntimeException#PackratRuntimeException(String, Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public SQLException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
