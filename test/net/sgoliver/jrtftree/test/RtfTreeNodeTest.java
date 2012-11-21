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
 * Class:		RtfTreeNodeTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;
import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;

import org.junit.Test;


public class RtfTreeNodeTest //In Sync
{
	@Test
	public void addChildToEmptyNode() {
		RtfTreeNode node = new RtfTreeNode();

		assertNull(node.getChildNodes());

		RtfTreeNode childNode = new RtfTreeNode();
		node.insertChild(0, childNode);

		assertNotNull(node.getChildNodes());
		assertSame(node.getChildNodes().get(0), childNode);
		assertNull(childNode.getChildNodes());

		RtfTreeNode anotherChildNode = new RtfTreeNode();
		childNode.appendChild(anotherChildNode);

		assertNotNull(childNode.getChildNodes());
		assertSame(childNode.getChildNodes().get(0), anotherChildNode);
	}

	@Test
	public void stringRepresentation() {
		RtfTreeNode node = new RtfTreeNode(RtfNodeType.KEYWORD, "b", true, 3);
		RtfTreeNode node2 = new RtfTreeNode(RtfNodeType.ROOT);

		assertEquals("[KEYWORD, b, true, 3]", node.toString());
		assertEquals("[ROOT, , false, 0]", node2.toString());
	}

	@Test
	public void textExtraction() {
		RtfTree tree = new RtfTree();

		tree.loadRtfFile("test\\testdocs\\testdoc4.rtf");

		RtfTreeNode simpleGroup = tree.getMainGroup().selectSingleGroup("ul");
		RtfTreeNode nestedGroups = tree.getMainGroup().selectSingleGroup("cf");
		RtfTreeNode keyword = tree.getMainGroup().selectSingleChildNode("b");
		RtfTreeNode control = tree.getMainGroup().selectSingleChildNode("'");
		RtfTreeNode root = tree.getRootNode();

		assertEquals("underline1", simpleGroup.getText());
		assertEquals(
				"blue1 luctus. Fusce in interdum ipsum. Cum sociis natoque penatibus et italic1 dis parturient montes, nascetur ridiculus mus.", 
				nestedGroups.getText());
		assertEquals("", keyword.getText());
		assertEquals("é", control.getText());
		assertEquals("", root.getText());

		assertEquals("underline1", simpleGroup.getRawText());
		assertEquals(
				"blue1 luctus. Fusce in interdum ipsum. Cum sociis natoque penatibus et italic1 dis parturient montes, nascetur ridiculus mus.",
				nestedGroups.getRawText());
		assertEquals("", keyword.getRawText());
		assertEquals("é", control.getRawText());
		assertEquals("", root.getRawText());

		RtfTreeNode fontsGroup = tree.getMainGroup().selectSingleGroup("fonttbl");
		RtfTreeNode generatorGroup = tree.getMainGroup().selectSingleGroup("*");

		assertEquals("", fontsGroup.getText());
		assertEquals("", generatorGroup.getText());

		assertEquals("Times New Roman;Arial;Arial;", fontsGroup.getRawText());
		assertEquals("Msftedit 5.41.15.1515;", generatorGroup.getRawText());
	}
	
	@Test
	public void textExtractionSpecial() {
		RtfTree tree = new RtfTree();

        tree.loadRtfFile("test\\testdocs\\testdoc5.rtf");

        assertEquals("Esto es una ‘prueba’\r\n\t y otra “prueba” y otra—prueba." + System.getProperty("line.separator"), tree.getText());
        assertEquals("Esto es una ‘prueba’\r\n\t y otra “prueba” y otra—prueba." + System.getProperty("line.separator"), tree.getMainGroup().getText());
        assertEquals("Arial;Msftedit 5.41.15.1515;Esto es una ‘prueba’\r\n\t y otra “prueba” y otra—prueba." + System.getProperty("line.separator"), tree.getMainGroup().getRawText());
	}
	
	@Test
    public void textExtractionUnicode()
    {
        RtfTree tree = new RtfTree();

        tree.loadRtfFile("test\\testdocs\\unicodedoc.rtf");

        assertEquals("Prueba Unicode: Вова Петя\r\nSin ignorar caracteres: Вова Петя\r\n", tree.getText());
    }
}
