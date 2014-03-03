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
 * <p>Fluent filtering for a collection of {@link Method}s.</p> 
 * 
 * <p><b>Note</b> that two {@link Method}s are determined to be equal of and only if they were declared 
 * in the same {@link Class}, with the same name and parameter types and return type.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @since 0.1.0
 * <br><br>
 * @author <a href="http://sahan.me">Lahiru Sahan Jayasinghe</a>
 */
public final class Methods implements Iterable<Method> {
	
	
	/**
	 * <p>This contract defines a strategy for filtering the {@link Method}s within an instance 
	 * of {@link Methods} by evaluating each {@link Method} to determine if it should be included 
	 * in the filtered result.</p>
	 * 
	 * <p>Instances of {@link Criterion} can be used via {@link Methods#filter(Criterion)}.</p>
	 * 
	 * @version 1.1.0
	 * <br><br>
	 * @since 0.1.0
	 * <br><br>
	 * @author <a href="http://sahan.me">Lahiru Sahan Jayasinghe</a>
	 */
	public static interface Criterion {
		
		boolean evaluate(Method method);
	}

	
	private Collection<Method> methods = null;
	

	private List<Method> filter(Criterion criterion) {
		
		List<Method> filteredMethods = new ArrayList<Method>();
		
		for (Method method : methods) {
			
			if(criterion.evaluate(method)) {
				
				filteredMethods.add(method);
			}
		}
		
		return filteredMethods;
	}
	
	/**
	 * <p>Creates a new instance of {@link Methods} from the given <b>object</b> by extracting all its 
	 * member {@link Method}s.</p> 
	 * 
	 * <p>See {@link Object#getClass()} and {@link Class#getDeclaredMethods()}.</p>
	 *
	 * @param target
	 * 			the object whose methods are to be extracted
	 * <br><br>
	 * @return a new instance of {@link Methods} for the {@link Method}s on the given object
	 * <br><br>
	 * @since 0.1.0
	 */
	public static Methods in(Object target) {
		
		return new Methods(target.getClass().getDeclaredMethods());
	}
	
	/**
	 * <p>Creates a new instance of {@link Methods} from the given <b>{@link Class}</b> by extracting 
	 * all its member {@link Method}s.</p> 
	 * 
	 * <p>See {@link Class#getDeclaredMethods()}.</p>
	 *
	 * @param target
	 * 			the {@link Class} whose methods are to be extracted
	 * <br><br>
	 * @return a new instance of {@link Methods} for the {@link Method}s on the given {@link Class}
	 * <br><br>
	 * @since 0.1.0
	 */
	public static Methods in(Class<? extends Object> target) {
		
		return new Methods(target.getDeclaredMethods());
	}
	
	private Methods(Method[] methods) {
		
		this.methods = Collections.unmodifiableList(
				Arrays.asList(methods == null? new Method[]{} :methods));
	}
	
	private Methods(Collection<Method> methods) {
		
		this.methods = Collections.unmodifiableCollection(
				methods == null? new ArrayList<Method>() :methods);
	}
	
