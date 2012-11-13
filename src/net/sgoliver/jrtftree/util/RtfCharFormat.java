package net.sgoliver.jrtftree.util;

import java.awt.Color;

public class RtfCharFormat 
{
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private String font = "Arial";
	private int size = 10;
	private Color color = Color.BLACK;
	
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isBold() {
		return bold;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
	public boolean isUnderline() {
		return underline;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getFont() {
		return font;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
}
