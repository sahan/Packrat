package com.lonepulse.packrat.config;

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

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * <p>This utility class offers services for reading the configuration properties 
 * from the manifest related to a single persistence unit.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @since 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class MetaDataReader {

	
	/**
	 * <p>This enum identifies the name of the property to retrieve from the manifest.</p>
	 * 
	 * @version 1.1.0
	 * <br><br>
	 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
	 */
	public static enum PROPERTY {
		
		/**
		 * <p>The name of the persitence unit. All flat-files will follow the 
		 * naming convention {@code <name>.db}.
		 * 
		 * @since 1.1.0
		 */
		NAME("name"),
		
		/**
		 * <p>The current version of the database. To perform database upgrades 
		 * simply increment this number in the manifest.
		 * 
		 * @since 1.1.0
		 */
		VERSION("version");
		
		
		private final String key;
		
		
		private PROPERTY(String key) {
			
			this.key = key;
		}

		/**
		 * <p>Retrieves the name of the property to be read from the manifests.</p>
		 *
		 * @return the name of the property defined in the manifest
		 * 
		 * @since 1.1.0
		 */
		public String getKey() {
			
			return key;
		}
	}
	
	
	private MetaDataReader() {}
	
	
	/**
	 * <p>Takes a {@link PROPERTY} and reads it's value form the manifest.</p>
	 * 
	 * @param context
	 * 			the {@link Context} of the caller
	 * 
	 * @param property
	 * 			the {@link PROPERTY} identifier
	 * 
	 * @return the value of the property
	 * 
	 * @throws MetaDataNotFoundException
	 * 			if the specified property is missing from manifest
	 * 
	 * @throws MetaDataReadException
	 * 			if the specified property failed to be read from the manifest
	 *
	 * @since 1.1.0
	 */
	public static final String read(Context context, PROPERTY property) {
		
		try {
		
			ApplicationInfo applicationInfo = context.getPackageManager()
				.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			
			String value = (String) applicationInfo.metaData.get(property.getKey());
			
			if(value == null || "".equals(value)) {
				
				throw new MetaDataNotFoundException(value);
			}
			
			return value;
		}
		catch (Exception e) {
			
			throw new MetaDataReadException(property, e);
		}
	}
}
