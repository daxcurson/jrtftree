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
 * Class:		NavigationTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;

public class NavigationTest //In Sync
{
	@Test
	public void emptyNodeNavigation() 
	{
		RtfTreeNode node = new RtfTreeNode();

		assertNull(node.getTree());
		assertNull(node.getRootNode());
		assertNull(node.getParentNode());

		assertNull(node.nextSibling());
		assertNull(node.previousSibling());

		assertNull(node.getChildNodes());

		assertNull(node.firstChild());
		assertNull(node.lastChild());
	}

	@Test
	public void typeNodeNavigation() 
	{
		RtfTreeNode node = new RtfTreeNode(RtfNodeType.KEYWORD);

		assertNull(node.getTree());
		assertNull(node.getRootNode());
		assertNull(node.getParentNode());

		assertNull(node.nextSibling());
		assertNull(node.previousSibling());

		assertNull(node.getChildNodes());

		assertNull(node.firstChild());
		assertNull(node.lastChild());
	}
	
	@Test
	public void initNodeNavigation() 
	{
		RtfTreeNode node = new RtfTreeNode(RtfNodeType.KEYWORD, "rtf", true, 99);

		assertNull(node.getTree());
		assertNull(node.getRootNode());
		assertNull(node.getParentNode());

		assertNull(node.nextSibling());
		assertNull(node.previousSibling());

		assertNull(node.getChildNodes());

		assertNull(node.firstChild());
		assertNull(node.lastChild());
	}
	
	@Test
	public void emptyTreeNavigation() 
	{
		RtfTree tree = new RtfTree();

		assertNotNull(tree.getRootNode());
		assertSame(tree.getRootNode().getTree(), tree);
		assertNull(tree.getMainGroup());
	}
	
	@Test
	public void simpleTreeNavigation() 
	{
		// Creación de un árbol sencillo
		RtfTree tree = new RtfTree();

		RtfTreeNode mainGroup = new RtfTreeNode(RtfNodeType.GROUP);
		RtfTreeNode rtfNode = new RtfTreeNode(RtfNodeType.KEYWORD, "rtf", true, 0);
		mainGroup.appendChild(rtfNode);

		RtfTreeNode newGroup = new RtfTreeNode(RtfNodeType.GROUP);
		RtfTreeNode node1 = new RtfTreeNode(RtfNodeType.KEYWORD, "ul", false, 0);
		RtfTreeNode node2 = new RtfTreeNode(RtfNodeType.TEXT, "Test", false, 0);
		RtfTreeNode node3 = new RtfTreeNode(RtfNodeType.TEXT, "ulnone", false, 0);

		newGroup.appendChild(node1);
		newGroup.appendChild(node2);
		newGroup.appendChild(node3);

		mainGroup.appendChild(newGroup);

		tree.getRootNode().appendChild(mainGroup);

		// Navegación básica: tree
		assertNotNull(tree.getRootNode());
		assertSame(tree.getMainGroup(), mainGroup);

		// Navegación básica: newGroup
		assertSame(newGroup.getTree(), tree);
		assertSame(newGroup.getParentNode(), mainGroup);
		assertSame(newGroup.getRootNode(), tree.getRootNode());
		assertNotNull(newGroup.getChildNodes());
		assertSame(newGroup.getChildNodes().get(1), node2);
		assertSame(newGroup.firstChild(), node1);
		assertSame(newGroup.lastChild(), node3);
		assertSame(newGroup.previousSibling(), rtfNode);
		assertNull(newGroup.nextSibling());
		assertEquals(1, newGroup.getIndex());

		// Navegación básica: nodo2
		assertSame(node2.getTree(), tree);
		assertSame(node2.getParentNode(), newGroup);
		assertSame(node2.getRootNode(), tree.getRootNode());
		assertNull(node2.getChildNodes());
		assertNull(node2.firstChild());
		assertNull(node2.lastChild());
		assertSame(node2.previousSibling(), node1);
		assertSame(node2.nextSibling(), node3);
		assertEquals(1, node2.getIndex());
	}
	
	@Test
	public void adjacentNodes() 
	{
		// Creación de un árbol sencillo
		RtfTree tree = new RtfTree();

		RtfTreeNode mainGroup = new RtfTreeNode(RtfNodeType.GROUP);
		RtfTreeNode rtfNode = new RtfTreeNode(RtfNodeType.KEYWORD, "rtf", true, 0);
		mainGroup.appendChild(rtfNode);

		RtfTreeNode newGroup = new RtfTreeNode(RtfNodeType.GROUP);
		RtfTreeNode node1 = new RtfTreeNode(RtfNodeType.KEYWORD, "ul", false, 0);
		RtfTreeNode node2 = new RtfTreeNode(RtfNodeType.TEXT, "Test", false, 0);
		RtfTreeNode node3 = new RtfTreeNode(RtfNodeType.KEYWORD, "ulnone",  false, 0);

		newGroup.appendChild(node1);
		newGroup.appendChild(node2);
		newGroup.appendChild(node3);

		mainGroup.appendChild(newGroup);

		tree.getRootNode().appendChild(mainGroup);

		RtfTreeNode node4 = new RtfTreeNode(RtfNodeType.TEXT, "fin", false, 0);

		mainGroup.appendChild(node4);

		assertSame(tree.getRootNode().nextNode(), mainGroup);
		assertSame(mainGroup.nextNode(), rtfNode);
		assertSame(rtfNode.nextNode(), newGroup);
		assertSame(newGroup.nextNode(), node1);
		assertSame(node1.nextNode(), node2);
		assertSame(node2.nextNode(), node3);
		assertSame(node3.nextNode(), node4);
		assertNull(node4.nextNode());

		assertSame(node4.previousNode(), node3);
		assertSame(node3.previousNode(), node2);
		assertSame(node2.previousNode(), node1);
		assertSame(node1.previousNode(), newGroup);
		assertSame(newGroup.previousNode(), rtfNode);
		assertSame(rtfNode.previousNode(), mainGroup);
		assertSame(mainGroup.previousNode(), tree.getRootNode());
		assertNull(tree.getRootNode().previousNode());
	}
}
