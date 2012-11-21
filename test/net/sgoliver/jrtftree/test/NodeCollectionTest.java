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
 * Class:		NodeCollectionTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sgoliver.jrtftree.core.RtfNodeCollection;
import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTreeNode;


public class NodeCollectionTest //In Sync
{
    @Test
     public void populateCollection()
     {
         RtfNodeCollection list1 = new RtfNodeCollection();
         RtfNodeCollection list2 = new RtfNodeCollection();

         RtfTreeNode node = new RtfTreeNode(RtfNodeType.KEYWORD, "b", true, 2);

         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "a", true, 1));
         list1.add(node);
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "c", true, 3));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "d", true, 4));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "e", true, 5));

         list2.add(node);
         list2.add(new RtfTreeNode(RtfNodeType.KEYWORD, "g", true, 7));

         assertEquals(5, list1.size());
         assertEquals(2, list2.size());

         assertSame(list1.get(1), node);
         assertSame(list2.get(0), node);

         list1.addRange(list2);

         assertEquals(7, list1.size());

         assertSame(list1.get(5), list2.get(0));
         assertSame(list1.get(6), list2.get(1));

         assertEquals("g", list1.get(6).getNodeKey());
         assertSame(list2.get(0), node);

         RtfTreeNode node1 = new RtfTreeNode(RtfNodeType.KEYWORD, "h", false, 8);

         list1.insert(5, node1);

         assertEquals(8, list1.size());
         assertSame(list1.get(5), node1);

         RtfTreeNode node2 = new RtfTreeNode(RtfNodeType.KEYWORD, "i", false, 9);

         list1.set(1, node2);

         assertEquals(8, list1.size());
         assertSame(list1.get(1), node2);
     }

     @Test
     public void removeNodesFromCollection()
     {
         RtfNodeCollection list1 = new RtfNodeCollection();

         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "a", true, 1));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "b", true, 2));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "c", true, 3));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "d", true, 4));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "e", true, 5));

         assertEquals(5, list1.size());
         assertEquals("b", list1.get(1).getNodeKey());

         list1.removeAt(1);

         assertEquals(4, list1.size());
         assertEquals("a", list1.get(0).getNodeKey());
         assertEquals("c", list1.get(1).getNodeKey());
         assertEquals("d", list1.get(2).getNodeKey());
         assertEquals("e", list1.get(3).getNodeKey());

         list1.removeRange(1, 2);

         assertEquals(2, list1.size());
         assertEquals("a", list1.get(0).getNodeKey());
         assertEquals("e", list1.get(1).getNodeKey());
     }

     @Test
     public void searchNodes()
     {
         RtfNodeCollection list1 = new RtfNodeCollection();
         RtfTreeNode node = new RtfTreeNode(RtfNodeType.KEYWORD, "c", true, 3);

         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "a", true, 1));
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "b", true, 2));
         list1.add(node);
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "b", true, 4));
         list1.add(node);
         list1.add(new RtfTreeNode(RtfNodeType.KEYWORD, "e", true, 6));

         assertEquals(2, list1.indexOf(node));
         assertEquals(-1, list1.indexOf(new RtfTreeNode()));

         assertEquals(2, list1.indexOf(node, 0));
         assertEquals(2, list1.indexOf(node, 2));
         assertEquals(4, list1.indexOf(node, 3));
         assertEquals(-1, list1.indexOf(node, 5));

         assertEquals(1, list1.indexOf("b", 0));
         assertEquals(1, list1.indexOf("b", 1));
         assertEquals(3, list1.indexOf("b", 2));
         assertEquals(-1, list1.indexOf("x", 0));

         assertEquals(1, list1.indexOf("b"));
         assertEquals(-1, list1.indexOf("x"));
     }
}