	/**
	 * <p>Filters the {@link Method}s which are annotated with the given annotation and returns a new 
	 * instance of {@link Methods} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Method}s annotated with this type will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods annotatedWith(final Class<? extends Annotation> annotation) {
		
		if(annotation == null) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				return method.isAnnotationPresent(annotation);
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s which are annotated with <b>all</b> of the given annotations and 
	 * returns a new instance of {@link Methods} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Method}s annotated with <b>all</b> these types will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods annotatedWithAll(final Class<? extends Annotation>... annotations) {
		
		if(annotations == null || annotations.length == 0) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				boolean hasAllAnnotations = true;
				
				for (Class<? extends Annotation> annotation : annotations) {
				
					if(!method.isAnnotationPresent(annotation)) {
						
						hasAllAnnotations = false;
						break;
					}
				}
				
				return hasAllAnnotations;
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s which are annotated with <b>any</b> of the given annotations and 
	 * returns a new instance of {@link Methods} that wrap the filtered collection.</p>
	 * 
	 * @param annotation
	 * 			the {@link Method}s annotated with <b>any</b> of these types will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods annotatedWithAny(final Class<? extends Annotation>... annotations) {
		
		if(annotations == null || annotations.length == 0) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				for (Class<? extends Annotation> annotation : annotations) {
					
					if(method.isAnnotationPresent(annotation)) {
						
						return true;
					}
				}
				
				return false;
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s whose <b>case insensitive</b> name equals the given name and returns 
	 * a new instance of {@link Methods} that wrap the filtered collection.</p> 
	 *
	 * @param methodName
	 * 			the {@link Method}s having this name will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods named(final String methodName) {
		
		if(methodName == null || methodName.isEmpty()) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				return method.getName().equalsIgnoreCase(methodName);
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s whose <b>case sensitive</b> name equals the given name and returns 
	 * a new instance of {@link Methods} that wrap the filtered collection. 
	 *
	 * @param methodName
	 * 			the {@link Method}s having this case insensitive name will be filtered
	 * <br><br>
	 * @return a <b>new instance</b> of {@link } which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods strictlyNamed(final String methodName) {
		
		if(methodName == null || methodName.isEmpty()) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				return method.getName().equals(methodName);
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s whose return type <b>can be assigned</b> to the given type and 
	 * returns a new instance of {@link Methods} that wrap the filtered collection.</p> 
	 *
	 * @param type
	 * 			the {@link Class} return type of the {@link Method}s to be filtered 
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods returns(final Class<?> type) {
		
		if(type == null) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				return type.isAssignableFrom(method.getReturnType());
			}
		}));
	}
	
	/**
	 * <p>Filters the {@link Method}s whose return type <b>equals</b> the given type and returns a new 
	 * instance of {@link Methods} that wrap the filtered collection.</p> 
	 *
	 * @param type
	 * 			the {@link Class} type of the {@link Method}s to be filtered 
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods strictlyReturns(final Class<?> type) {
		
		if(type == null) {
			
			return this;
		}
		
		return new Methods(filter(new Criterion() {

			@Override
			public boolean evaluate(Method method) {
				
				return method.getReturnType().equals(type);
			}
		}));
	}
	
	/**
	 * <p>Finds the <b>difference</b> between this instance and a supplied instance. Returns a new 
	 * instance of {@link Methods} with only those {@link Method}s which are unique to this instance.</p> 
	 * 
	 * <p><pre>
	 * Difference can be expressed as, 
	 * <code>
	 * A - B = { x &isin; A and x &notin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Methods instance
	 * B = supplied Methods instance
	 * x = any method from the resulting Methods instance 
	 * </pre></p>
	 *
	 * @param methods
	 * 			the instance of {@link Methods} whose common items are subtracted from this instance
	 * <br><br>
	 * @return a new instance of {@link Methods} which wraps only the only those {@link Method}s which 
	 * 		   are unique to this instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods difference(Methods methods) {
		
		if(methods == null) {
			
			return this;
		}
		
		Set<Method> view = new HashSet<Method>(this.methods);
		view.removeAll(new HashSet<Method>(methods.methods));
		
		return new Methods(view);
	}
	
	/**
	 * <p>Finds the <b>union</b> between this instance and a supplied instance. Returns a new instance 
	 * of {@link Methods} which contains a <b>set of all</b> the {@link Method}s which in both this instance 
	 * and the supplied instance.</p>
	 * 
	 * <p><pre>
	 * Union can be expressed as, 
	 * <code>
	 * A &cup; B = { x : x &isin; A or x &isin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Methods instance
	 * B = supplied Methods instance
	 * x = any method from the resulting Methods instance 
	 * </pre></p>
	 * 
	 * @param methods
	 * 			the instance of {@link Methods} whose {@link Method}s are added to this instance
	 * <br><br>
	 * @return a new instance of {@link Methods} which wraps all the unique {@link Method}s in this 
	 * 		   instance and the given instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods union(Methods methods) {
		
		if(methods == null) {
			
			return this;
		}
		
		Set<Method> view = new HashSet<Method>(this.methods);
		view.addAll(new HashSet<Method>(methods.methods));
		
		return new Methods(view);
	}
	
	/**
	 * <p>Finds the <b>intersection</b> between this instance and a supplied instance. Returns a new 
	 * instance of {@link Methods} which contains all the {@link Method}s <b>common</b> to both instances.</b>
	 * 
	 * <p><pre>
	 * Intersection can be expressed as, 
	 * <code>
	 * A &cap; B = { x : x &isin; A and x &isin; B }
	 * </code> 
	 * where,
	 * 
	 * A = this Methods instance
	 * B = supplied Methods instance
	 * x = any method from the resulting Methods instance 
	 * </pre></p>
	 * 
	 * @param methods
	 * 			the instance of {@link Methods} whose common items are discovered
	 * <br><br>
	 * @return a new instance of {@link Methods} which wraps only the only those {@link Method}s which 
	 * 		   are common between this instance and the passed instance
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods intersection(Methods methods) {
		
		if(methods == null) {
			
			return this;
		}
		
		Set<Method> view = new HashSet<Method>(this.methods);
		view.retainAll(new HashSet<Method>(methods.methods));
		
		return new Methods(view);
	}
	
	/**
	 * <p>Filters the {@link Method}s which match the given {@link Criterion} and returns a new instance 
	 * of {@link Methods} that wrap the filtered collection.</p>
	 *
	 * @param criterion
	 * 			the {@link Criterion} whose evaluation determines the filtered method
	 * <br><br>
	 * @return a <b>new instance</b> of {@link Methods} which wraps the filtered {@link Method}s
	 * <br><br>
	 * @since 0.1.0
	 */
	public Methods matching(Criterion criterion) {
		
		return new Methods(filter(criterion));
	}
	
	/**
	 * <p>Groups all parameter types in each of the {@link Method}s in this instance into a {@link Collection} 
	 * of {@link Classes}.</p>
	 *
	 * @return an unmodifiable collection of {@link Classes} which groups all the parameter types for 
	 * 		   each of the {@link Method}s
	 *
	 * @since 0.1.0
	 */
	public Collection<Classes> params() {
		
		List<Classes> classesOfParameters = new ArrayList<Classes>();
		
		for(Method method : this) {
			
			classesOfParameters.add(Classes.from(method.getParameterTypes()));
		}
		
		return Collections.unmodifiableList(classesOfParameters);
	}
	
	/**
	 * <p>Allows the {@link Method}s enveloped by this instance of {@link Methods} to be traversed 
	 * sequentially using the returned {@link Iterator}.</p> 
	 * 
	 * <p><b>Note</b> this {@link Iterator} does not allow the underlying {@link Method}s to be modified, 
	 * for example using {@link Iterator#remove()}. Doing so will result in an 
	 * {@link UnsupportedOperationException}.</p>
	 *
	 * @return the iterator which allows the enclosed {@link Method}s to traversed sequentially
	 * <br><br>
	 * @since 0.1.0
	 */
	@Override
	public Iterator<Method> iterator() {
		
		return this.methods.iterator();
	}
}
