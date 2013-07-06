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
 * Class:		LoadRtfTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import net.sgoliver.jrtftree.core.RtfTree;

import org.junit.Test;

public class LoadRtfTest //In Sync
{
	@Test
	public void loadSimpleDocFromFile()
	{
		RtfTree tree = new RtfTree();
		
		int res = tree.loadRtfFile("test\\testdocs\\testdoc1.rtf");
		
		String strTree1 = leerFichero("test\\testdocs\\result1-1.txt");
		String strTree2 = leerFichero("test\\testdocs\\result1-2.txt");
		String rtf1 = leerFichero("test\\testdocs\\rtf1.txt");
		String text1 = leerFichero("test\\testdocs\\text1.txt");
		
		assertEquals(0, res);
        assertFalse(tree.getMergeSpecialCharacters());
        assertEquals(strTree1.trim(), tree.toString().trim());
        assertEquals(strTree2.trim(), tree.toStringEx().trim());
        assertEquals(rtf1.trim(), tree.getRtf().trim());
        assertEquals(text1.trim(), tree.getText().trim());
	}
	
    @Test
	public void loadImageDocFromFile() 
    {
		RtfTree tree = new RtfTree();

		int res = tree.loadRtfFile("test\\testdocs\\testdoc3.rtf");

		String rtf5 = leerFichero("test\\testdocs\\rtf5.txt");
		String text2 = leerFichero("test\\testdocs\\text2.txt");

		assertEquals(0, res);
		assertFalse(tree.getMergeSpecialCharacters());
		assertEquals(rtf5.trim(), tree.getRtf().trim());
		assertEquals(text2.trim(), tree.getText().trim());
	}
    
    @Test
	public void loadSimpleDocMergeSpecialFromFile() 
    {
		RtfTree tree = new RtfTree();

		tree.setMergeSpecialCharacters(true);

		int res = tree.loadRtfFile("test\\testdocs\\testdoc1.rtf");

		String strTree1 = leerFichero("test\\testdocs\\result1-3.txt");
		String strTree2 = leerFichero("test\\testdocs\\result1-4.txt");
		String rtf1 = leerFichero("test\\testdocs\\rtf1.txt");
		String text1 = leerFichero("test\\testdocs\\text1.txt");

		assertEquals(0, res);
		assertTrue(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

    @Test
	public void loadSimpleDocFromString() 
    {
		RtfTree tree = new RtfTree();

		String strDoc = leerFichero("test\\testdocs\\testdoc1.rtf");

		int res = tree.loadRtfText(strDoc);

		String strTree1 = leerFichero("test\\testdocs\\result1-1.txt");
		String strTree2 = leerFichero("test\\testdocs\\result1-2.txt");
		String rtf1 = leerFichero("test\\testdocs\\rtf1.txt");
		String text1 = leerFichero("test\\testdocs\\text1.txt");

		assertEquals(0, res);
		assertFalse(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

	@Test
	public void loadSimpleDocMergeSpecialFromString() 
	{
		RtfTree tree = new RtfTree();
		tree.setMergeSpecialCharacters(true);

		String strDoc = leerFichero("test\\testdocs\\testdoc1.rtf");

		int res = tree.loadRtfText(strDoc);

		String strTree1 = leerFichero("test\\testdocs\\result1-3.txt");
		String strTree2 = leerFichero("test\\testdocs\\result1-4.txt");
		String rtf1 = leerFichero("test\\testdocs\\rtf1.txt");
		String text1 = leerFichero("test\\testdocs\\text1.txt");
		
		assertEquals(0, res);
		assertTrue(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
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
