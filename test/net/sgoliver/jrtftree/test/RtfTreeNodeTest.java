package net.sgoliver.jrtftree.test;

import static org.junit.Assert.*;
import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;

import org.junit.Test;


public class RtfTreeNodeTest 
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
}
