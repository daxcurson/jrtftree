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
 * Class:		SelectNodesTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import net.sgoliver.jrtftree.core.RtfNodeCollection;
import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;


public class SelectNodesTest //In Sync
{
	@Test
     public void selectChildNodesByType()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectChildNodes(RtfNodeType.KEYWORD);  //48 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectChildNodes(RtfNodeType.CONTROL);  //3 nodes
         RtfNodeCollection lista3 = tree.getMainGroup().selectChildNodes(RtfNodeType.GROUP);    //3 nodes

         assertEquals(49, lista1.size());
         assertEquals(3, lista2.size());
         assertEquals(3, lista3.size());

         assertSame(lista1.get(5), tree.getMainGroup().getChildNodes().get(8));  //viewkind
         assertEquals("lang", lista1.get(22).getNodeKey());   //lang3082  

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(45)); //'233
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(47)); //'241
         assertEquals(241, lista2.get(1).getParameter());     //'241

         assertSame(lista3.get(0), tree.getMainGroup().getChildNodes().get(5));
         assertEquals("fonttbl", lista3.get(0).firstChild().getNodeKey());
         assertSame(lista3.get(1), tree.getMainGroup().getChildNodes().get(6));
         assertEquals("colortbl", lista3.get(1).firstChild().getNodeKey());
         assertSame(lista3.get(2), tree.getMainGroup().getChildNodes().get(7));
         assertEquals("*", lista3.get(2).getChildNodes().get(0).getNodeKey());
         assertEquals("generator", lista3.get(2).getChildNodes().get(1).getNodeKey());
     }

     @Test
     public void selectNodesByType()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectNodes(RtfNodeType.KEYWORD);  //69 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectNodes(RtfNodeType.CONTROL);  //4 nodes
         RtfNodeCollection lista3 = tree.getMainGroup().selectNodes(RtfNodeType.GROUP);    //6 nodes

         assertEquals(69, lista1.size(), 69);
         assertEquals(4, lista2.size());
         assertEquals(6, lista3.size());

         assertSame(lista1.get(5), tree.getMainGroup().getChildNodes().get(5).firstChild());     //fonttbl
         assertSame(lista1.get(22), tree.getMainGroup().getChildNodes().get(6).getChildNodes().get(7)); //green0
         assertEquals("green", lista1.get(22).getNodeKey());                //green0  

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(7).firstChild()); //* generator
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(45)); //'233
         assertSame(lista2.get(2), tree.getMainGroup().getChildNodes().get(47)); //'241
         assertEquals(lista2.get(2).getParameter(), 241);     //'241

         assertSame(lista3.get(0), tree.getMainGroup().getChildNodes().get(5));
         assertEquals(lista3.get(0).firstChild().getNodeKey(), "fonttbl");
         assertSame(lista3.get(3), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(3));
         assertEquals(lista3.get(3).firstChild().getNodeKey(), "f");
         assertEquals(lista3.get(3).firstChild().getParameter(), 2);
         assertSame(lista3.get(5), tree.getMainGroup().getChildNodes().get(7));
         assertEquals(lista3.get(5).getChildNodes().get(0).getNodeKey(), "*");
         assertEquals(lista3.get(5).getChildNodes().get(1).getNodeKey(), "generator");
     }

     @Test
     public void selectSingleNodeByType()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleNode(RtfNodeType.KEYWORD); //rtf1
         RtfTreeNode node2 = tree.getMainGroup().selectSingleNode(RtfNodeType.CONTROL); //* generator
         RtfTreeNode node3 = tree.getMainGroup().selectSingleNode(RtfNodeType.GROUP);   //fonttbl

         assertSame(node1, tree.getMainGroup().getChildNodes().get(0));
         assertEquals(node1.getNodeKey(), "rtf");
         assertSame(node2, tree.getMainGroup().getChildNodes().get(7).getChildNodes().get(0));
         assertEquals(node2.getNodeKey(), "*");
         assertEquals(node2.nextSibling().getNodeKey(), "generator");
         assertSame(node3, tree.getMainGroup().getChildNodes().get(5));
         assertEquals(node3.firstChild().getNodeKey(), "fonttbl");
     }

     @Test
     public void selectSingleChildNodeByType()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleChildNode(RtfNodeType.KEYWORD); //rtf1
         RtfTreeNode node2 = tree.getMainGroup().selectSingleChildNode(RtfNodeType.CONTROL); //'233
         RtfTreeNode node3 = tree.getMainGroup().selectSingleChildNode(RtfNodeType.GROUP);   //fonttbl

         assertSame(node1, tree.getMainGroup().getChildNodes().get(0));
         assertEquals(node1.getNodeKey(), "rtf");
         assertSame(node2, tree.getMainGroup().getChildNodes().get(45));
         assertEquals(node2.getNodeKey(), "'");
         assertEquals(node2.getParameter(), 233);
         assertSame(node3, tree.getMainGroup().getChildNodes().get(5));
         assertEquals(node3.firstChild().getNodeKey(), "fonttbl");
     }

     @Test
     public void selectChildNodesByKeyword()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectChildNodes("fs");  //5 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectChildNodes("f");   //3 nodes

         assertEquals(lista1.size(), 5);
         assertEquals(lista2.size(), 3);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(17));
         assertSame(lista1.get(1), tree.getMainGroup().getChildNodes().get(22));
         assertSame(lista1.get(2), tree.getMainGroup().getChildNodes().get(25));
         assertSame(lista1.get(3), tree.getMainGroup().getChildNodes().get(43));
         assertSame(lista1.get(4), tree.getMainGroup().getChildNodes().get(77));

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(16));
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(56));
         assertSame(lista2.get(2), tree.getMainGroup().getChildNodes().get(76));
     }

     @Test
     public void selectNodesByKeyword()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectNodes("fs");  //5 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectNodes("f");   //6 nodes

         assertEquals(lista1.size(), 5);
         assertEquals(lista2.size(), 6);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(17));
         assertSame(lista1.get(1), tree.getMainGroup().getChildNodes().get(22));
         assertSame(lista1.get(2), tree.getMainGroup().getChildNodes().get(25));
         assertSame(lista1.get(3), tree.getMainGroup().getChildNodes().get(43));
         assertSame(lista1.get(4), tree.getMainGroup().getChildNodes().get(77));

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(1).firstChild());
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(2).firstChild());
         assertSame(lista2.get(2), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(3).firstChild());
         assertSame(lista2.get(3), tree.getMainGroup().getChildNodes().get(16));
         assertSame(lista2.get(4), tree.getMainGroup().getChildNodes().get(56));
         assertSame(lista2.get(5), tree.getMainGroup().getChildNodes().get(76));
     }

     @Test
     public void selectSingleNodeByKeyword()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleNode("fs"); 
         RtfTreeNode node2 = tree.getMainGroup().selectSingleNode("f");  

         assertSame(node1, tree.getMainGroup().getChildNodes().get(17));
         assertSame(node2, tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(1).firstChild());
     }

     @Test
     public void selectSingleChildNodeByKeyword()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleChildNode("fs");
         RtfTreeNode node2 = tree.getMainGroup().selectSingleChildNode("f");

         assertSame(node1, tree.getMainGroup().getChildNodes().get(17));
         assertSame(node2, tree.getMainGroup().getChildNodes().get(16));
     }

     @Test
     public void selectChildNodesByKeywordAndParam()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectChildNodes("fs", 24);  //2 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectChildNodes("f", 1);    //1 nodes

         assertEquals(lista1.size(), 2);
         assertEquals(lista2.size(), 1);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(22));
         assertSame(lista1.get(1), tree.getMainGroup().getChildNodes().get(43));

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(56));
     }

     @Test
     public void selectNodesByKeywordAndParam()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectNodes("fs", 24);  //2 nodes
         RtfNodeCollection lista2 = tree.getMainGroup().selectNodes("f", 1);    //2 nodes

         assertEquals(lista1.size(), 2);
         assertEquals(lista2.size(), 2);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(22));
         assertSame(lista1.get(1), tree.getMainGroup().getChildNodes().get(43));

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(2).firstChild());
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(56));
     }

     @Test
     public void selectSingleNodeByKeywordAndParam()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleNode("fs", 24);
         RtfTreeNode node2 = tree.getMainGroup().selectSingleNode("f", 1);

         assertSame(node1, tree.getMainGroup().getChildNodes().get(22));
         assertSame(node2, tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(2).firstChild());
     }

     @Test
     public void selectSingleChildNodeByKeywordAndParam()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleChildNode("fs", 24);
         RtfTreeNode node2 = tree.getMainGroup().selectSingleChildNode("f", 1);

         assertSame(node1, tree.getMainGroup().getChildNodes().get(22));
         assertSame(node2, tree.getMainGroup().getChildNodes().get(56));
     }

     @Test
     public void selectChildGroups()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectChildGroups("colortbl");  //1 node
         RtfNodeCollection lista2 = tree.getMainGroup().selectChildGroups("f");         //0 nodes

         assertEquals(lista1.size(), 1);
         assertEquals(lista2.size(), 0);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(6));
     }

     @Test
     public void selectGroups()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection lista1 = tree.getMainGroup().selectGroups("colortbl");  //1 node
         RtfNodeCollection lista2 = tree.getMainGroup().selectGroups("f");         //3 nodes

         assertEquals(lista1.size(), 1);
         assertEquals(lista2.size(), 3);

         assertSame(lista1.get(0), tree.getMainGroup().getChildNodes().get(6));

         assertSame(lista2.get(0), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(1));
         assertSame(lista2.get(1), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(2));
         assertSame(lista2.get(2), tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(3));
     }

     @Test
     public void selectSingleGroup()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().selectSingleGroup("f");
         RtfTreeNode node2 = tree.getMainGroup().getChildNodes().get(5).selectSingleChildGroup("f");

         assertSame(node1, tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(1));
         assertSame(node2, tree.getMainGroup().getChildNodes().get(5).getChildNodes().get(1));
     }

     @Test
     public void selectSpecialGroups()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection list1 = tree.getMainGroup().selectChildGroups("generator");
         RtfNodeCollection list2 = tree.getMainGroup().selectChildGroups("generator", false);
         RtfNodeCollection list3 = tree.getMainGroup().selectChildGroups("generator", true);

         RtfNodeCollection list4 = tree.getMainGroup().selectGroups("generator");
         RtfNodeCollection list5 = tree.getMainGroup().selectGroups("generator", false);
         RtfNodeCollection list6 = tree.getMainGroup().selectGroups("generator", true);

         RtfTreeNode node1 = tree.getMainGroup().selectSingleChildGroup("generator");
         RtfTreeNode node2 = tree.getMainGroup().selectSingleChildGroup("generator", false);
         RtfTreeNode node3 = tree.getMainGroup().selectSingleChildGroup("generator", true);

         RtfTreeNode node4 = tree.getMainGroup().selectSingleGroup("generator");
         RtfTreeNode node5 = tree.getMainGroup().selectSingleGroup("generator", false);
         RtfTreeNode node6 = tree.getMainGroup().selectSingleGroup("generator", true);

         assertEquals(list1.size(), 0);
         assertEquals(list2.size(), 0);
         assertEquals(list3.size(), 1);

         assertEquals(list4.size(), 0);
         assertEquals(list5.size(), 0);
         assertEquals(list6.size(), 1);

         assertNull(node1);
         assertNull(node2);
         assertNotNull(node3);

         assertNull(node4);
         assertNull(node5);
         assertNotNull(node6);
     }

     @Test
     public void selectSiblings()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfTreeNode node1 = tree.getMainGroup().getChildNodes().get(4);               //deflang3082
         RtfTreeNode node2 = tree.getMainGroup().getChildNodes().get(6).getChildNodes().get(2); //colortbl/red

         RtfTreeNode n1 = node1.selectSibling(RtfNodeType.GROUP);
         RtfTreeNode n2 = node1.selectSibling("viewkind");
         RtfTreeNode n3 = node1.selectSibling("fs", 28);

         RtfTreeNode n4 = node2.selectSibling(RtfNodeType.KEYWORD);
         RtfTreeNode n5 = node2.selectSibling("blue");
         RtfTreeNode n6 = node2.selectSibling("red", 255);

         assertSame(n1, tree.getMainGroup().getChildNodes().get(5));
         assertSame(n2, tree.getMainGroup().getChildNodes().get(8));
         assertSame(n3, tree.getMainGroup().getChildNodes().get(17));

         assertSame(n4, tree.getMainGroup().getChildNodes().get(6).getChildNodes().get(3));
         assertSame(n5, tree.getMainGroup().getChildNodes().get(6).getChildNodes().get(4));
         assertSame(n6, tree.getMainGroup().getChildNodes().get(6).getChildNodes().get(6));
     }

     @Test
     public void findText()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         RtfNodeCollection list1 = tree.getMainGroup().findText("Italic");

         assertEquals(list1.size(), 2);

         assertSame(list1.get(0), tree.getMainGroup().getChildNodes().get(18));
         assertEquals(list1.get(0).getNodeKey(), "Bold Italic Underline Size 14");

         assertSame(list1.get(1), tree.getMainGroup().getChildNodes().get(73));
         assertEquals(list1.get(1).getNodeKey(), "Italic2");
     }

     @Test
     public void replaceText()
     {
         RtfTree tree = new RtfTree();

         tree.loadRtfFile(getClass().getClassLoader().getResource("testdocs/testdoc1.rtf").getFile());

         tree.getMainGroup().replaceText("Italic", "REPLACED");

         String rtf2 = leerFichero(getClass().getClassLoader().getResource("testdocs/rtf2.txt").getFile());

         assertEquals(tree.getRtf().trim(), rtf2.trim());
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
