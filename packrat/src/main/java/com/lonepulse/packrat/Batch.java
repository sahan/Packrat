package com.lonepulse.packrat;

/*
 * #%L
 * Packrat
 * %%
 * Copyright (C) 2013 - 2014 Lonepulse
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

import java.util.Collection;

public interface Batch<T extends Entity<T>> {
	
	void create(Collection<T> entities);
	
	void read(Collection<T> entities);
	
	void update(Collection<T> entities);
	
	void delete(Collection<T> entities);
	
	Collection<T> find(Query query);
	
	Collection<T> all();
	
	void purge();
}
