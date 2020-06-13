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
 * Class:		RtfStyleSheetType
 * Description:	Tipos de hojas de estilo de documento RTF.
 * ******************************************************************************/


/**
 * Tipos de hojas de estilo de un documento RTF.
 */
public class RtfStyleSheetType //In Sync
{
	/**
	 * Hoja de estilo sin definir.
	 */
	public static final int NONE = 0;
	
	/**
	 * Hoja de estilo de caracter.
	 */
	public static final int CHARACTER = 1;
	
	/**
	 * Hoja de estilo de párrafo.
	 */
	public static final int PARAGRAPH = 2;
	
	/**
	 * Hoja de estilo de sección.
	 */
	public static final int SECTION = 3;
	
	/**
	 * Hoja de estilo de tabla.
	 */
	public static final int TABLE = 4;
	
	/**
	 * Convierte a cadena el tipo de hoja de estilos.
	 * @param ssType Tipo de hoja de estilos.
	 * @return Representación textual del tipo de hoja de estilos.
	 */
	public static String toString(int ssType)
	{
		String res = "";
		
		switch(ssType)
		{
			case NONE:
				res = "NONE";
				break;	
			case CHARACTER:
				res =  "CHARACTER";
				break;
			case PARAGRAPH:
				res = "PARAGRAPH";
				break;
			case SECTION:
				res = "SECTION";
				break;
			case TABLE:
				res = "TABLE";
				break;
		}
		
		return res;
	}
	
	
}
