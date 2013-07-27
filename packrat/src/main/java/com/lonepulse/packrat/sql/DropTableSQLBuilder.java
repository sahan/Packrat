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


import android.text.TextUtils;

/**
 * <p>This concrete implementation of {@link AbstractSQLBuilder} provides an implementation 
 * of {@link CreateTablePolicy}.</p>
 * 
 * <p>The operations on this template are not synchronized, please employ your own mechanisms 
 * for thread safety.</p>
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.1
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class DropTableSQLBuilder extends AbstractSQLBuilder implements DropTablePolicy {
	
	
	/**
	 * <p>An implementation of the {@link DropTablePolicy} which delegates the operations 
	 * to an internal copy of {@link DropTableSQLBuilder}. All operations update the internal 
	 * copy to the next mutable state of the builder. 
	 */
	private static final class StateController implements DropTablePolicy {
		
		private DropTablePolicy dropTablePolicy;
		
		public StateController(DropTablePolicy dropTablePolicy) { 
			this.dropTablePolicy = dropTablePolicy; 
		}

		@Override public boolean isCorrupted() {
			return dropTablePolicy.isCorrupted();
		}

		@Override public boolean isImmutable() {
			return dropTablePolicy.isImmutable();
		}

		@Override public String build() throws SQLException {
			return dropTablePolicy.build();
		}

		@Override public String getSQLStatement() {
			return dropTablePolicy.getSQLStatement();
		}

		@Override public DropTablePolicy dropTable(String tableName) throws MalformedSQLException {
			return dropTablePolicy = dropTablePolicy.dropTable(tableName);
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
	 * <p>Creates a new instance of {@link CreateTablePolicy} by instantiating a {@link CreateTableSQLBuilder} 
	 * with its <b>nascent</b> state. 
	 *
	 * @return a new instance of {@link CreateTableSQLBuilder} wrapped in a {@link StateController}
	 * 
	 * @since 1.1.0
	 */
	public static final DropTablePolicy newInstance() {
		
		return new StateController(new DropTableSQLBuilder() {

			@Override public String build() throws SQLException {
				throw new MalformedSQLException("Invoke dropTable() to specify the table to drop before building. ");
			}
		});
	}
	
	/**
	 * <p>Creates a new {@link DropTableSQLBuilder}. Visibility is restricted to enforce use of {@link #newInstance()}.
	 *
	 * @since 1.1.0
	 */
	private DropTableSQLBuilder() {}
	
	/**
	 * <p>Creates a new {@link DropTableSQLBuilder} by mirroring the state of the given {@link DropTableSQLBuilder}.
	 * 
	 * @param dropTableSQLBuilder
	 * 			the instance of {@link dropTableSQLBuilder} whose state is to be mirrored in this instance
	 *
	 * @since 1.1.0
	 */
	private DropTableSQLBuilder(DropTableSQLBuilder dropTableSQLBuilder) {
		
		super(dropTableSQLBuilder);
	}
	
	/**
	 * <p>See {@link DropTablePolicy#dropTable(String)}.
	 */
	@Override
	public DropTablePolicy dropTable(String tableName) throws MalformedSQLException {
		
		if(!TextUtils.isEmpty(tableName)) {
		
			return this;
		}
			
		sql().append("DROP TABLE ").append(tableName);
		setCorrupted(false);
		
		return new DropTableSQLBuilder() {
			
			@Override public DropTablePolicy dropTable(String tableName) throws MalformedSQLException {
				throwIfImmutable();
				throw new MalformedSQLException("Cannot invoke dropTable() twice on the same template. ");
			}
		};
	}

	/**
	 * <p>See {@link DropTablePolicy#build()}.
	 */
	@Override
	public String build() throws SQLException {
		
		throwIfImmutable();
		
		if(isCorrupted()) {
			
			throw new MalformedSQLException("Invoke dropTable() before building. ");
		}
		
		setImmutable();
		return sql().append(";").toString();
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
			
			throw new SQLException(DropTableSQLBuilder.IMMUTABLE_ERROR_CONTEXT.toString());
		}
	}
}
