/**
 * PromniCAT - Collection and Analysis of Business Process Models
 * Copyright (C) 2012 Cindy Fähnrich, Tobias Hoppe, Andrina Mascher, Christian Kieschnick
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
package de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.test;


import org.junit.Test;

import de.uni_potsdam.hpi.bpt.ai.diagram.DiagramBuilder;
import de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.InvalidModelException;
import de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.UnsupportedModelException;
import de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.correctors.AbstractCorrector;
import de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.test.util.TestModelBuilder;
import de.uni_potsdam.hpi.bpt.promnicat.utilityUnits.processModelCorrection.wrapper.DiagramWrapper;

/**
 * Test class for all subclasses of {@link AbstractCorrector}.
 * @author Christian Kieschnick
 *
 */
public class DiagramWrapperTest {
	
	@Test
	public void Duplicate() throws InvalidModelException, UnsupportedModelException, Exception{
		DiagramWrapper wrapper = new DiagramWrapper(
				TestModelBuilder.getModelFromFile("improved_bundled_edges"));
		assert wrapper.duplicate() != null;
		wrapper = new DiagramWrapper(
				DiagramBuilder.parseJson(
						TestModelBuilder.readFromFile("resources/testModels/improved_bundled_edges.json")));
		assert wrapper.duplicate() != null;
	}
	
}
