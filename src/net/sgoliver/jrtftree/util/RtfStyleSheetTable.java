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
 * Class:		RtfStyleSheetTable
 * Description:	Representa la tabla de hojas de estilo de un documento RTF.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.util.*;

public class RtfStyleSheetTable 
{
	private HashMap<Integer, RtfStyleSheet> stylesheets = null;
	
	/**
	 * Constructor de la tabla de estilos.
	 */
	public RtfStyleSheetTable()
    {
        stylesheets = new HashMap<Integer, RtfStyleSheet>();
    }
	
	/**
	 * A�ade un nuevo estilo a la tabla de estilos. El estilo se a�adir� con un nuevo �ndice no existente en la tabla.
	 * @param ss Nuevo estilo a a�adir a la tabla.
	 */
	public void addStyleSheet(RtfStyleSheet ss)
    {
        ss.setIndex(newStyleSheetIndex());

        stylesheets.put(ss.getIndex(), ss);
    }
	
	/**
	 * A�ade un nuevo estilo a la tabla de estilos. El estilo se a�adir� con el �ndice de estilo pasado como par�metro.
	 * @param index Indice del estilo a a�adir a la tabla.
	 * @param ss Nuevo estilo a a�adir a la tabla.
	 */
	public void addStyleSheet(int index, RtfStyleSheet ss)
    {
        ss.setIndex(index);

        stylesheets.put(index, ss);
    }
	
	/**
	 * Elimina un estilo de la tabla de estilos por �ndice.
	 * @param index Indice de la hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(int index)
    {
        stylesheets.remove(index);
    }
	
	/**
	 * Elimina de la tabla de estilos el estilo pasado como par�metro.
	 * @param ss Hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(RtfStyleSheet ss)
    {
        stylesheets.remove(ss.getIndex());
    }
	
	/**
	 * Recupera un estilo de la tabla de estilos por �ndice.
	 * @param index Indice del estilo a recuperar.
	 * @return Estilo cuyo �ndice es el pasado como par�metro.
	 */
	public RtfStyleSheet get(int index)
    {
        return stylesheets.get(index);
    }
	
	/**
	 * N�mero de estilos contenidos en la tabla de estilos.
	 * @return N�mero de estilos contenidos en la tabla de estilos.
	 */
	public int size()
	{
		return stylesheets.size();
	}
	
	/**
	 * �ndice del estilo cuyo nombre es el pasado como par�metro.
	 * @param name Nombre del estilo buscado.
	 * @return Estilo cuyo nombre es el pasado como par�metro.
	 */
	public int indexOf(String name)
    {
        int intIndex = -1;
        Object[] fntIndex = stylesheets.keySet().toArray();

        for(int i=0; i<fntIndex.length; i++)
        {
        	if (stylesheets.get(fntIndex[i]).equals(name))
            {
                intIndex = (Integer)fntIndex[i];
                break;
            }
        }

        return intIndex;
    }
	
	/**
	 * Calcula un nuevo �ndice para insertar un estilo en la tabla.
	 * @return �ndice del pr�ximo estilo a insertar.
	 */
	private int newStyleSheetIndex()
    {
        int intIndex = -1;
        Object[] fntIndex = stylesheets.keySet().toArray();

        for(int i=0; i<fntIndex.length; i++)
        {
        	if ((Integer)fntIndex[i] > intIndex)
                intIndex = (Integer)fntIndex[i];
        }

        return (intIndex + 1);
    }
}
