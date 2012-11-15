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
 * Class:		RtfFontTable
 * Description:	Tabla de Fuentes de un documento RTF.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.util.HashMap;

public class RtfFontTable //In Sync
{
	private HashMap<Integer, String> fonts = null;
	
	/**
	 * Constructor de la clase RtfFontTable.
	 */
	public RtfFontTable()
    {
        fonts = new HashMap<Integer,String>();
    }
	
	/**
	 * Inserta un nueva fuente en la tabla de fuentes.
	 * @param name Nueva fuente a insertar.
	 */
	public void AddFont(String name)
    {
        fonts.put(newFontIndex(),name);
    }
	
	/**
	 * Inserta un nueva fuente en la tabla de fuentes.
	 * @param index Indice de la fuente a insertar.
	 * @param name Nueva fuente a insertar.
	 */
	public void AddFont(int index, String name)
    {
        fonts.put(index, name);
    }
	
	/**
	 * Obtiene la fuente n-ésima de la tabla de fuentes.
	 * @param index Indice de la fuente a recuperar.
	 * @return Fuente n-ésima de la tabla de fuentes.
	 */
	public String get(int index)
	{
		return fonts.get(index);
	}
	
	/**
	 * Número de fuentes en la tabla.
	 * @return
	 */
	public int size()
	{
		return fonts.size();
	}
	
	/**
	 * Obtiene el índice de una fuente determinado en la tabla.
	 * @param name Fuente a consultar.
	 * @return Indice de la fuente consultada.
	 */
	public int indexOf(String name)
    {
        int intIndex = -1;
        Object[] keys = fonts.keySet().toArray();

        for(int i=0; i<keys.length; i++)
        {
            if (fonts.get(keys[i]).equals(name))
            {
                intIndex = (Integer)keys[i];
                break;
            }
        }

        return intIndex;
    }
	
	/**
	 * Obtiene un índice que no se esté usando en la tabla.
	 * @return Obtiene un índice que no se esté usando en la tabla.
	 */
	private int newFontIndex()
    {
        int intIndex = -1;
        Object[] keys = fonts.keySet().toArray();

        for(int i=0; i<keys.length; i++)
        {
            if ((Integer)keys[i] > intIndex)
                intIndex = (Integer)keys[i];
        }

        return (intIndex + 1);
    }
}
