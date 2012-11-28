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
package de.uni_potsdam.hpi.bpt.promnicat.persistenceAPI.db4o.config;

import java.io.IOException;
import java.util.logging.Logger;

import de.uni_potsdam.hpi.bpt.promnicat.persistenceAPI.db4o.PersistenceApiDb4o;
import de.uni_potsdam.hpi.bpt.promnicat.persistenceApi.IPersistenceApi;
import de.uni_potsdam.hpi.bpt.promnicat.persistenceApi.config.AbstractConfigurationParser;
import de.uni_potsdam.hpi.bpt.promnicat.util.Constants;

/**
 * This class parses the PromniCAT configuration file used for
 * Orient DB database instances.
 * @author Tobias Hoppe
 *
 */
public class ConfigurationParserDb4o extends AbstractConfigurationParser {
	
	private static Logger logger = Logger.getLogger(ConfigurationParserDb4o.class.getName());

	/**
	 * @param configPath the path to the configuration file being used. If an empty {@link String} is given,
	 *  the default path '{@link Constants#DEFAULT_CONFIG_PATH}' is used.
	 * @throws IOException if the configuration file could not be found.
	 */
	public ConfigurationParserDb4o(String configPath) throws IOException {
		super(configPath);
		ConfigurationParserDb4o.configPath = configPath;
	}
	
	/**
	 * A new {@link IPersistenceApi} with the specified access rights and
	 * database path is created. The configuration file must have been
	 * parsed before calling this method!
	 * 
	 * @param database specifies the type of {@link IPersistenceApi} to instantiate
	 * 
	 * @return a {@link IPersistenceApi} instance as specified in the given configuration.
	 */
	public static IPersistenceApi getDataBaseInstance(String configurationPath) {
		if(configurationPath == null || configurationPath.isEmpty()) {
			throw new IllegalArgumentException("The configuration file path must not be empty!");
		}
		if (properties == null || configPath == null || !configPath.equals(configurationPath)) {
			try {
				new ConfigurationParserDb4o(configurationPath);
			} catch (IOException e) {
				logger.severe("Configuration file could not be parsed!");
				e.printStackTrace();
			}
		}
		String dbPath = properties.getProperty(Constants.DB_Path);
		String dbUser = properties.getProperty(Constants.DB_USER);
		String dbPassword = properties.getProperty(Constants.DB_PASSWD);
		String dbPort = properties.getProperty(Constants.DB_PORT);
		if (dbPath != null && dbUser == null && dbPassword == null && dbPort == null) {
			return new PersistenceApiDb4o(dbPath);
		}
		if (dbPath == null || dbUser == null || dbPassword == null || dbPort == null) {
			throw new IllegalArgumentException("The provided configuration file is invalid.");
		}		
		return new PersistenceApiDb4o(dbPath, new Integer(dbPort), dbUser, dbPassword);
	}

	@Override
	public IPersistenceApi getDbInstance() {
		return getDataBaseInstance(ConfigurationParserDb4o.configPath);		
	}
}
