package net.sgoliver.jrtftree.test;
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


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import net.sgoliver.jrtftree.core.RtfMerger;
import net.sgoliver.jrtftree.core.RtfTree;

public class MergeTest //In Sync
{
	@Test
	public void MergeDocuments() 
	{
		RtfMerger merger = new RtfMerger(getClass().getClassLoader().getResource("testdocs/merge-template.rtf").getFile());
		merger.addPlaceHolder("$doc1$", getClass().getClassLoader().getResource("testdocs/merge-doc1.rtf").getFile());
		merger.addPlaceHolder("$doc2$", getClass().getClassLoader().getResource("testdocs/merge-doc2.rtf").getFile());

		assertEquals(merger.getPlaceholders().size(), 2);

		merger.addPlaceHolder("$doc3$", getClass().getClassLoader().getResource("testdocs/merge-doc2.rtf").getFile());

		assertEquals(merger.getPlaceholders().size(), 3);

		merger.removePlaceHolder("$doc3$");

		assertEquals(merger.getPlaceholders().size(), 2);

		RtfTree tree = merger.merge();
		
		try
		{
			String directorio=getClass().getClassLoader().getResource("testdocs").getPath();
			tree.saveRtf(directorio+"/merge-result-1.rtf");
		}
		catch(IOException ex)
		{
			;
		}

		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/merge-result-1.rtf").getFile());
		String rtf3 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf3.txt").getFile());

		assertEquals(rtf3.trim(), rtf1.trim());
	}
	
	@Test
	public void MergeDocumentsInMemory() 
	{
		RtfMerger merger = new RtfMerger();
		
		RtfTree tree = new RtfTree();
		tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/merge-template.rtf").getFile());
		
		merger.setTemplate(tree);
		
		RtfTree ph1 = new RtfTree();
        ph1.loadRtfFile(getClass().getClassLoader().getResource("testdocs/merge-doc1.rtf").getFile());

        RtfTree ph2 = new RtfTree();
        ph2.loadRtfFile(getClass().getClassLoader().getResource("testdocs/merge-doc2.rtf").getFile());
		
		merger.addPlaceHolder("$doc1$", ph1);
		merger.addPlaceHolder("$doc2$", ph2);

		assertEquals(merger.getPlaceholders().size(), 2);

		RtfTree ph3 = new RtfTree();
        ph3.loadRtfFile(getClass().getClassLoader().getResource("testdocs/merge-doc2.rtf").getFile());
		
		merger.addPlaceHolder("$doc3$", ph3);

		assertEquals(merger.getPlaceholders().size(), 3);

		merger.removePlaceHolder("$doc3$");

		assertEquals(merger.getPlaceholders().size(), 2);

		RtfTree resTree = merger.merge();
		
		try
		{
			String directorio=getClass().getClassLoader().getResource("testdocs").getPath();
			resTree.saveRtf(directorio+"/merge-result-2.rtf");
		}
		catch(IOException ex)
		{
			;
		}

		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/merge-result-2.rtf").getFile());
		String rtf3 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf3.txt").getFile());

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
