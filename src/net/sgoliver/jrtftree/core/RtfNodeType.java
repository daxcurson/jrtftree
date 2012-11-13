/* Actualizado */

/********************************************************************************
 *   This file is part of JRtfTree.
 *
 *   JRtfTree is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   JRtfTree is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JRtfTree; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ********************************************************************************/	

/********************************************************************************
 * Library:		JRtfTree
 * Version:     v0.1.1b
 * Class:		RtfNodeType
 * Copyright:   2005 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * SF Project:	http://nrtftree.sourceforge.net
 *				http://sourceforge.net/projects/nrtftree
 * Date:		15/01/2006
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

/**
 * Tipos de nodo de un documento RTF.
 */
public class RtfNodeType
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
