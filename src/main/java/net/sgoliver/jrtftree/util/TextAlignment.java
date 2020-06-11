package net.sgoliver.jrtftree.util;
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
 * Class:		TextAlignment
 * Description:	Tipos de alineación de texto.
 * ******************************************************************************/

/**
 * Tipos de hojas de estilo de un documento RTF.
 */
public class TextAlignment //In Sync
{
	/**
	 * Texto centrado.
	 */
	public static final int LEFT = 0;
	
	/**
	 * Texto justificado.
	 */
	public static final int RIGHT = 1;
	
	/**
	 * Texto alineado a la izquierda.
	 */
	public static final int CENTERED = 2;
	
	/**
	 * Texto justificado a la derecha.
	 */
	public static final int JUSTIFIED = 3;
	
	/**
	 * Convierte a cadena el tipo de alineación de texto.
	 * @param ta Tipo de alineación de texto.
	 * @return Representación textual del tipo de alineación de texto.
	 */
	public static String toString(int ta)
	{
		String res = "";
		
		switch(ta)
		{
			case CENTERED:
				res = "CENTERED";
				break;	
			case JUSTIFIED:
				res =  "JUSTIFIED";
				break;
			case LEFT:
				res = "LEFT";
				break;
			case RIGHT:
				res = "RIGHT";
				break;
		}
		
		return res;
	}
	
	
}
