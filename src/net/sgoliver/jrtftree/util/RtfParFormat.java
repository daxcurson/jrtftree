package net.sgoliver.jrtftree.util;

import net.sgoliver.jrtftree.util.TextAlignment;;

public class RtfParFormat 
{
	private int alignment = TextAlignment.LEFT;
    private float leftIndentation = 0;
    private float rightIndentation = 0;
    
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
	public int getAlignment() {
		return alignment;
	}
	public void setLeftIndentation(float leftIndentation) {
		this.leftIndentation = leftIndentation;
	}
	public float getLeftIndentation() {
		return leftIndentation;
	}
	public void setRightIndentation(float rightIndentation) {
		this.rightIndentation = rightIndentation;
	}
	public float getRightIndentation() {
		return rightIndentation;
	}
}
