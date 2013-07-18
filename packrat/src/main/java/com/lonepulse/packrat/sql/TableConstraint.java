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
 * <p>Represents a constraint which can be applied on a table, targeting one 
 * or more columns. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum TableConstraint implements SQL {

	/**
	 * <p>Identifies a column or a set of columns which provide an identity to 
	 * the whole table. Two or more columns signify a composite primary key.
	 * 
	 * @since 1.1.0
	 */
	PRIMARY_KEY("PRIMARY KEY"),
	
	/**
	 * <p>Identifies a column or a set of columns which contain a unique value 
	 * or a set of values within the whole table.
	 * 
	 * @since 1.1.0
	 */
	UNIQUE("UNIQUE");
	
	
	
	/**
	 * <p>The SQL representation of the constraint.
	 */
	private String sql;
	
	
	/**
	 * <p>Instantiates a new {@link TableConstraint} and initializes 
	 * its {@link #sql} representation.
	 * 
	 * @param constraint
	 * 			the SQL representation of this column constraint
	 *
	 * @since 1.1.0
	 */
	private TableConstraint(String constraint) {
		
		this.sql = constraint;
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
		
		return sql;
	}
	
	/**
	 * <p>Applies a {@link TableConstraint} on one or more columns and produces the 
	 * resulting {@link SQL} segment. 
	 *
	 * @param args
	 * 			the columns on which the {@link TableConstraint} is applied
	 * 
	 * @return the {@link SQL} with the columns to which the constraint is applied
	 * 
	 * @since 1.1.0
	 */
	public SQL onColumns(String... args) {
		
		StringBuilder sqlBuilder = new StringBuilder(sql);
		
		if(args != null && args.length > 0) {
		
			sqlBuilder.append("(").append(args[0]);
			
			for (int i = 1; i < args.length; i++) {
				
				sqlBuilder.append(",").append(args[i]);
			}
			
			sqlBuilder.append(")");
		}
		
		final String sqlWithColumns = sqlBuilder.toString();
		
		return new SQL() {
			
			@Override
			public String getSQLStatement() {
				
				return sqlWithColumns;
			}
		};
	}

	/**
	 * <p>Delegates to {@link #getSQLStatement()}. To use this column constraint with 
	 * some arguments use {@code getSql().withArgs(java.lang.String...)}.
	 */
	@Override
	public String toString() {

		return getSQLStatement();
	}
}
