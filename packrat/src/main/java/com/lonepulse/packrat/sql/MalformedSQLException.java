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


/**
 * <p>This exception is thrown due to unrecoverable errors in <b>building</b> 
 * SQL statements using rudimentary {@link String} manipulation.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class MalformedSQLException extends SQLException {


	private static final long serialVersionUID = 563212701694328603L;

	
	/**
	 * <p>See {@link SQLException#SQLBuilderException()}.
	 *
	 * @since 1.1.0
	 */
	public MalformedSQLException() {}

	/**
	 * <p>See {@link SQLException#SQLBuilderException(String)}.
	 *
	 * @since 1.1.0
	 */
	public MalformedSQLException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * <p>See {@link SQLException#SQLBuilderException(Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public MalformedSQLException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link SQLException#SQLBuilderException(String, Throwable)}.
	 *
	 * @since 1.1.0
	 */
	public MalformedSQLException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
