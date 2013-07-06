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
 * Class:		RtfToken
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

public class RtfToken //In Sync
{
	/**
	 * Tipo de token.
	 */
	private int tokenType;
	
	/**
	 * Clave del token.
	 */
	private String key;
	
	/**
	 * Indicador de la existencia de parametro asociado al token.
	 */
	private boolean hasParam;
	
	/**
	 * Parámetro asociado al token, en caso de existir.
	 */
	private int param;
	
	/**
	 * Establece el parámetro del token.
	 */
	public void setParam(int param) {
		this.param = param;
	}

	/**
	 * Obtiene el parámetro del token.
	 */
	public int getParam() {
		return param;
	}
	
	/**
	 * Establece si el token tiene parámetro asociado.
	 */
	public void setHasParam(boolean hasParam) {
		this.hasParam = hasParam;
	}

	/**
	 * Obtiene si el token tiene parámetro asociado.
	 */
	public boolean getHasParam() {
		return hasParam;
	}

	/**
	 * Estable el tipo de token.
	 */
	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Obtiene el tipo de token.
	 */
	public int getTokenType() {
		return tokenType;
	}
	
	/**
	 * Estable la clave del token.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Obtiene el tipo de token.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Constructor de la clase.
	 */
	public RtfToken()
	{
        setTokenType(RtfTokenType.NONE);
        setKey("");
        setHasParam(false);
        setParam(0);
	}
	
	/**
	 * Convierte el token a una cadena de texto.
	 */
	public String toString()
	{
		return ("[" + getTokenType() + "] " + getKey() + " [hasParam=\"" + getHasParam() + "\" ; param=\"" + getParam() + "\"]");
	}
}
