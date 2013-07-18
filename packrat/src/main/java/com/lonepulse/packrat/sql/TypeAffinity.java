package com.lonepulse.packrat.sql;

import java.lang.reflect.Field;

import com.lonepulse.packrat.util.Resolver;

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
 * <p>Identifies the type affinities for a column on an SQLite 3 database table.  
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum TypeAffinity {

	/**
	 * <p>To be used on a column which is to store textual data.
	 * 
	 * @since 1.1.0
	 */
	TEXT("TEXT"),
	
	/**
	 * <p>To be used on a column which is to store integer data.
	 * 
	 * @since 1.1.0
	 */
	INTEGER("INTEGER"),
	
	/**
	 * <p>To be used on a column which is to store floating point numerals.
	 * 
	 * @since 1.1.0
	 */
	REAL("REAL"),
	
	/**
	 * <p>To be used on a column which is to store numeric data.
	 * 
	 * @since 1.1.0
	 */
	NUMERIC("NUMERIC"),
	
	/**
	 * <p>To be used on a column which shows no interest in any type affinity.
	 * 
	 * @since 1.1.0
	 */
	NONE("NONE");
	
	
	/**
	 * <p>The SQL representation of the type affinity.
	 */
	private String sql;
	
	
	/**
	 * <p>Instantiates a new {@link TypeAffinity} and initializes 
	 * it's {@link #sql} representation.
	 * 
	 * @param sql
	 * 			the SQL representation of this type affinity
	 *
	 * @since 1.1.0
	 */
	private TypeAffinity(String sql) {
		
		this.sql = sql;
	}

	/**
	 * <p>Retrieves the SQL representation of this type affinity. It's safe 
	 * to use {@link #toString()} for retrieving the SQL as it delegates to 
	 * this method. 
	 * 
	 *
	 * @return the SQL representation of this type affinity
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

	/**
	 * <p>A {@link Resolver} which determines the {@link TypeAffinity} which best matches 
	 * a given {@link Field}'s type.
	 * 
	 * @since 1.1.0
	 */
	public static final Resolver<Class<?>, TypeAffinity> RESOLVER = new Resolver<Class<?>, TypeAffinity>() {
		
		/**
		 * <p>Takes the {@link Class} of a {@link Field} and resolves the most 
		 * suitable {@link TypeAffinity} which represents it in a table.
		 * 
		 * @param type
		 * 			the {@link Class} of the {@link Field} on a model
		 * 
		 * @return the {@link TypeAffinity} which best matches this {@link Field}
		 * 
		 * @since 1.1.0
		 */
		@Override
		public TypeAffinity resolve(Class<?> type) {
	
			if(byte.class.isAssignableFrom(type) || short.class.isAssignableFrom(type) ||
			   int.class.isAssignableFrom(type) || long.class.isAssignableFrom(type) ||
			   Byte.class.isAssignableFrom(type) || Short.class.isAssignableFrom(type) ||
			   Integer.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type)) {
				
				return INTEGER;
			}
			else if(float.class.isAssignableFrom(type) || double.class.isAssignableFrom(type) ||
					Float.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) {
				
				return REAL;
			}
			else if(Number.class.isAssignableFrom(type)) {
				
				return NUMERIC;
			}
			else {
				
				return TEXT;
			}
		}
	};
}
