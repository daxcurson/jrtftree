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
 * Class:		RtfDocumentTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import net.sgoliver.jrtftree.util.RtfCharFormat;
import net.sgoliver.jrtftree.util.RtfDocument;
import net.sgoliver.jrtftree.util.RtfParFormat;
import net.sgoliver.jrtftree.util.TextAlignment;

import org.junit.Test;


public class RtfDocumentTest 
{
	@Test
	public void CreateSimpleDocument() 
	{
		RtfDocument doc = new RtfDocument("test\\testdocs\\rtfdocument.rtf");

		RtfCharFormat charFormat = new RtfCharFormat();
		charFormat.setColor(new Color(0,0,139));
		charFormat.setUnderline(true);
		charFormat.setBold(true);
		doc.updateCharFormat(charFormat);

		RtfParFormat parFormat = new RtfParFormat();
		parFormat.setAlignment(TextAlignment.JUSTIFIED);
		doc.updateParFormat(parFormat);

		doc.addText("First Paragraph");
		doc.addNewParagraph(2);

		doc.setFormatBold(false);
		doc.setFormatUnderline(false);
		doc.setFormatColor(Color.RED);

		doc.addText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer quis eros at tortor pharetra laoreet. Donec tortor diam, imperdiet ut porta quis, congue eu justo.");
		doc.addText("Quisque viverra tellus id mauris tincidunt luctus. Fusce in interdum ipsum. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.");
		doc.addText("Donec ac leo justo, vitae rutrum elit. Nulla tellus elit, imperdiet luctus porta vel, consectetur quis turpis. Nam purus odio, dictum vitae sollicitudin nec, tempor eget mi.");
		doc.addText("Etiam vitae porttitor enim. Aenean molestie facilisis magna, quis tincidunt leo placerat in. Maecenas malesuada eleifend nunc vitae cursus.");
		doc.addNewParagraph(2);

		doc.addText("Second Paragraph", charFormat);
		doc.addNewParagraph(2);

		charFormat.setFont("Courier New");
		charFormat.setColor(new Color(0,128,0));
		charFormat.setBold(false);
		charFormat.setUnderline(false);
		doc.updateCharFormat(charFormat);

		doc.setAlignment(TextAlignment.LEFT);
		doc.setLeftIndentation(2);
		doc.setRightIndentation(2);

		doc.addText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer quis eros at tortor pharetra laoreet. Donec tortor diam, imperdiet ut porta quis, congue eu justo.");
		doc.addText("Quisque viverra tellus id mauris tincidunt luctus. Fusce in interdum ipsum. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.");
		doc.addText("Donec ac leo justo, vitae rutrum elit. Nulla tellus elit, imperdiet luctus porta vel, consectetur quis turpis. Nam purus odio, dictum vitae sollicitudin nec, tempor eget mi.");
		doc.addText("Etiam vitae porttitor enim. Aenean molestie facilisis magna, quis tincidunt leo placerat in. Maecenas malesuada eleifend nunc vitae cursus.");
		doc.addNewParagraph(2);

		doc.updateCharFormat(charFormat);
		doc.setFormatUnderline(false);
		doc.setFormatItalic(true);
		doc.setFormatColor(new Color(0,0,139));

		doc.setLeftIndentation(0);

		doc.addText("Test Doc.");
		doc.addNewLine(2);
		doc.addText("Stop.");

		doc.close();

		String rtf1 = leerFichero("test\\testdocs\\rtfdocument.rtf");
		String rtf4 = leerFichero("test\\testdocs\\rtf4.txt");

		assertEquals(rtf4.trim(), rtf1.trim());
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
