package com.lonepulse.packrat.util;

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
 * <p>Common utilities which are used for constructing SQL statements.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class SQLUtils {

	/**
	 * <p>Instantiation is nonsensical.
	 *
	 * @since 1.1.0
	 */
	private SQLUtils() {}
	
	/**
	 * <p>Takes an argument and returns a quoted version of it with a 
	 * prepended and appended single quote.
	 *
	 * @param textualArg
	 * 			an argument which is to be enclosed in single quotes
	 * 
	 * @return the quoted argument
	 * 
	 * @since 1.1.0
	 */
	public static final String quote(String textualArg) {
		
		return "'" + textualArg + "'";
	}
	
	/**
	 * <p>Takes an argument and returns a single-quoted version if it's textual and 
	 * not quoted already.
	 *
	 * @param potentialTextualArg
	 * 			an argument which could be numeric or textual
	 * 
	 * @return the argument quoted, if it is textual
	 * 
	 * @since 1.1.0
	 */
	public static final String quoteIfTextual(String potentialTextualArg) {
		
		try {
			
			if(potentialTextualArg.startsWith("'") && potentialTextualArg.endsWith("'")) {
				
				return potentialTextualArg;
			}
			
			Double.parseDouble(potentialTextualArg);
			
			return potentialTextualArg;
		}
		catch(NumberFormatException nfe) {
			
			return "'" + potentialTextualArg + "'";
		}
	}
	
}
