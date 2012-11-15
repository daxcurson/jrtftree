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
 * Class:		RtfParFormat
 * Description:	Representa un formato de párrafo.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import net.sgoliver.jrtftree.util.TextAlignment;;

public class RtfParFormat //In Sync
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
