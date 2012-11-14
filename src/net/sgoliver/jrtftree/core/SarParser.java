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
 * Description:	Representa un documento RTF en forma de �rbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

/**
 * Esta clase, utilizada por RtfReader, contiene todos los m�todos necesarios para tratar cada uno de 
 * los tipos de elementos presentes en un documento RTF. Estos m�todos ser�n llamados autom�ticamente 
 * durante el an�lisis del documento RTF realizado por la clase RtfReader.
 */
public abstract class SarParser //In Sync
{
	/**
	 * Este m�todo se llama una s�la vez al comienzo del an�lisis del documento RTF.
	 */
    public abstract void startRtfDocument();

    /**
     * Este m�todo se llama una s�la vez al final del an�lisis del documento RTF.
     */
    public abstract void endRtfDocument();

    /**
     * Este m�todo se llama cada vez que se lee una llave de comienzo de grupo RTF.
     */
    public abstract void startRtfGroup();

    /**
     * Este m�todo se llama cada vez que se lee una llave de fin de grupo RTF. 
     */
    public abstract void endRtfGroup();

    /**
     * Este m�todo se llama cada vez que se lee una palabra clave RTF.
     * @param key Palabra clave leida del documento.
     * @param hasParam Indica si la palabra clave va acompa�ada de un par�metro.
     * @param param Par�metro que acompa�a a la palabra clave. En caso de que la palabra clave no vaya acompa�ada de ning�n par�metro, es decir, que el campo hasParam sea 'false', este campo contendr� el valor 0.
     */
    public abstract void rtfKeyword(String key, boolean hasParam, int param);

    /**
     * Este m�todo se llama cada vez que se lee un s�mbolo de Control RTF.
     * @param key S�mbolo de Control leido del documento.
     * @param hasParam Indica si el s�mbolo de Control va acompa�ado de un par�metro.
     * @param param Par�metro que acompa�a al s�mbolo de Control. En caso de que el s�mbolo de Control no vaya acompa�ado de ning�n par�metro, es decir, que el campo hasParam sea 'false', este campo contendr� el valor 0.
     */
    public abstract void rtfControl(String key, boolean hasParam, int param);

    /**
     * Este m�todo se llama cada vez que se lee un fragmento de Texto del documento RTF.
     * @param text Texto leido del documento.
     */
    public abstract void rtfText(String text);
}
