package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import net.sgoliver.jrtftree.core.RtfTree;

import org.junit.Test;

public class LoadRtfTest 
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
