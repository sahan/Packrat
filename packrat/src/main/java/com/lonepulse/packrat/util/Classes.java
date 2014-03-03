package com.lonepulse.packrat.util;

/*
 * #%L
 * Sneeze
 * %%
 * Copyright (C) 2014 Lonepulse
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>Fluent filtering for a collection of {@link Class}es.</p> 
 * 
 * <p><b>Note</b> that two {@link Class}es are determined to be equal if they have the same fully 
 * qualified class name.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @since 0.1.0
 * <br><br>
 * @author <a href="http://sahan.me">Lahiru Sahan Jayasinghe</a>
 */
public final class Classes implements Iterable<Class<?>> {
	
	
	/**
	 * <p>This contract defines a strategy for filtering the {@link Class}es within an instance 
	 * of {@link Classes} by evaluating each {@link Class} to determine if it should be included 
	 * in the filtered result.</p>
	 * 
	 * <p>Instances of {@link Criterion} can be used via {@link Classes#filter(Criterion)}.</p>
	 * 
	 * @version 1.1.0
	 * <br><br>
	 * @since 0.1.0
	 * <br><br>
	 * @author <a href="http://sahan.me">Lahiru Sahan Jayasinghe</a>
	 */
	public static interface Criterion {
		
		boolean evaluate(Class<?> clazz);
	}
	
	private List<Class<?>> filter(Criterion criterion) {
		
		List<Class<?>> filteredClasses = new ArrayList<Class<?>>();
		
		for (Class<?> clazz : classes) {
			
			if(criterion.evaluate(clazz)) {
				
				filteredClasses.add(clazz);
			}
		}
		
		return filteredClasses;
	}
	
	
	private Collection<Class<?>> classes = null;
	
	
	/**
	 * <p>Creates a new instance of {@link Classes} from the given array of {@link Class}es.</p> 
	 * 
	 * @param classes
	 * 			the array of {@link Class}es to filter
	 * <br><br>
	 * @return a new instance of {@link Classes} for the given {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public static Classes from(Class<?>[] classes) {
		
		return new Classes(classes == null? new ArrayList<Class<?>>() : Arrays.asList(classes));
	}
	
	/**
	 * <p>Creates a new instance of {@link Classes} from the given {@link Collection} of {@link Class}es.</p>
	 * 
	 * @param classes
	 * 			the {@link Collection} of {@link Class}es to filter
	 * <br><br>
	 * @return a new instance of {@link Classes} for the given {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public static Classes from(Collection<Class<?>> classes) {
		
		return new Classes(classes == null? new ArrayList<Class<?>>() : classes);
	}
	
	private Classes(Collection<Class<?>> classes) {
		
		this.classes = classes;
	}
	
	/**
	 * <p>Filters the {@link Class}es which are annotated with the given annotation and returns a new 
	 * instance of {@link Classes} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Class}es annotated with this type will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes annotatedWith(final Class<? extends Annotation> annotation) {
		
		if(annotation == null) {
			
			return this;
		}
		
		return new Classes(filter(new Criterion() {

			@Override
			public boolean evaluate(Class<?> clazz) {
				
				return clazz.isAnnotationPresent(annotation);
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Class}es which are annotated with <b>all</b> of the given annotations and 
	 * returns a new instance of {@link Classes} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Class}es annotated with <b>all</b> these types will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes annotatedWithAll(final Class<? extends Annotation>... annotations) {
		
		if(annotations == null || annotations.length == 0) {
			
			return this;
		}
		
		return new Classes(filter(new Criterion() {

			@Override
			public boolean evaluate(Class<?> clazz) {
				
				boolean hasAllAnnotations = true;
				
				for (Class<? extends Annotation> annotation : annotations) {
				
					if(!clazz.isAnnotationPresent(annotation)) {
						
						hasAllAnnotations = false;
						break;
					}
				}
				
				return hasAllAnnotations;
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Class}es which are annotated with <b>any</b> of the given annotations and 
	 * returns a new instance of {@link Classes} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Class}es annotated with <b>any</b> of these types will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes annotatedWithAny(final Class<? extends Annotation>... annotations) {
		
		if(annotations == null || annotations.length == 0) {
			
			return this;
		}
		
		return new Classes(filter(new Criterion() {

			@Override
			public boolean evaluate(Class<?> clazz) {
				
				for (Class<? extends Annotation> annotation : annotations) {
					
					if(clazz.isAnnotationPresent(annotation)) {
						
						return true;
					}
				}
				
				return false;
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Class}es whose <b>case insensitive simple</b> name equals the given name 
	 * and returns a new instance of {@link Classes} that wrap the filtered collection.</p> 
	 *
	 * @param className
	 * 			the {@link Class}es having this name will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes named(final String className) {
		
		if(className == null || className.isEmpty()) {
			
			return this;
		}
		
		return new Classes(filter(new Criterion() {

			@Override
			public boolean evaluate(Class<?> clazz) {
				
				return clazz.getSimpleName().equalsIgnoreCase(className);
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Class}es whose <b>case insensitive fully qualified</b> name equals the given 
	 * name and returns a new instance of {@link Classes} that wrap the filtered collection.</p> 
	 *
	 * @param fullyQualifiedClassName
	 * 			the {@link Class}es having this name will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 * 
	 * TODO @sahan update to accept wildcards
	 */
	public Classes fullyNamed(final String fullyQualifiedClassName) {
		
		if(fullyQualifiedClassName == null || fullyQualifiedClassName.isEmpty()) {
			
			return this;
		}
		
		return new Classes(filter(new Criterion() {

			@Override
			public boolean evaluate(Class<?> clazz) {
				
				return clazz.getName().equalsIgnoreCase(fullyQualifiedClassName);
			}
		}));
	}
	
