package com.lonepulse.packrat.sql;

import com.lonepulse.packrat.util.SQLUtils;

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
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum ColumnConstraint implements SQL {

	/**
	 * <p>Identifies a column which provides an identity to the tuple.
	 * 
	 * @since 1.1.0
	 */
	PRIMARY_KEY("PRIMARY KEY"),
	
	/**
	 * <p>Enforces a primary key column to be incremented automatically for each record.
	 * 
	 * @since 1.1.0
	 */
	AUTO_INCREMENT("AUTOINCREMENT"),
	
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
	NOT_NULL("NOT NULL"),
	
	/**
	 * <p>Identifies a column which could contain a default value if 
	 * no value is provided during population. Provide the default 
	 * value using {@link #withArgs(String...)}.
	 *  
	 * @since 1.1.0
	 */
	DEFAULT("DEFAULT");
	
	
	
	/**
	 * <p>The SQL representation of the constraint.
	 */
	private SQLStatement sqlStatement;
	
	
	/**
	 * <p>Instantiates a new {@link ColumnConstraint} and initializes 
	 * its {@link #sql} representation.
	 * 
	 * @param constraint
	 * 			the SQL representation of this column constraint
	 *
	 * @since 1.1.0
	 */
	private ColumnConstraint(final String constraint) {
		
		this.sqlStatement = new SQLStatement() {
			
			@Override
			public String getSQLStatement() {
				
				return constraint;
			}
		};
	}

	/**
	 * <p>Retrieves the SQL representation of this column constraint. It's 
	 * safe to use {@link #toString()} for retrieving the SQL as it delegates 
	 * to this method. 
	 *
	 * @return the SQL representation of this column constraint
	 */
	@Override
	public String getSQLStatement() {
		
		return sqlStatement.getSQLStatement();
	}
	
	/**
	 * <p>Allows the use of a {@link ColumnConstraint} with arguments. For 
	 * example {@code DEFAULT 0}. 
	 *
	 * @param args
	 * 			the arguments to be applied to the {@link ColumnConstraint}
	 * 
	 * @return the {@link SQL} with the arguments applied
	 * 
	 * @since 1.1.0
	 */
	public SQL withArgs(String... args) {
		
		StringBuilder sqlBuilder = new StringBuilder(sqlStatement.getSQLStatement());
		
		if(args != null && args.length > 0) {
		
			for (int i = 0; i < args.length; i++) {
				
				sqlBuilder.append(" ").append(SQLUtils.quoteIfTextual(args[i]));
			}
		}
		
		final String sqlWithArgs = sqlBuilder.toString();
		
		return new SQLStatement() {
			
			@Override
			public String getSQLStatement() {
				
				return sqlWithArgs;
			}
		};
	}

	/**
	 * <p>Delegates to {@link #sqlStatement#toString()}. To use this column constraint with 
	 * some arguments use {@code getSql().withArgs(java.lang.String...)}.
	 */
	@Override
	public String toString() {

		return sqlStatement.toString();
	}
}
