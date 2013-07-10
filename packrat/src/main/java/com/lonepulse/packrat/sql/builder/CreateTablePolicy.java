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
 * <p>A fluent facade which specifies the services offered for building SQL statements 
 * that <b>create tables</b> using rudimentary string manipulation.  
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface CreateTablePolicy {

	/**
	 * <p>Initializes the SQL build by specifying the name of the table 
	 * to be created. 
	 *
	 * @param tableName
	 * 			the name of the table to be created
	 * 
	 * @return the current state of the {@link CreateTablePolicy}
	 * 
	 * @throws MalformedSQLException
	 * 			if this operation has resulted in a corrupt SQL statement
	 * 
	 * @since 1.1.0
	 */
	CreateTablePolicy createTable(String tableName) throws MalformedSQLException;
	
	/**
	 * <p>Adds a constraint which specifies that the table should be created 
	 * only if it does not exist in the database. 
	 *
	 * @return the current state of the {@link CreateTablePolicy}
	 * 
	 * @throws MalformedSQLException
	 * 			if this operation has resulted in a corrupt SQL statement
	 * 
	 * @since 1.1.0
	 */
	CreateTablePolicy ifNotExists() throws MalformedSQLException;
	
	/**
	 * <p>Adds a column to the Initializes the SQL build by specifying the name of the table of the 
	 * table to be created. 
	 *
	 * @param columnName
	 * 			the name of the column to be created
	 * 
	 * @param typeAffinity
	 * 			the data type which this column <i>likes</i> to contain
	 * 
	 * @return the current state of the {@link CreateTablePolicy}
	 * 
	 * @throws MalformedSQLException
	 * 			if this operation has resulted in a corrupt SQL statement
	 * 
	 * @since 1.1.0
	 */
	CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException;
	
	/**
	 * <p>Adds column constraints to a freshly created column using {@link ColumnConstraint}. 
	 * For example to define a column as <b>unique</b> use {@code ColumnConstraint.UNIQUE}, or 
	 * to define a <b>default</b> constraint use {@code ColumnConstraint.DEFAULT.withArgs("basic")} 
	 * (the default value being 'basic' in this example).
	 *
	 * @param columnConstraints
	 * 			the constraints to be applied to the column to be created; these are {@link SQL} 
	 * 			segments usually created using {@link ColumnConstraint}  
	 * 
	 * @return the current state of the {@link CreateTablePolicy}
	 * 
	 * @throws MalformedSQLException
	 * 			if this operation has resulted in a corrupt SQL statement
	 * 
	 * @since 1.1.0
	 */
	CreateTablePolicy withConstraints(SQL... columnConstraints) throws MalformedSQLException;
}
