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
 * <p>An abstract implementation of {@link SQL} which adds implementations for 
 * {@link Object#hashCode()}, {@link Object#equals(Object)} and {@link Object#toString()}. 
 * It supports cloning via {@link Cloneable}; see {@link #clone()}.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public abstract class SQLStatement implements SQL, Cloneable {
	

	/**
	 * <p>Retrieves a <b>deep copy<b> of this instance of {@link SQLStatement} based on 
	 * the current state of the SQL statement as returned by {@link #getSQLStatement()}.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public SQLStatement clone() throws CloneNotSupportedException {
		
		final String sql = getSQLStatement(); 
		
		return new SQLStatement() {
			
			@Override
			public String getSQLStatement() {
				
				return sql;
			}
		};
	}

	/**
	 * <p>Calculates the hashcode based on the current state of the SQL statement as 
	 * returned by {@link #getSQLStatement()}.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSQLStatement() == null) ? 0 : getSQLStatement().hashCode());
		
		return result;
	}

	/**
	 * <p>Check equality based on the current state of the SQL statement as 
	 * returned by {@link #getSQLStatement()}.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			
			return true;
		}
		
		if (obj == null) {
			
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			
			return false;
		}
		
		SQLStatement other = (SQLStatement) obj;
		
		if (getSQLStatement() == null) {
			
			if (other.getSQLStatement() != null) return false;
		} 
		else if (!getSQLStatement().equals(other.getSQLStatement())) { 
			
			return false;
		}
		
		return true;
	}

	/**
	 * <p>Delegates to {@link #getSQLStatement()}.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public String toString() {
		
		return getSQLStatement();
	}
}
