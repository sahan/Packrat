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
 * <p>A fluent facade which specifies the services offered for building SQL 
 * statements that <b>drop tables</b> using rudimentary string manipulation. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface DropTablePolicy {

	/**
	 * <p>Initializes the SQL build by specifying the name of the table 
	 * to be dropped. 
	 *
	 * @param tableName
	 * 			the name of the table to be dropped
	 * 
	 * @return the current state of the {@link DropTablePolicy}
	 * 
	 * @throws MalformedSQLException
	 * 			if this operation has resulted in a corrupt SQL statement
	 * 
	 * @since 1.1.0
	 */
	DropTablePolicy dropTable(String tableName) throws MalformedSQLException;
}