	/**
	 * <p>Finds the <b>difference</b> between this instance and a supplied instance. Returns a new 
	 * instance of {@link Classes} with only those {@link Class}es which are unique to this instance.</p> 
	 * 
	 * <p><pre>
	 * Difference can be expressed as, 
	 * <code>
	 * A - B = { x &isin; A and x &notin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Classes instance
	 * B = supplied Classes instance
	 * x = any class from the resulting Classes instance 
	 * </pre></p>
	 *
	 * @param classes
	 * 			the instance of {@link Classes} whose common items are subtracted from this instance
	 * <br><br>
	 * @return a new instance of {@link Classes} which wraps only the only those {@link Class}es which 
	 * 		   are unique to this instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes difference(Classes classes) {
		
		if(classes == null) {
			
			return this;
		}
		
		Set<Class<?>> view = new HashSet<Class<?>>(this.classes);
		view.removeAll(new HashSet<Class<?>>(classes.classes));
		
		return new Classes(view);
	}
	
	/**
	 * <p>Finds the <b>union</b> between this instance and a supplied instance. Returns a new instance 
	 * of {@link Classes} which contains a <b>set of all</b> the {@link Class}es which in both this instance 
	 * and the supplied instance.</p>
	 * 
	 * <p><pre>
	 * Union can be expressed as, 
	 * <code>
	 * A &cup; B = { x : x &isin; A or x &isin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Classes instance
	 * B = supplied Classes instance
	 * x = any class from the resulting Classes instance 
	 * </pre></p>
	 * 
	 * @param classes
	 * 			the instance of {@link Classes} whose {@link Class}es are added to this instance
	 * <br><br>
	 * @return a new instance of {@link Classes} which wraps all the unique {@link Class}es in this 
	 * 		   instance and the given instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes union(Classes classes) {
		
		if(classes == null) {
			
			return this;
		}
		
		Set<Class<?>> view = new HashSet<Class<?>>(this.classes);
		view.addAll(new HashSet<Class<?>>(classes.classes));
		
		return new Classes(view);
	}
	
	/**
	 * <p>Finds the <b>intersection</b> between this instance and a supplied instance. Returns a new 
	 * instance of {@link Classes} which contains all the {@link Class}es <b>common</b> to both instances.</b>
	 * 
	 * <p><pre>
	 * Intersection can be expressed as, 
	 * <code>
	 * A &cap; B = { x : x &isin; A and x &isin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Classes instance
	 * B = supplied Classes instance
	 * x = any class from the resulting Classes instance 
	 * </pre></p>
	 * 
	 * @param classes
	 * 			the instance of {@link Classes} whose common items are discovered
	 * <br><br>
	 * @return a new instance of {@link Classes} which wraps only the only those {@link Class}es which 
	 * 		   are common between this instance and the passed instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes intersection(Classes classes) {
		
		if(classes == null) {
			
			return this;
		}
		
		Set<Class<?>> view = new HashSet<Class<?>>(this.classes);
		view.retainAll(new HashSet<Class<?>>(classes.classes));
		
		return new Classes(view);
	}
	
	/**
	 * <p>Filters the {@link Class}es which match the given {@link Criterion} and returns a new instance 
	 * of {@link Classes} that wrap the filtered collection.</p>
	 *
	 * @param criterion
	 * 			the {@link Criterion} whose evaluation determines the filtered class
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Classes} which wraps the filtered {@link Class}es
	 * <br><br>
	 * @since 0.1.0
	 */
	public Classes matching(Criterion criterion) {
		
		return new Classes(filter(criterion));
	}
	
	/**
	 * <p>Groups all {@link Field}s in each of the classes in this instance into a {@link Collection} 
	 * of {@link Fields}.</p>
	 *
	 * @return an unmodifiable collection of {@link Fields} which groups all the {@link Field}s for 
	 * 		   each of the {@link Class}es
	 *
	 * @since 0.1.0
	 */
	public Collection<Fields> fields() {
		
		List<Fields> fieldsOfFields = new ArrayList<Fields>();
		
		for(Class<?> clazz : this) {
			
			fieldsOfFields.add(Fields.in(clazz));
		}
		
		return Collections.unmodifiableList(fieldsOfFields);
	}
	
	/**
	 * <p>Groups all {@link Field}s in each of the classes in this instance into a {@link Collection} 
	 * of {@link Fields}.</p>
	 *
	 * @return an unmodifiable collection of {@link Methods} which groups all the {@link Method}s for 
	 * 		   each of the {@link Class}es
	 *
	 * @since 0.1.0
	 */
	public Collection<Methods> methods() {
		
		List<Methods> methodsOfMethods = new ArrayList<Methods>();
		
		for(Class<?> clazz : this) {
			
			methodsOfMethods.add(Methods.in(clazz));
		}
		
		return Collections.unmodifiableList(methodsOfMethods);
	}
	
	/**
	 * <p>Allows the {@link Class}es enveloped by this instance of {@link Classes} to be traversed 
	 * sequentially using the returned {@link Iterator}.</p> 
	 * 
	 * <p><b>Note</b> this {@link Iterator} does not allow the underlying {@link Class}es to be modified, 
	 * for example using {@link Iterator#remove()}. Doing so will result in an 
	 * {@link UnsupportedOperationException}.</p>
	 *
	 * @return the iterator which allows the enclosed {@link Class}es to traversed sequentially
	 * <br><br>
	 * @since 0.1.0
	 */
	@Override
	public Iterator<Class<?>> iterator() {
		
		return this.classes.iterator();
	}
}
