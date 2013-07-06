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
 * Class:		HeaderSectionTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Scanner;

import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.util.InfoGroup;
import net.sgoliver.jrtftree.util.RtfColorTable;
import net.sgoliver.jrtftree.util.RtfFontTable;
import net.sgoliver.jrtftree.util.RtfStyleSheetTable;
import net.sgoliver.jrtftree.util.RtfStyleSheetType;

import org.junit.BeforeClass;
import org.junit.Test;

public class HeaderSectionsTest //In Sync
{
	private static RtfTree tree = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		tree = new RtfTree();
        tree.loadRtfFile("test\\testdocs\\testdoc2.rtf");
	}

	@Test
	public void FontTableTest() 
	{
		RtfFontTable fontTable = tree.getFontTable();

		assertEquals(fontTable.size(), 3);
		assertEquals(fontTable.get(0), "Times New Roman");
		assertEquals(fontTable.get(1), "Arial");
		assertEquals(fontTable.get(2), "Arial");

		assertEquals(fontTable.indexOf("Times New Roman"), 0);
		assertEquals(fontTable.indexOf("Arial"), 1);
		assertEquals(fontTable.indexOf("nofont"), -1);
	}

	@Test
	public void ColorTableTest() 
	{
		RtfColorTable colorTable = tree.getColorTable();

		assertEquals(colorTable.size(), 3);
		assertEquals(colorTable.get(0), new Color(0, 0, 0));
		assertEquals(colorTable.get(1), new Color(0, 0, 128));
		assertEquals(colorTable.get(2), new Color(255, 0, 0));

		assertEquals(colorTable.IndexOf(new Color(0, 0, 0)), 0);
		assertEquals(colorTable.IndexOf(new Color(0, 0, 128)), 1);
		assertEquals(colorTable.IndexOf(new Color(255, 0, 0)), 2);
	}

	@Test
	public void StyleSheetTableTest() 
	{
		RtfStyleSheetTable styleTable = tree.getStyleSheetTable();

		assertEquals(styleTable.size(), 7);

		assertEquals(styleTable.get(0).getIndex(), 0);
		assertEquals(styleTable.get(0).getType(), RtfStyleSheetType.PARAGRAPH);
		assertEquals(styleTable.get(0).getName(), "Normal");
		assertEquals(styleTable.get(0).getNext(), 0);
		assertEquals(styleTable.get(0).getFormatting().size(), 25);

		assertEquals(styleTable.get(1).getIndex(), 1);
		assertEquals(styleTable.get(1).getType(), RtfStyleSheetType.PARAGRAPH);
		assertEquals(styleTable.get(1).getName(), "heading 1");
		assertEquals(styleTable.get(1).getNext(), 0);
		assertEquals(styleTable.get(1).getBasedOn(), 0);
		assertEquals(styleTable.get(1).getStyrsid(), 2310575);
		assertEquals(styleTable.get(1).getFormatting().size(), 33);

		assertEquals(styleTable.get(10).getIndex(), 10);
		assertEquals(styleTable.get(10).getType(), RtfStyleSheetType.CHARACTER);
		assertEquals(styleTable.get(10).getName(), "Default Paragraph Font");
		assertEquals(styleTable.get(10).isAdditive(), true);
		assertEquals(styleTable.get(10).isSemiHidden(), true);
		assertEquals(styleTable.get(10).getFormatting().size(), 0);

		assertEquals(styleTable.get(11).getIndex(), 11);
		assertEquals(styleTable.get(11).getType(), RtfStyleSheetType.TABLE);
		assertEquals(styleTable.get(11).getName(), "Normal Table");
		assertEquals(styleTable.get(11).getNext(), 11);
		assertEquals(styleTable.get(11).isSemiHidden(), true);
		assertEquals(styleTable.get(11).getFormatting().size(), 44);
	}

	@Test
	public void InfoGroupTest() 
	{
		InfoGroup infoGroup = tree.getInfoGroup();

		assertEquals(infoGroup.getTitle(), "Test NRtfTree Title");
		assertEquals(infoGroup.getSubject(), "Test NRtfTree Subject");
		assertEquals(infoGroup.getAuthor(), "Sgoliver (Author)");
		assertEquals(infoGroup.getKeywords(), "test;nrtftree;sgoliver");
		assertEquals(infoGroup.getDoccomm(), "This is a test comment.");
		assertEquals(infoGroup.getOperator(), "None");
		assertEquals(infoGroup.getCreationTime(), new GregorianCalendar(2008, 4, 28, 18, 52, 00));
		assertEquals(infoGroup.getRevisionTime(), new GregorianCalendar(2009, 5, 29, 20, 23, 00));
		assertEquals(infoGroup.getVersion(), 6);
		assertEquals(infoGroup.getEditingTime(), 4);
		assertEquals(infoGroup.getNofpages(), 1);
		assertEquals(infoGroup.getNofwords(), 12);
		assertEquals(infoGroup.getNofchars(), 59);
		assertEquals(infoGroup.getManager(), "Sgoliver (Admin)");
		assertEquals(infoGroup.getCompany(), "www.sgoliver.net");
		assertEquals(infoGroup.getCategory(), "Demos (Category)");
		assertEquals(infoGroup.getInternalVersion(), 24579);

		assertEquals(infoGroup.getComment(), "");
		assertEquals(infoGroup.getHlinkbase(), "");
		assertEquals(infoGroup.getId(), -1);
		assertNull(infoGroup.getLastPrintTime());
		assertNull(infoGroup.getBackupTime());

		String infoString = leerFichero("test\\testdocs\\infogroup.txt");

		assertEquals(infoString.trim(), infoGroup.toString().trim());
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
