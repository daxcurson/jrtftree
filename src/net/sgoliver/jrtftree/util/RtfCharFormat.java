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
 * Class:		RtfCharFormat
 * Description:	Representa un formato de texto.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.awt.Color;

public class RtfCharFormat //In Sync
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
