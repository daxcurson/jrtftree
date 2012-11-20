/********************************************************************************
 *   This file is part of NRtfTree Library.
 *
 *   JRtfTree Library is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation; either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JRtfTree Library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program. If not, see <http://www.gnu.org/licenses/>.
 ********************************************************************************/

/********************************************************************************
 * Library:		JRtfTree
 * Version:     v0.3
 * Date:		15/11/2012
 * Copyright:   2006-2012 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * GitHub:		https://github.com/sgolivernet/jrtftree
 * Class:		MergeTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import net.sgoliver.jrtftree.core.RtfMerger;
import net.sgoliver.jrtftree.core.RtfTree;

import org.junit.Test;


public class MergeTest 
{
	@Test
	public void MergeDocuments() 
	{
		RtfMerger merger = new RtfMerger("test\\testdocs\\merge-template.rtf");
		merger.addPlaceHolder("$doc1$", "test\\testdocs\\merge-doc1.rtf");
		merger.addPlaceHolder("$doc2$", "test\\testdocs\\merge-doc2.rtf");

		assertEquals(merger.getPlaceholders().size(), 2);

		merger.addPlaceHolder("$doc3$", "test\\testdocs\\merge-doc2.rtf");

		assertEquals(merger.getPlaceholders().size(), 3);

		merger.removePlaceHolder("$doc3$");

		assertEquals(merger.getPlaceholders().size(), 2);

		RtfTree tree = merger.merge();
		
		try
		{
			tree.saveRtf("test\\testdocs\\merge-result.rtf");
		}
		catch(IOException ex)
		{
			;
		}

		String rtf1 = leerFichero("test\\testdocs\\merge-result.rtf");
		String rtf3 = leerFichero("test\\testdocs\\rtf3.txt");

		assertEquals(rtf3.trim(), rtf1.trim());
	}
	
	private String leerFichero(String path)
	{
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		
		Scanner scanner = null;
		
		try {
		    scanner = new Scanner(new FileInputStream(path), "UTF-8");

			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine() + NL);
			}
		} catch(IOException ex) {
			System.out.println("Error lectura.");
		}
		finally {
			scanner.close();
		}

		return text.toString();
	}
}
