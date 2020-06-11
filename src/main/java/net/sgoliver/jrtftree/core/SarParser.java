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
 * Class:		SarParser
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

/**
 * Esta clase, utilizada por RtfReader, contiene todos los métodos necesarios para tratar cada uno de 
 * los tipos de elementos presentes en un documento RTF. Estos métodos serán llamados automáticamente 
 * durante el análisis del documento RTF realizado por la clase RtfReader.
 */
public abstract class SarParser //In Sync
{
	/**
	 * Este método se llama una sóla vez al comienzo del análisis del documento RTF.
	 */
    public abstract void startRtfDocument();

    /**
     * Este método se llama una sóla vez al final del análisis del documento RTF.
     */
    public abstract void endRtfDocument();

    /**
     * Este método se llama cada vez que se lee una llave de comienzo de grupo RTF.
     */
    public abstract void startRtfGroup();

    /**
     * Este método se llama cada vez que se lee una llave de fin de grupo RTF. 
     */
    public abstract void endRtfGroup();

    /**
     * Este método se llama cada vez que se lee una palabra clave RTF.
     * @param key Palabra clave leida del documento.
     * @param hasParam Indica si la palabra clave va acompañada de un parámetro.
     * @param param Parámetro que acompaña a la palabra clave. En caso de que la palabra clave no vaya acompañada de ningún parámetro, es decir, que el campo hasParam sea 'false', este campo contendrá el valor 0.
     */
    public abstract void rtfKeyword(String key, boolean hasParam, int param);

    /**
     * Este método se llama cada vez que se lee un símbolo de Control RTF.
     * @param key Símbolo de Control leido del documento.
     * @param hasParam Indica si el símbolo de Control va acompañado de un parámetro.
     * @param param Parámetro que acompaña al símbolo de Control. En caso de que el símbolo de Control no vaya acompañado de ningún parámetro, es decir, que el campo hasParam sea 'false', este campo contendrá el valor 0.
     */
    public abstract void rtfControl(String key, boolean hasParam, int param);

    /**
     * Este método se llama cada vez que se lee un fragmento de Texto del documento RTF.
     * @param text Texto leido del documento.
     */
    public abstract void rtfText(String text);
}
