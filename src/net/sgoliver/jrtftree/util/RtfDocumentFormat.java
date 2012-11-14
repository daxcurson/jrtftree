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
 * Class:		RtfDocumentFormat
 * Description:	Representa un formato de documento.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

public class RtfDocumentFormat 
{
    private float marginl = 2;
    private float marginr = 2;
    private float margint = 2;
    private float marginb = 2;
    
	public void setMarginl(float marginl) {
		this.marginl = marginl;
	}
	public float getMarginl() {
		return marginl;
	}
	public void setMarginr(float marginr) {
		this.marginr = marginr;
	}
	public float getMarginr() {
		return marginr;
	}
	public void setMargint(float margint) {
		this.margint = margint;
	}
	public float getMargint() {
		return margint;
	}
	public void setMarginb(float marginb) {
		this.marginb = marginb;
	}
	public float getMarginb() {
		return marginb;
	}
}
