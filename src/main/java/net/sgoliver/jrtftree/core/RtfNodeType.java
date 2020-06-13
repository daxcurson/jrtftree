package net.sgoliver.jrtftree.core;
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
 * Class:		RtfNodeType
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

/**
 * Tipos de nodo de un documento RTF.
 */
public class RtfNodeType //In Sync
{
	/**
	 * Nodo raíz.
	 */
	public static final int ROOT = 0;
	
	/**
	 * Palabra clave.
	 */
	public static final int KEYWORD = 1;
	
	/**
	 * Símbolo de control.
	 */
	public static final int CONTROL = 2;
	
	/**
	 * Fragmento de texto.
	 */
	public static final int TEXT = 3;
	
	/**
	 * Grupo RTF.
	 */
	public static final int GROUP = 4;
	
	/**
	 * Nodo no inicializado.
	 */
	public static final int NONE = 5;
	
	/**
	 * Convierte el tipo de nodo a cadena.
	 * @param nodeType Tipo de nodo.
	 * @return Representación textual del tipo de nodo.
	 */
	public static String toString(int nodeType)
	{
		String res = "";
		
		switch(nodeType)
		{
			case ROOT:
				res = "ROOT";
				break;
			case KEYWORD:
				res =  "KEYWORD";
				break;
			case CONTROL:
				res = "CONTROL";
				break;
			case TEXT:
				res = "TEXT";
				break;
			case GROUP:
				res = "GROUP";
				break;
			case NONE:
				res = "NONE";
				break;
		}
		
		return res;
	}
}
