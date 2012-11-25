/**
 * PromniCAT - Collection and Analysis of Business Process Models
 * Copyright (C) 2012 Cindy Fähnrich, Tobias Hoppe, Andrina Mascher
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uni_potsdam.hpi.bpt.promnicat.persistenceApi;

import java.util.UUID;

/**
 * This class ensures the mapping of the databaseId.
 * Field types that can automatically be mapped to the database are:
 * boolean, String, short, int, double, long, float and their classes
 * References to other objects are stored also.
 * Arrays, Lists, Sets and Maps can be stored if their content is not too complex.
 * For details see: http://code.google.com/p/orient/wiki/Types
 * <br>
 * Make sure to add an empty constructor as well as getter and setter for all fields
 * in the format get<Field Name Capitalized> and set<Field Name Capitalized>.
 * 
 * @author Andrina Mascher, Tobias Hoppe
 *
 */
public interface IPojo {

	/**
	 * @return the database identifier used by the database, 
	 * if null save it in database first to create one
	 */
	public UUID getDbId();
	
	/**
	 * @return true if the database id is set, if not store it in database first to create one
	 */
	public boolean hasDbId();

}
