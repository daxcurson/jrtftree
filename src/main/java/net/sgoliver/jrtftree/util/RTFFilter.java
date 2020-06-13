package net.sgoliver.jrtftree.util;

import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;

public interface RTFFilter 
{
	void filter(RtfTree v);
	void filter(RtfTreeNode v);
}
