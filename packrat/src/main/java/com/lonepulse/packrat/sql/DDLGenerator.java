package com.lonepulse.packrat.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lonepulse.packrat.Entity;
import com.lonepulse.packrat.annotation.AutoIncrement;
import com.lonepulse.packrat.annotation.Column;
import com.lonepulse.packrat.annotation.Default;
import com.lonepulse.packrat.annotation.Id;
import com.lonepulse.packrat.annotation.NotNull;
import com.lonepulse.packrat.annotation.Table;
import com.lonepulse.packrat.annotation.Transient;
import com.lonepulse.packrat.annotation.Unique;
import com.lonepulse.packrat.util.Fields;

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
 * <p>A concrete implementation of {@link DDLPolicy} which generates <b>Data 
 * Definition Language</b> statements for creating and altering schemas.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class DDLGenerator implements DDLPolicy {


	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Model extends Entity<Model>> String create(Class<Model> type) throws DDLException {

		String table = getTableName(type);
		
		boolean createIfNotExists = type.isAnnotationPresent(Table.class)? 
			type.getAnnotation(Table.class).createIfNotExists() :true;
		
		CreateTablePolicy template = CreateTableSQLBuilder.newInstance().createTable(table);
		
		if(createIfNotExists) {
			
			template.ifNotExists();
		}
		
		for (Field field : Fields.in(type)) {
			
			if(!field.isAnnotationPresent(Transient.class)) {
				
				String column = field.getName();
				
				if(field.isAnnotationPresent(Column.class)) {
					
					Column columnMetadata = field.getAnnotation(Column.class);
					
					String name = columnMetadata.name();
					column = (name == null || "".equals(name))? column :name;
				}
					
				template.addColumn(column, TypeAffinity.resolve(field.getType()));
				
				List<SQL> columnConstraints = new ArrayList<SQL>();
				
				if(field.isAnnotationPresent(Id.class)) {
					
					columnConstraints.add(ColumnConstraint.PRIMARY_KEY);
					
					if(field.isAnnotationPresent(AutoIncrement.class)) {
						
						columnConstraints.add(ColumnConstraint.AUTO_INCREMENT);
					}
				}
				
				if(field.isAnnotationPresent(Unique.class)) {
					
					columnConstraints.add(ColumnConstraint.UNIQUE);
				}
				
				if(field.isAnnotationPresent(NotNull.class)) {
					
					columnConstraints.add(ColumnConstraint.NOT_NULL);
				}
				else if(field.isAnnotationPresent(Default.class)) {
					
					columnConstraints.add(ColumnConstraint.DEFAULT
						.withArgs(field.getAnnotation(Default.class).value()));
				}
				
				if(!columnConstraints.isEmpty()) {
				
					template.withColumnConstraints(columnConstraints);
				}
			}
		}
			
		return template.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Model extends Entity<Model>> String drop(Class<Model> type) throws DDLException {
		
		return DropTableSQLBuilder.newInstance()
				.dropTable(getTableName(type)).build();
	}
	
	private <Model extends Entity<Model>> String getTableName(Class<Model> type) {
		
		String table = type.getSimpleName();
		
		if(type.isAnnotationPresent(Table.class)) {
			
			Table tableMetadata = type.getAnnotation(Table.class);
			
			String name = tableMetadata.name();
			table = (name == null || "".equals(name))? table :name;
		}
		
		return table;
	}
}
