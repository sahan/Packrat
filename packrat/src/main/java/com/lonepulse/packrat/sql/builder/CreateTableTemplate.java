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
 * <p>This concrete implementation of {@link AbstractSQLBuilder} provides an implementation 
 * of {@link CreateTablePolicy}.</p>
 * 
 * <p><b>Note</b> that this template may either mutate its state or produce an entirely new 
 * instance of {@link CreateTablePolicy} as a result of its operations.
 * 
 * <p>The mutable operations on this template are not synchronized, please employ your own 
 * mechanisms for thread safety.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class CreateTableTemplate extends AbstractSQLBuilder implements CreateTablePolicy {


	/**
	 * <p>This flag indicates whether the first column definition has been added 
	 * to the SQL statement composition.
	 */
	private AtomicBoolean firstColumnAdded;
	
	/**
	 * <p>This flag indicates whether any table constraints have been added.
	 */
	private AtomicBoolean firstTableConstraintAdded;
	
	
	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} by instantiating a 
	 * {@link CreateTableTemplate} with it's <b>nascent</b> state. 
	 *
	 * @return a new instance of {@link CreateTableTemplate}.
	 * 
	 * @since 1.1.0
	 */
	public static final CreateTablePolicy newInstance() {
		
		return new CreateTableTemplate() {

			String errorContext = "Invoke createTable() to provide the table definition. ";
			
			@Override
			public CreateTablePolicy ifNotExists() throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override
			public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override
			public CreateTablePolicy withColumnConstraints(SQL... columnConstraints) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override
			public CreateTablePolicy withTableConstraints(SQL... tableConstraints) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}
			
			@Override
			public String build() throws SQLException {
				throw new MalformedSQLException(errorContext);
			}
		};
	}
	
	/**
	 * <p>Creates a new {@link CreateTableTemplate} by initializing the switches.
	 *
	 * @since 1.1.0
	 */
	private CreateTableTemplate() {
	
		firstColumnAdded = new AtomicBoolean(false);
		firstTableConstraintAdded = new AtomicBoolean(false);
	}
	
	/**
	 * <p>Creates a new {@link CreateTableTemplate} by mirroring the state of the 
	 * given {@link CreateTableTemplate}.
	 * 
	 * @param createTableTemplate
	 * 			the instance of {@link CreateTableTemplate} whose state is to be 
	 * 			mirrored in this instance
	 *
	 * @since 1.1.0
	 */
	private CreateTableTemplate(CreateTableTemplate createTableTemplate) {
		
		super(createTableTemplate);
		
		firstColumnAdded = new AtomicBoolean(createTableTemplate.firstColumnAdded.get());
		firstTableConstraintAdded = new AtomicBoolean(createTableTemplate.firstTableConstraintAdded.get());
	}
	
	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} with its <b>post table defined</b> state.</p>
	 * 
	 * @see CreateTablePolicy#createTable(String)
	 */
	@Override
	public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(TextUtils.isEmpty(tableName))
			throw new SQLException("A table name is required to create a table definition.");
		
		sql().append("CREATE TABLE ").append(tableName);
		
		try {
		
			return new CreateTableTemplate(this) {
	
				@Override
				public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
					throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
				}
			};
		}
		finally { setImmutable(); }
	}

	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} with its <b>pre columns defined</b> state.</p>
	 * 
	 * @see CreateTablePolicy#ifNotExists()
	 */
	@Override
	public CreateTablePolicy ifNotExists() throws MalformedSQLException {
		
		throwIfImmutable();
		
		sql().append("IF NOT EXISTS ");
		
		try {
			
			return new CreateTableTemplate(this) {
				
				@Override
				public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
					throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
				}
	
				@Override
				public CreateTablePolicy ifNotExists() throws MalformedSQLException {
					throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
				}
			};
		}
		finally { setImmutable(); }
	}

	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} with its <b>column defined</b> state 
	 * if this is first column definition; else the same instance is returned.</p>
	 *  
	 * @see CreateTablePolicy#addColumn(String, TypeAffinity)
	 */
	@Override
	public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(TextUtils.isEmpty(columnName))
			throw new SQLException("A column name is required to create a column definition.");
		
		if(typeAffinity == null)
			throw new SQLException("A column definition should be given a type affinity.");
		
		if(!firstColumnAdded.get()) {
			
			sql().append(" ( ");
		}
		else {
			
			sql().append(", ");
		}
			
		if(!firstColumnAdded.get())  {
			
			firstColumnAdded.set(true);
			
			sql().append(columnName).append(" ").append(typeAffinity);
			setCorrupted(false);
			
			try {
				
				return new CreateTableTemplate(this) {
					
					@Override
					public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
						throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
					}
	
					@Override
					public CreateTablePolicy ifNotExists() throws MalformedSQLException {
						throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
					}
				};
			}
			finally { setImmutable(); }
		}
		else {
			
			return this;
		}
	}
	
	/**
	 * <p>Returns an instance of {@link CreateTablePolicy} in its <b>column defined</b> state.</p>
	 * 
	 * @see CreateTablePolicy#withColumnConstraints(SQL...)
	 */
	@Override
	public CreateTablePolicy withColumnConstraints(SQL... columnConstraints) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(!firstColumnAdded.get())
			throw new MalformedSQLException("Column constraints require an accompanying column definition.");
		
		if(columnConstraints != null) {
			
			for (SQL constraint : columnConstraints) {
				
				sql().append(" ").append(constraint);
			}
		}
		
		return this;
	}
	
	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} with its <b>post columns defined</b> state 
	 * if this is the first table constraint being added; else the same instance is returned.</p>
	 *  
	 * @see CreateTablePolicy#addColumn(String, TypeAffinity)
	 */
	@Override
	public CreateTablePolicy withTableConstraints(SQL... tableConstraints) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(!firstColumnAdded.get())
			throw new MalformedSQLException("Define a few columns using addColumn() before creating any table constraints.");
		
		if(tableConstraints != null) {
			
			for (SQL constraint : tableConstraints) {
				
				sql().append(", ").append(constraint);
			}
		}
		
		if(!firstTableConstraintAdded.get()) {
			
			firstTableConstraintAdded.set(true);
			
			try {
			
				return new CreateTableTemplate(this) {
					
					@Override
					public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
						throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
					}
					
					@Override
					public CreateTablePolicy ifNotExists() throws MalformedSQLException {
						throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
					}
					
					@Override
					public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
						throw new MalformedSQLException("You cannot define any more columns after adding table constraints. ");
					}
					
					@Override
					public CreateTablePolicy withColumnConstraints(SQL... columnConstraints) throws MalformedSQLException {
						throw new MalformedSQLException("You cannot apply any column constraints after adding table constraints. ");
					}
				};
			}
			finally { setImmutable(); }
		}
		else {
			
			return this;
		}
	}

	/**
	 * <p>See {@link CreateTablePolicy#build()}.
	 */
	@Override
	public String build() throws SQLException {
		
		throwIfImmutable();
		
		if(isCorrupted())
			throw new MalformedSQLException("Invoke createTable() and add at least one column before building. ");
		
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
		
		if(isImmutable()) {
			
			StringBuilder errorContext = new StringBuilder("This template is now immutable due to an ")
			.append("operation which resulted in a stale state. Use the template generated by the ")
			.append("intermediate operation or invoke getSQLStatement() to edit the SQL manually.");
			
			throw new SQLException(errorContext.toString());
		}
	}
}
