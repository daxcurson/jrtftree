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
 * Class:		RtfLex
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.io.IOException;
import java.io.*;

/**
 * Analizador léxico (tokenizador) para documentos en formato RTF. Analiza el documento y devuelve de 
 * forma secuencial todos los elementos RTF leidos (tokens).
 */
public class RtfLex
{
	//Atributos
	private Reader rtf;
	private StringBuilder keysb;
	private StringBuilder parsb;
	private int c;

	//Constantes
	private static final int EOF = -1;
	
	//Constructores
	
	/**
	 * Constructor de la clase RtfLex
	 * @param rtfreader Stream del fichero a analizar.
	 */
    public RtfLex(Reader rtfreader) throws IOException
    {
        rtf = rtfreader;
        
        keysb = new StringBuilder();
        parsb = new StringBuilder();
        
        //Se lee el primer caracter del documento
        c = rtf.read();
    }
    
    //Métodos Públicos
    
    /**
     * Lee un nuevo token del documento RTF.
     */
    public RtfToken nextToken() throws IOException
    {
        //Se crea el nuevo token a devolver
        RtfToken token = new RtfToken();

        //Se ignoran los retornos de carro, tabuladores y caracteres nulos
        while (c == '\r' || c == '\n' || c == '\t' || c == '\0')
            c = rtf.read();

        //Se trata el caracter leido
        if (c != EOF)
        {
            switch (c)
            {
                case '{':
                    token.setTokenType(RtfTokenType.GROUP_START);
                    c = rtf.read();
                    break;
                case '}':
                    token.setTokenType(RtfTokenType.GROUP_END);
                    c = rtf.read();
                    break;
                case '\\':
                    parseKeyword(token);
                    break;
                default:
                    token.setTokenType(RtfTokenType.TEXT);
                    parseText(token);
                    break;
            }
        }
        else
        {
            //Fin de fichero
            token.setTokenType(RtfTokenType.EOF);
        }

        return token;
    }
    
    //Métodos Privados
    
    /**
     * Lee una palabra clave del documento RTF.
     * @param token Token RTF al que se asignará la palabra clave.
     * @throws IOException
     */
    private void parseKeyword(RtfToken token) throws IOException
    {
    	//Se limpian los StringBuilders
        keysb.setLength(0);
        parsb.setLength(0);
        
        int parametroInt = 0;
        boolean negativo = false;
        
        c = rtf.read();
        
        //Si el caracter leido no es una letra --> Se trata de un símbolo de control o un caracter especial: '\\', '\{' o '\}'
        if (!Character.isLetter((char)c))
        {
        	if (c == '\\' || c == '{' || c == '}')  //Caracter especial
            {
                token.setTokenType(RtfTokenType.TEXT);
                token.setKey(new String(new char[]{(char)c}));
            }
        	else  //Simbolo de control
        	{
	            token.setTokenType(RtfTokenType.CONTROL);
	            token.setKey(new String(new char[]{(char)c})); 
	
	            //Si se trata de un caracter especial (codigo de 8 bits) se lee el parámetro hexadecimal
	            if (token.getKey().equals("\'"))
	            {
	                String cod = "";
	
	                cod += (char)rtf.read();
	                cod += (char)rtf.read();
	
	                token.setHasParam(true);
	
	                token.setParam(Integer.parseInt(cod,16));
	            }
	            
	            //TODO: ¿Hay más símbolos de Control con parámetros?
        	}
        	
        	c = rtf.read();
        }
        else   //El caracter leido es una letra
        {
	        //Se lee la palabra clave completa (hasta encontrar un caracter no alfanumérico, por ejemplo '\' ó ' '      
	        while (Character.isLetter((char)c))
	        {
	            keysb.append((char)c);
	 
	            c = rtf.read();
	        }
	
	        //Se asigna la palabra clave leida
	        token.setTokenType(RtfTokenType.KEYWORD);
	        token.setKey(keysb.toString());
	
	        //Se comprueba si la palabra clave tiene parámetro
	        if (Character.isDigit((char)c) || c == '-')
	        {
	            token.setHasParam(true);
	
	            //Se comprubea si el parámetro es negativo
	            if (c == '-')
	            {
	                negativo = true;
	
	                c = rtf.read();
	            }
	
	            //Se lee el parámetro completo           
	            while (Character.isDigit((char)c))
	            {
	                parsb.append((char)c);
	
	                c = rtf.read();
	            }
	
	            parametroInt = Integer.parseInt(parsb.toString()); 
	
	            if (negativo)
	                parametroInt = -parametroInt;
	
	            //Se asigna el parámetro de la palabra clave
	            token.setParam(parametroInt);
	        }
	
	        if (c == ' ')
	        {
	            c = rtf.read();
	        }
        }
    }
    
    /**
     * Lee una cadena de Texto del documento RTF.
     * @param car Primer caracter de la cadena.
     * @param token Token RTF al que se asignará la palabra clave.
     */
    private void parseText(RtfToken token) throws IOException
    {
    	//Se limpia el StringBuilder
    	keysb.setLength(0);

        while (c != '\\' && c != '}' && c != '{' && c != EOF)
        {
            keysb.append((char)c);

            c = rtf.read();

            //Se ignoran los retornos de carro, tabuladores y caracteres nulos
            while (c == '\r' || c == '\n' || c == '\t' || c == '\0')
                c = rtf.read();
        }

        token.setKey(keysb.toString());
    }
}
