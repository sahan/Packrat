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


import java.util.Collection;

import android.text.TextUtils;

/**
 * <p>This concrete implementation of {@link AbstractSQLBuilder} provides an implementation 
 * of {@link CreateTablePolicy}. Use {@link CreateTableSQLBuilder#newInstance()} to obtain a 
 * fresh copy of the template.</p>
 * 
 * <p>The mutable operations on this template are not synchronized, please employ your own 
 * mechanisms for thread safety.</p>
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class CreateTableSQLBuilder extends AbstractSQLBuilder implements CreateTablePolicy {

	/**
	 * <p>An implementation of the {@link CreateTablePolicy} which delegates the operations 
	 * to an internal copy of {@link CreateTableSQLBuilder}. All operations update the internal 
	 * copy to the next mutable state of the builder. 
	 */
	private static final class StateController implements CreateTablePolicy {
		
		private CreateTablePolicy createTablePolicy;
		
		public StateController(CreateTablePolicy createTablePolicy) { 
			this.createTablePolicy = createTablePolicy; 
		}
		
		@Override public boolean isCorrupted() { 
			return createTablePolicy.isCorrupted(); 
		}

		@Override public boolean isImmutable() { 
			return createTablePolicy.isImmutable(); 
		}

		@Override public String build() throws SQLException { 
			return createTablePolicy.build(); 
		}

		@Override public String getSQLStatement() { 
			return createTablePolicy.getSQLStatement(); 
		}

		@Override public CreateTablePolicy createTable(String tableName) throws MalformedSQLException { 
			return createTablePolicy = createTablePolicy.createTable(tableName); 
		}

		@Override public CreateTablePolicy ifNotExists() throws MalformedSQLException { 
			return createTablePolicy = createTablePolicy.ifNotExists(); 
		}

		@Override public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException { 
			return createTablePolicy = createTablePolicy.addColumn(columnName, typeAffinity); 
		}

		@Override public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException { 
			return createTablePolicy = createTablePolicy.withColumnConstraints(columnConstraints); 
		}

		@Override public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException { 
			return createTablePolicy = createTablePolicy.withTableConstraints(tableConstraints); 
		}
	}
	
	/**
	 * <p>The detailed error context which is logged when an operation is invoked after 
	 * the template has been set to immutable.
	 */
	private static final StringBuilder IMMUTABLE_ERROR_CONTEXT = new StringBuilder()
	.append("The build service has already been invoked and this builder is now immutable. ")
	.append("Invoke getSQLStatement() to edit the SQL manually.");
	

	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} by instantiating a 
	 * {@link CreateTableSQLBuilder} with its <b>nascent</b> state. 
	 *
	 * @return a new instance of {@link CreateTableSQLBuilder} wrapped in a {@link StateController}
	 * 
	 * @since 1.1.0
	 */
	public static final CreateTablePolicy newInstance() {
		
		return new StateController(new CreateTableSQLBuilder() {

			String errorContext = "Invoke createTable() to provide the table definition. ";
			
			@Override public CreateTablePolicy ifNotExists() throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}

			@Override public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException {
				throw new MalformedSQLException(errorContext);
			}
			
			@Override public String build() throws SQLException {
				throw new MalformedSQLException(errorContext);
			}
		});
	}
	
	/**
	 * <p>Creates a new {@link CreateTableSQLBuilder}. Visibility is restricted to enforce use 
	 * of {@link #newInstance()}.
	 *
	 * @since 1.1.0
	 */
	private CreateTableSQLBuilder() {}
	
	/**
	 * <p>Creates a new {@link CreateTableSQLBuilder} by mirroring the state of the 
	 * given {@link CreateTableSQLBuilder}.
	 * 
	 * @param createTableSQLBuilder
	 * 			the instance of {@link CreateTableSQLBuilder} whose state is to be 
	 * 			mirrored in this instance
	 *
	 * @since 1.1.0
	 */
	private CreateTableSQLBuilder(CreateTableSQLBuilder createTableSQLBuilder) {
		
		super(createTableSQLBuilder);
	}
	
	/**
	 * <p>Creates a new instance of {@link CreateTablePolicy} with its <b>post table defined</b> state.</p>
	 * 
	 * @see CreateTablePolicy#createTable(String)
	 */
	@Override
	public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
		
		throwIfImmutable();
		
		if(TextUtils.isEmpty(tableName)) {
			
			return this;
		}
		
		sql().append("CREATE TABLE ").append(tableName);
		
		return new CreateTableSQLBuilder(this) {

			@Override public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
			}

			@Override public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot add column constraints without creating a column. ");
			}

			@Override public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot add table constraints without creating a column. ");
			}

			@Override public String build() throws SQLException { 
				throw new MalformedSQLException("Cannot build this statement without creating a column. ");
			}
		};
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
		
		return new CreateTableSQLBuilder(this) {
			
			@Override public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
			}

			@Override public CreateTablePolicy ifNotExists() throws MalformedSQLException {
				throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
			}

			@Override public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot add column constraints without creating a column. ");
			}

			@Override public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot add table constraints without creating a column. ");
			}

			@Override public String build() throws SQLException {
				throw new MalformedSQLException("Cannot build this statement without creating a column. ");
			}
		};
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
		
		if(TextUtils.isEmpty(columnName) || typeAffinity == null)  {
			
			return this;
		}
		
		sql().append(" ( ").append(columnName).append(" ").append(typeAffinity);
		setCorrupted(false);
			
		return new CreateTableSQLBuilder(this) {
			
			@Override public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
				throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
			}

			@Override public CreateTablePolicy ifNotExists() throws MalformedSQLException {
				throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
			}

			@Override
			public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
				
				throwIfImmutable();
				
				if(TextUtils.isEmpty(columnName) || typeAffinity == null)  {
					
					return this;
				}
				
				sql().append(", ").append(columnName).append(" ").append(typeAffinity);
				return this;
			}
		};
	}
	
	/**
	 * <p>Returns an instance of {@link CreateTablePolicy} in its <b>column defined</b> state.</p>
	 * 
	 * @see CreateTablePolicy#withColumnConstraints(Collection<SQL>)
	 */
	@Override
	public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException {
		
		if(columnConstraints == null || columnConstraints.isEmpty()) {
			
			return this;
		}
		
		for (SQL constraint : columnConstraints) {
				
			sql().append(" ").append(constraint);
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
	public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException {
		
		if(tableConstraints == null || tableConstraints.isEmpty()) {
			
			return this;
		}
		
		for (SQL constraint : tableConstraints) {
			
			sql().append(", ").append(constraint);
		}
		
		return new CreateTableSQLBuilder(this) {
			
			@Override public CreateTablePolicy createTable(String tableName) throws MalformedSQLException {
				throwIfImmutable();
				throw new MalformedSQLException("Cannot invoke createTable() twice on the same template. ");
			}
			
			@Override public CreateTablePolicy ifNotExists() throws MalformedSQLException {
				throwIfImmutable();
				throw new MalformedSQLException("ifNotExists() can only be invoked once, immediately after createTable(). ");
			}
			
			@Override public CreateTablePolicy addColumn(String columnName, TypeAffinity typeAffinity) throws MalformedSQLException {
				throwIfImmutable();
				throw new MalformedSQLException("You cannot define any more columns after adding table constraints. ");
			}
			
			@Override public CreateTablePolicy withColumnConstraints(Collection<SQL> columnConstraints) throws MalformedSQLException {
				throwIfImmutable(); 
				throw new MalformedSQLException("You cannot apply any column constraints after adding table constraints. ");
			}

			@Override public CreateTablePolicy withTableConstraints(Collection<SQL> tableConstraints) throws MalformedSQLException {
				
				throwIfImmutable();
				if(tableConstraints == null || tableConstraints.isEmpty()) {
					
					return this;
				}
				
				for (SQL constraint : tableConstraints) {
					
					sql().append(", ").append(constraint);
				}
				
				return this;
			}
		};
	}

	/**
	 * <p>See {@link CreateTablePolicy#build()}.
	 */
	@Override
	public String build() throws SQLException {
		
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
	 */
	private void throwIfImmutable() throws SQLException {
		
		if(isImmutable()) {
			
			throw new SQLException(CreateTableSQLBuilder.IMMUTABLE_ERROR_CONTEXT.toString());
		}
	}
}
