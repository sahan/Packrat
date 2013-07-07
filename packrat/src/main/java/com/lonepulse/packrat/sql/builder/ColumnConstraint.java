package com.lonepulse.packrat.sql.builder;

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
 * <p>Represents a constraint which can be applied on a column. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum ColumnConstraint {

	/**
	 * <p>Identifies a column which provides an identity to the tuple.
	 * 
	 * @since 1.1.0
	 */
	PRIMARY_KEY("PRIMARY KEY"),
	
	/**
	 * <p>Identifies a column which contains a unique value within the 
	 * whole table.
	 * 
	 * @since 1.1.0
	 */
	UNIQUE("UNIQUE"),
	
	/**
	 * <p>Identifies a column which cannot contain null values.
	 * 
	 * @since 1.1.0
	 */
	NOT_NULL("NOT NULL");
	
	
	/**
	 * <p>The SQL representation of the constraint.
	 */
	private String sql;
	
	
	/**
	 * <p>Instantiates a new {@link ColumnConstraint} and initializes 
	 * it's {@link #sql} representation.
	 * 
	 * @param sql
	 * 			the SQL representation of this column constraint
	 *
	 * @since 1.1.0
	 */
	private ColumnConstraint(String sql) {
		
		this.sql = sql;
	}

	/**
	 * <p>Retrieves the SQL representation of this column constraint. It's 
	 * safe to use {@link #toString()} for retrieving the SQL as it delegates 
	 * to this method. 
	 *
	 * @return the SQL representation of this column constraint
	 */
	public String getSql() {
		
		return sql;
	}

	/**
	 * <p>Delegates to {@link #getSql()}.
	 */
	@Override
	public String toString() {

		return getSql();
	}
}
