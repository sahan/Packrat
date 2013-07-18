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
 * <p>This contract specifies the services offered for generating <b>schemas</b> 
 * by producing <b>Data Definition Language</b> statements - in a specific SQL 
 * flavor, for a targeted model. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface DDLPolicy {

	//TODO include schema ALTERs? NOTE: SQLite supports column renames and additions only 
	//TODO look into cascading CREATEs and DROPs.
	
	/**
	 * <p>Generate the <b>CREATE</b> statements for a relation identified by 
	 * the given model and it's metadata.
	 *
	 * @param modelType
	 * 			the {@link Class} of the model object whose <b>CREATE</b> 
	 * 			SQL statement(s) are to be generated
	 * 
	 * @return a {@link StringBuilder} which contains a concatenation of 
	 * 		   all the creation statements 
	 * 
	 * @throws DDLException
	 * 			if the SQL statement(s) failed to be generated
	 * 
	 * @since 1.1.0
	 */
	<Model extends Object> StringBuilder create(Class<Model> modelType) throws DDLException;
	
	/**
	 * <p>Generate the <b>DROP</b> statements for a relation identified 
	 * by the given model.
	 *
	 * @param modelType
	 * 			the {@link Class} of the model object whose <b>DROP</b> 
	 * 			SQL statement(s) are to be generated
	 * 
	 * @return a {@link StringBuilder} which contains a concatenation of 
	 * 		   all the drop statements 
	 * 
	 * @throws DDLException
	 * 			if the SQL statement(s) failed to be generated
	 * 
	 * @since 1.1.0
	 */
	<Model extends Object> StringBuilder drop(Class<Model> modelType) throws DDLException;
}
