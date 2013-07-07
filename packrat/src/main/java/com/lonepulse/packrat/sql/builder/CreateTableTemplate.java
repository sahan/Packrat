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


import java.util.concurrent.atomic.AtomicBoolean;

import android.text.TextUtils;

/**
 * <p>This concrete implementation of {@link AbstractSQLBuilder} provides an 
 * implementation of {@link CreateTablePolicy}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class CreateTableTemplate extends AbstractSQLBuilder implements CreateTablePolicy {


	/**
	 * <p>This flag indicates whether the SQL statement composition has begun.
	 */
	private AtomicBoolean tableInitialized;
	
	/**
	 * <p>This flag indicates whether the first column defition has been added 
	 * to the SQL statement composition.
	 */
	private AtomicBoolean firstColumnAdded;
	
	
	/**
	 * <p>Creates a new {@link CreateTableTemplate} by initializing the switches.
	 *
	 * @since 1.1.0
	 */
	public CreateTableTemplate() {
	
		tableInitialized = new AtomicBoolean(false);
		firstColumnAdded = new AtomicBoolean(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(TextUtils.isEmpty(tableName))
			throw new SQLException("A table name is required to create a table definition.");
		
		if(tableInitialized.get())
			throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
		
		if(firstColumnAdded.get())
			throw new MalformedSQLException("Invoke createTable() before adding any columns. ");
		
		sql().append("CREATE TABLE ").append(tableName);
		tableInitialized.set(true);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreateTablePolicy ifNotExists() throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(!tableInitialized.get())
			throw new MalformedSQLException("Invoke createTable() before invoking ifNotExists(). ");
		
		if(firstColumnAdded.get())
			throw new MalformedSQLException("Invoke ifNotExists() before adding any columns. ");
		
		sql().append("IF NOT EXISTS ");
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(TextUtils.isEmpty(columnName))
			throw new SQLException("A column name is required to create a column definition.");
		
		if(typeAffinity == null)
			throw new SQLException("A column definition should be given a type affinity.");
		
		if(!tableInitialized.get())
			throw new MalformedSQLException("Invoke createTable() before adding columns. ");
		
		if(!firstColumnAdded.get()) {
			
			sql().append(" ( ");
		}
		else {
			
			sql().append(", ");
		}
			
		sql().append(columnName).append(" ").append(typeAffinity);
		
		if(!firstColumnAdded.get())  {
			
			firstColumnAdded.set(true);
			setCorrupted(false);
		}
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized CreateTablePolicy withConstraints(ColumnConstraint... columnConstraints) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(!firstColumnAdded.get())
			throw new MalformedSQLException("Column constraints require an accompanying column definition.");
		
		if(columnConstraints != null) {
			
			for (ColumnConstraint constraint : columnConstraints) {
				
				sql().append(" ").append(constraint);
			}
		}
		
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized String build() throws SQLException {
		
		throwIfImmutable();
		
		if(isCorrupted()) {
			
			throw new MalformedSQLException("Invoke createTable() and add at least one column before building. ");
		}

		setImmutable();
		return sql().append(" );").toString();
	}
	
	/**
	 * <p>Throws an exception if this template cannot be mutated any further. 
	 *
	 * @throws SQLException
	 * 			if this template cannot be mutated any further
	 * 
	 * @since 1.1.0
	 */
	private void throwIfImmutable() throws SQLException {
		
		if(isImmutable())
			throw new SQLException("This template is now immutable. Use getSQLStatement() to edit the SQL manually.");
	}
}
