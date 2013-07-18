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


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>An abstract implementation of {@link SQLBuilder} which initializes 
 * all switches and common information.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
abstract class AbstractSQLBuilder implements SQLBuilder {

	
	/**
	 * <p>This flag determines if the {@link SQLBuilder} has already been immutable.
	 */
	private AtomicBoolean immutable;
	
	/**
	 * <p>An atomic boolean switch which provides an indication of whether the 
	 * internal state of the SQL build is malformed.
	 */
	private AtomicBoolean corrupted;
	
	/**
	 * <p>The {@link StringBuilder} which contains the SQL string that is 
	 * being composed.
	 */
	private StringBuilder sql;
	
	
	/**
	 * <p>Creates a new instance of {@link AbstractSQLBuilder} by initializing 
	 * all state switches and common information.
	 *
	 * @since 1.1.0
	 */
	public AbstractSQLBuilder() {
	
		corrupted = new AtomicBoolean(true);
		immutable = new AtomicBoolean(false);
		sql = new StringBuilder();
	}
	
	/**
	 * <p>Creates a new instance of {@link AbstractSQLBuilder} by mirroring the 
	 * state from the given instance of {@link AbstractSQLBuilder}.
	 * 
	 * @param abstractSQLBuilder
	 * 			the instance of {@link AbstractSQLBuilder} whose state is to be 
	 * 			mirrored in this instance
	 *
	 * @since 1.1.0
	 */
	public AbstractSQLBuilder(AbstractSQLBuilder abstractSQLBuilder) {
		
		corrupted = new AtomicBoolean(abstractSQLBuilder.corrupted.get());
		immutable = new AtomicBoolean(abstractSQLBuilder.immutable.get());
		sql = new StringBuilder(abstractSQLBuilder.sql.toString());
	}
	
	/**
	 * <p>Retrieves the {@link StringBuilder} which contains the SQL string 
	 * which is being composed.</p>
	 *
	 * @return the {@link StringBuilder} which contains the SQL string that 
	 * 		   is being composed 
	 * 
	 * @since 1.1.0
	 */
	protected synchronized StringBuilder sql() {
		
		return sql;
	}
	
	/**
	 * <p>Manages the internal state of the sql by changing 
	 * the corruption switch. 
	 *
	 * @param isCorrupt
	 * 			the boolean state to set the internal corruption switch to 
	 * 			
	 * @since 1.1.0
	 */
	protected synchronized void setCorrupted(boolean isCorrupt) {
		
		corrupted.set(isCorrupt);
	}

	/**
	 * <p>Discovers the internal corruption state - i.e. this returns an 
	 * indication of whether the current SQL build is malformed.  
	 *
	 * @return {@code true} if the current SQL build is malformed
	 * 
	 * @since 1.1.0
	 */
	@Override
	public synchronized boolean isCorrupted() {
		
		return corrupted.get();
	}
	
	/**
	 * <p>Disallows further composition of SQL by making the internal state 
	 * immutable from here onwards. 
	 * 			
	 * @since 1.1.0
	 */
	protected synchronized void setImmutable() {

		immutable.set(true);
	}

	/**
	 * <p>Discovers the internal corruption state - i.e. this returns an 
	 * indication of whether the current SQL build is malformed.  
	 *
	 * @return {@code true} if the current SQL composition is malformed
	 * 
	 * @since 1.1.0
	 */
	@Override
	public synchronized boolean isImmutable() {
		
		return immutable.get();
	}

	/**
	 * <p>Retrieves the SQL statement which has been composed so far. 
	 * 
	 * @return the SQL statement which has been composed so far
	 * 
	 * @since 1.1.0
	 */
	@Override
	public String getSQLStatement() {
		
		return sql.toString();
	}
}
