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
 * Class:		RtfColorTable
 * Description:	Tabla de Colores de un documento RTF.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.awt.Color;
import java.util.LinkedList;

public class RtfColorTable //In Sync
{
	private LinkedList<Color> colors;
	
	/**
	 * Constructor de la clase RtfColorTable.
	 */
	public RtfColorTable()
    {
        colors = new LinkedList<Color>();
    }
	
	/**
	 * Inserta un nuevo color en la tabla de colores.
	 * @param color Nuevo color a insertar.
	 */
	public void addColor(Color color)
    {
        colors.add(color);
    }
	
	/**
	 * Obtiene el color n-ésimo de la tabla de colores.
	 * @param index Indice del color a recuperar.
	 * @return Color n-ésimo de la tabla de colores.
	 */
	public Color get(int index)
	{
		return colors.get(index);
	}
	
	/**
	 * Número de fuentes en la tabla.
	 * @return
	 */
	public int size()
	{
		return colors.size();
	}
	
	/**
	 * Obtiene el índice de un color determinado en la tabla
	 * @param name Color a consultar.
	 * @return Indice del color consultado.
	 */
	public int IndexOf(Color color)
    {
        return colors.indexOf(color);
    }
}
