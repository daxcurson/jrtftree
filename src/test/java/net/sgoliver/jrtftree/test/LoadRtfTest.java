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
 * Class:		LoadRtfTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import net.sgoliver.jrtftree.core.RtfTree;

public class LoadRtfTest // In Sync
{
	@Test
	public void loadSimpleDocFromFile() {
		RtfTree tree = new RtfTree();

		int res = tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

		String strTree1 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-1.txt").getFile());
		String strTree2 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-2.txt").getFile());
		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf1.txt").getFile());
		String text1 = leerFichero(getClass().getClassLoader().getResource("testdocs/text1.txt").getFile());

		assertEquals(0, res);
		assertFalse(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

	@Test
	public void loadImageDocFromFile() {
		RtfTree tree = new RtfTree();

		int res = tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc3.rtf").getFile());

		String rtf5 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf5.txt").getFile());
		String text2 = leerFichero(getClass().getClassLoader().getResource("testdocs/text2.txt").getFile());

		assertEquals(0, res);
		assertFalse(tree.getMergeSpecialCharacters());
		assertEquals(rtf5.trim(), tree.getRtf().trim());
		assertEquals(text2.trim(), tree.getText().trim());
	}

	@Test
	public void loadSimpleDocMergeSpecialFromFile() {
		RtfTree tree = new RtfTree();

		tree.setMergeSpecialCharacters(true);

		int res = tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

		String strTree1 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-3.txt").getFile());
		String strTree2 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-4.txt").getFile());
		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf1.txt").getFile());
		String text1 = leerFichero(getClass().getClassLoader().getResource("testdocs/text1.txt").getFile());

		assertEquals(0, res);
		assertTrue(tree.getMergeSpecialCharacters());
		System.out.print(tree.getText().trim());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

	@Test
	public void loadSimpleDocFromString() {
		RtfTree tree = new RtfTree();

		String strDoc = leerFichero(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

		int res = tree.loadRtfText(strDoc);

		String strTree1 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-1.txt").getFile());
		String strTree2 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-2.txt").getFile());
		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf1.txt").getFile());
		String text1 = leerFichero(getClass().getClassLoader().getResource("testdocs/text1.txt").getFile());

		assertEquals(0, res);
		assertFalse(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

	@Test
	public void loadSimpleDocMergeSpecialFromString() {
		RtfTree tree = new RtfTree();
		tree.setMergeSpecialCharacters(true);

		String strDoc = leerFichero(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

		int res = tree.loadRtfText(strDoc);

		String strTree1 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-3.txt").getFile());
		String strTree2 = leerFichero(getClass().getClassLoader().getResource("testdocs/result1-4.txt").getFile());
		String rtf1 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf1.txt").getFile());
		String text1 = leerFichero(getClass().getClassLoader().getResource("testdocs/text1.txt").getFile());

		assertEquals(0, res);
		assertTrue(tree.getMergeSpecialCharacters());
		assertEquals(strTree1.trim(), tree.toString().trim());
		assertEquals(strTree2.trim(), tree.toStringEx().trim());
		assertEquals(rtf1.trim(), tree.getRtf().trim());
		assertEquals(text1.trim(), tree.getText().trim());
	}

	private String leerFichero(String path) {
		String contenido="";
		try {
			Path f=new File(path).toPath();
			contenido=new String(Files.readAllBytes(f), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.println("Error lectura.");
		}

		return contenido;
	}
}
