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


import static com.lonepulse.packrat.util.ParamUtils.throwIfNull;
import static com.lonepulse.packrat.util.ParamUtils.throwIfNullOrEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>An immutable fluent facade which extracts and filters an array of {@link Fields}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class Fields {

	
	/**
	 * <p>The array of fields which are to be filtered.
	 */
	private Collection<Field> fields = null;
	
	
	/**
	 * <p>Creates a new instance of {@link Fields} from the given target object by 
	 * extracting all its member {@link Field}s.</p> 
	 * 
	 * <p>See {@link Class#getDeclaredFields()}.</p>
	 *
	 * @param target
	 * 			the object whose fields are to extracted
	 * 
	 * @return a new instance of {@link Fields} with the extracted {@link Field}s
	 * 
	 * @since 1.1.0
	 */
	public static final Fields valueOf(Object target) {
		
		return new Fields(target.getClass().getDeclaredFields());
	}
	
	/**
	 * <p>Instantiates a new instance of {@link Field}s with the given {@link Field} array.
	 * 
	 * @param fields
	 * 			the array of {@link Field}s which are to be filtered
	 *
	 * @since 1.1.0
	 */
	public Fields(Field[] fields) {
		
		throwIfNull(Field[].class, fields);
		this.fields = Arrays.asList(fields);
	}
	
	/**
	 * <p>Instantiates a new instance of {@link Field}s with the given {@link Field} collection.
	 * 
	 * @param fields
	 * 			the {@link Collection} of {@link Field}s which are to be filtered
	 *
	 * @since 1.1.0
	 */
	public Fields(Collection<Field> fields) {
		
		throwIfNull(Collection.class, fields);
		this.fields = Collections.unmodifiableCollection(fields);
	}
	
	/**
	 * <p>Filters the fields which are annotated with the given annotation and returns 
	 * a new instance of {@link Fields} that wrap the filtered collection.
	 * 
	 * @param annotation
	 * 			the {@link Field}s containing this annotation type will be filtered
	 * 
	 * @return a <b>new instance</b> of {@link Fields} which wraps the filtered {@link Field}s
	 * 
	 * @since 1.1.0
	 */
	public Fields annotatedWith(Class<Annotation> annotation) {
		
		throwIfNull(annotation);
		
		List<Field> filteredFields = new ArrayList<Field>();
		
		for (Field field : fields) {
			
			if(field.isAnnotationPresent(annotation)) {
				
				filteredFields.add(field);
			}
		}
		
		return new Fields(filteredFields);
	}
	
	/**
	 * <p>Filters the fields whose name equals (case-sensitive) the given name and returns 
	 * a new instance of {@link Fields} that wrap the filtered collection. 
	 *
	 * @param fieldName
	 * 			the {@link Field}s having this name will be filtered
	 * 
	 * @return a <b>new instance</b> of {@link Fields} which wraps the filtered {@link Field}s
	 * 
	 * @since 1.1.0
	 */
	public Fields nameEquals(String fieldName) {
		
		throwIfNullOrEmpty(fieldName);
		
		List<Field> filteredFields = new ArrayList<Field>();
		
		for (Field field : fields) {
			
			if(field.getName().equals(fieldName)) {
				
				filteredFields.add(field);
			}
		}
		
		return new Fields(filteredFields);
	}
	
	/**
	 * <p>Filters the fields whose case insensitive name equals the given name and returns 
	 * a new instance of {@link Fields} that wrap the filtered collection. 
	 *
	 * @param fieldName
	 * 			the {@link Field}s having this case insensitive name will be filtered
	 * 
	 * @return a <b>new instance</b> of {@link Fields} which wraps the filtered {@link Field}s
	 * 
	 * @since 1.1.0
	 */
	public Fields nameEqualsIgnoreCase(String fieldName) {
		
		throwIfNullOrEmpty(fieldName);
		
		List<Field> filteredFields = new ArrayList<Field>();
		
		for (Field field : fields) {
			
			if(field.getName().equalsIgnoreCase(fieldName)) {
				
				filteredFields.add(field);
			}
		}
		
		return new Fields(filteredFields);
	}
	
	/**
	 * <p>Retrieves all the {@link Field}s which are wrapped by this instance of {@link Fields} 
	 * as an <b>unmodifiable</b> collection.
	 *
	 * @return the fields which are wrapped by this instance of {@link Fields}
	 */
	public Collection<Field> all() {
		
		return fields;
	}
}
