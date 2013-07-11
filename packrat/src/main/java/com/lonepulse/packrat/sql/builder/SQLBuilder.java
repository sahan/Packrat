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
 * <p>This contract specifies the common services offered for builders 
 * which produce SQL using rudimentary string manipulation. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface SQLBuilder extends SQL {
	
	/**
	 * <p>Discovers the internal corruption state - i.e. this returns an 
	 * indication of whether the current SQL build is malformed.  
	 *
	 * @return {@code true} if the current SQL composition is malformed
	 * 
	 * @since 1.1.0
	 */
	boolean isCorrupted();
	
	/**
	 * <p>Templates built using this policy cannot be recycled. If the template has 
	 * progressed to being immutable, any new SQL compositions via this policy should 
	 * be done using a fresh template.</p> 
	 * 
	 * <p><b>if you wish to manually edit the SQL, use {@link #getSQLStatement()} to 
	 * fetch the final SQL statement which was composed (as a {@link StringBuilder}) 
	 * and perform any necessary edits.</b>
	 *
	 * @return {@code true} if the template cannot be mutated any longer
	 * 
	 * @since 1.1.0
	 */
	boolean isImmutable();
	
	/**
	 * <p>Finalizes the build and outputs the composed SQL string. Any implementation of this method 
	 * should check to corruption state of the SQL composition by invoking {@link #isCorrupted()} and 
	 * throw a sub-class of {@link SQLException} if the SQL is indeed corrupt. 
	 *
	 * @return the composed SQL string 
	 * 
	 * @throws SQLException
	 * 			if the final build failed due to a corrupt state
	 * 
	 * @since 1.1.0
	 */
	String build() throws SQLException;
}
