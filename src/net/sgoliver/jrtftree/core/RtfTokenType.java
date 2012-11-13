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
 * Class:		RtfTokenType
 * Copyright:   2005 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * SF Project:	http://nrtftree.sourceforge.net
 *				http://sourceforge.net/projects/nrtftree
 * Date:		15/01/2006
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

/**
 * Tipos de token de un árbol de documento RTF.
 */
public class RtfTokenType
{
	/**
	 * Indica que el token sólo se ha inicializado.
	 */
	public static final int NONE = 0;
	
	/**
	 * Palabra clave sin parámetro.
	 */
	public static final int KEYWORD = 1;
	
	/**
	 * Símbolo de Control sin parámetro.
	 */
	public static final int CONTROL = 2;
	
	/**
	 * Texto del documento.
	 */
	public static final int TEXT = 3;
	
	/**
	 * Marca de fin de fichero.
	 */
	public static final int EOF = 4;
	
	/**
	 * Inicio de grupo: '{'
	 */
	public static final int GROUP_START = 5;
	
	/**
	 * Fin de grupo: '}'
	 */
	public static final int GROUP_END = 6;
	
	/**
	 * Convierte a cadena el tipo de token.
	 * @param tokenType Tipo de token.
	 * @return Representación textual del tipo de token.
	 */
	public static String toString(int tokenType)
	{
		String res = "";
		
		switch(tokenType)
		{
			case NONE:
				res = "NONE";
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
			case EOF:
				res = "EOF";
				break;
			case GROUP_START:
				res = "GROUP_START";
				break;
			case GROUP_END:
				res = "GROUP_END";
				break;
		}
		
		return res;
	}
}
