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
 * Class:		RtfReader
 * Copyright:   2005 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * SF Project:	http://nrtftree.sourceforge.net
 *				http://sourceforge.net/projects/nrtftree
 * Date:		15/01/2006
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Esta clase proporciona los métodos necesarios para la carga y análisis secuencial de un documento RTF. 
 */
public class RtfReader
{
    private Reader rtf;			//Fichero/Cadena de entrada RTF
    private RtfLex lex;			//Analizador léxico para RTF
    private RtfToken tok;		//Token actual
    private SarParser reader;	//Rtf Reader
    
    /**
     * Constructor de la clase RtfReader.
     * @param reader Objeto del tipo SARParser que contienen los métodos necesarios para el tratamiento de los distintos elementos de un documento RTF.
     */
    public RtfReader(SarParser reader)
    {
        lex = null;
        tok = null;
        rtf = null;

        this.reader = reader;
    }

    /**
     * Carga un documento RTF dada la ruta del fichero que lo contiene.
     * @param path Ruta del fichero que contiene el documento RTF.
     * @return Resultado de la carga del documento. Si la carga se realiza correctamente se devuelve el valor 0.
     */
    public int loadRtfFile(String path)
    {
        //Resultado de la carga
        int res = 0;

        try
        {
	        //Se abre el fichero de entrada
	        rtf = new FileReader(path);
	
	        //Se crea el analizador léxico para RTF
	        lex = new RtfLex(rtf);
        }
        catch(IOException ex)
        {
        	res = -1;
        }

        //Se devuelve el resultado de la carga
        return res;
    }
    
    /**
     * Carga un documento RTF dada la cadena de caracteres que lo contiene.
     * @param text Cadena de caracteres que contiene el documento RTF.
     * @return Resultado de la carga del documento. Si la carga se realiza correctamente se devuelve el valor 0.
     */
    public int loadRtfText(String text)
    {
        //Resultado de la carga
        int res = 0;

        try
        {
	        //Se abre el fichero de entrada
	        rtf = new StringReader(text);
		
	        //Se crea el analizador léxico para RTF
	        lex = new RtfLex(rtf);
        }
        catch(IOException ex)
        {
        	res = -1;
        }

        //Se devuelve el resultado de la carga
        return res;
    }
    
    /**
     * Comienza el análisis del documento RTF y provoca la llamada a los distintos métodos del objeto IRtfReader indicado en el constructor de la clase.
     * @return Resultado del análisis del documento. Si la carga se realiza correctamente se devuelve el valor 0.
     */
    public int parse()
    {
        //Resultado del análisis
        int res = 0;

        try
        {
	        //Comienza el documento
	        reader.startRtfDocument();
	
	        //Se obtiene el primer token
	        tok = lex.nextToken();
	
	        while (tok.getTokenType() != RtfTokenType.EOF)
	        {
	            switch (tok.getTokenType())
	            {
	                case RtfTokenType.GROUP_START:
	                    reader.startRtfGroup();
	                    break;
	                case RtfTokenType.GROUP_END:
	                    reader.endRtfGroup();
	                    break;
	                case RtfTokenType.KEYWORD:
	                    reader.rtfKeyword(tok.getKey(), tok.getHasParam(), tok.getParam());
	                    break;
	                case RtfTokenType.CONTROL:
	                    reader.rtfControl(tok.getKey(), tok.getHasParam(), tok.getParam());
	                    break;
	                case RtfTokenType.TEXT:
	                    reader.rtfText(tok.getKey());
	                    break;
	                default:
	                    res = -1;
	                    break;
	            }
	
	            //Se obtiene el siguiente token
	            tok = lex.nextToken();
	        }
	
	        //Finaliza el documento
	        reader.endRtfDocument();
	
	        //Se cierra el stream
	        rtf.close();
        }
        catch(IOException ex)
        {
        	res = -1;
        }

        return res;
    }
}
