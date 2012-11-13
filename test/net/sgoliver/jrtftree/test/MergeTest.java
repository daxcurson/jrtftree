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
		RtfMerger merger = new RtfMerger("test\\testdocs\\merge-template.rtf", true);
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
