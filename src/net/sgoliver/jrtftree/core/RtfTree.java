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
 * Class:		RtfTree
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.sgoliver.jrtftree.util.InfoGroup;
import net.sgoliver.jrtftree.util.RtfColorTable;
import net.sgoliver.jrtftree.util.RtfFontTable;
import net.sgoliver.jrtftree.util.RtfStyleSheet;
import net.sgoliver.jrtftree.util.RtfStyleSheetTable;
import net.sgoliver.jrtftree.util.RtfStyleSheetType;

/**
 * Reresenta la estructura en forma de árbol de un documento RTF. 
 */
public class RtfTree
{
	//Atributos
	private RtfTreeNode rootNode;
	private Reader rtf;
	private RtfLex lex;
	private RtfToken tok;
	private int level;
	private boolean mergeSpecialCharacters;
	
	/**
	 * Constructor de la clase. 
	 */
    public RtfTree()
    {
        //Se crea el nodo raíz del documento
        rootNode = new RtfTreeNode(RtfNodeType.ROOT, "ROOT", false, 0);
        
        rootNode.setTree(this);
        
        //Se inicializa la propiedad mergeSpecialCharacters
        mergeSpecialCharacters = false;

        //Se inicializa la profundidad actual
        //level = 0;
    }
    
    /**
     * Realiza una copia exacta del árbol RTF.
     * @return Devuelve una copia exacta del árbol RTF.
     */
    public RtfTree cloneTree()
    {
        RtfTree clon = new RtfTree();

        clon.rootNode = this.rootNode.cloneNode(true);

        return clon;
    }
    
    /**
     * Carga un fichero en formato RTF.
     * @param path Ruta del fichero con el documento.
     * @return Se devuelve el valor 0 en caso de no producirse ningún error en la carga del documento. En caso contrario se devuelve el valor -1.
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
	
	        //Se carga el árbol con el contenido del documento RTF
	        res = parseRtfTree();
	
	        //Se cierra el stream
	        rtf.close();
        }
        catch(IOException ex)
        {
        	res = -1;
        }

        //Se devuelve el resultado de la carga
        return res;
    }

    /**
     * Carga una cadena de Texto con formato RTF.
     * @param text Cadena de Texto que contiene el documento.
     * @return Se devuelve el valor 0 en caso de no producirse ningún error en la carga del documento. En caso contrario se devuelve el valor -1.
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
	
	        //Se carga el árbol con el contenido del documento RTF
	        res = parseRtfTree();
	
	        //Se cierra el stream
	        rtf.close();
	    }
	    catch(IOException ex)
	    {
	    	res = -1;
	    }

        //Se devuelve el resultado de la carga
        return res;
    }

    /**
     * Escribe el código RTF del documento a un fichero.
     * @param filePath Ruta del fichero a generar con el documento RTF.
     * @throws IOException
     */
    public void saveRtf(String filePath) throws IOException
    { 
        //Stream de salida
        FileWriter sw = new FileWriter(filePath);

        //Se trasforma el árbol RTF a texto y se escribe al fichero
        sw.write(this.rootNode.getRtf());

        //Se cierra el fichero
        sw.flush();
        sw.close();
    }

    /**
     * Devuelve una representación textual del documento cargado.
     */
    public String toString()
    {
        String res = "";

        res = toStringInm(rootNode, 0, false);

        return res;
    }

    /**
     * Devuelve una representación textual del documento cargado. Añade el tipo de nodo a la izquierda del contenido del nodo.
     * @return Cadena de caracteres con la representación del documento.
     */
    public String toStringEx()
    {
        String res = "";

        res = toStringInm(rootNode, 0, true);

        return res;
    }

    /**
     * Devuelve la tabla de fuentes del documento RTF.
     * @return Tabla de fuentes del documento RTF.
     */
    public RtfFontTable getFontTable()
    {
        RtfFontTable tablaFuentes = new RtfFontTable();

		//Nodo raiz del documento
		RtfTreeNode root = this.rootNode;

		//Grupo principal del documento
		RtfTreeNode nprin = root.firstChild();

        //Buscamos la tabla de fuentes en el árbol
        boolean enc = false;
        int i = 0;
        RtfTreeNode ntf = new RtfTreeNode();  //Nodo con la tabla de fuentes

        while (!enc && i < nprin.getChildNodes().size())
        {
            if (nprin.getChildNodes().get(i).getNodeType() == RtfNodeType.GROUP &&
                nprin.getChildNodes().get(i).firstChild().getNodeKey().equals("fonttbl"))
            {
                enc = true;
                ntf = nprin.getChildNodes().get(i);
            }

            i++;
        }

        //Rellenamos el array de fuentes
        for (int j = 1; j < ntf.getChildNodes().size(); j++)
        {
            RtfTreeNode fuente = ntf.getChildNodes().get(j);

            int indiceFuente = -1;
            String nombreFuente = null;

            for(int k=0; k<fuente.getChildNodes().size(); k++)
            {
            	RtfTreeNode nodo = fuente.getChildNodes().get(k);
            	
                if (nodo.getNodeKey().equals("f"))
                    indiceFuente = nodo.getParameter();

                if (nodo.getNodeType() == RtfNodeType.TEXT)
                    nombreFuente = nodo.getNodeKey().substring(0, nodo.getNodeKey().length() - 1);
            }

            tablaFuentes.AddFont(indiceFuente, nombreFuente);
        }

        return tablaFuentes;
    }

    /**
     * Devuelve la tabla de colores del documento RTF.
     * @return Tabla de colores del documento RTF.
     */
    public RtfColorTable getColorTable()
    {
        RtfColorTable tablaColores = new RtfColorTable();

        //Nodo raiz del documento
        RtfTreeNode root = this.rootNode;

        //Grupo principal del documento
        RtfTreeNode nprin = root.firstChild();

        //Buscamos la tabla de colores en el árbol
        boolean enc = false;
        int i = 0;
        RtfTreeNode ntc = new RtfTreeNode();  //Nodo con la tabla de fuentes

        while (!enc && i < nprin.getChildNodes().size())
        {
            if (nprin.getChildNodes().get(i).getNodeType() == RtfNodeType.GROUP &&
                nprin.getChildNodes().get(i).firstChild().getNodeKey().equals("colortbl"))
            {
                enc = true;
                ntc = nprin.getChildNodes().get(i);
            }

            i++;
        }

        //Rellenamos el array de colores
        int rojo = 0;
        int verde = 0;
        int azul = 0;

        //Añadimos el color por defecto, en este caso el negro.
        //tabla.Add(Color.FromArgb(rojo,verde,azul));

        for (int j = 1; j < ntc.getChildNodes().size(); j++)
        {
            RtfTreeNode nodo = ntc.getChildNodes().get(j);

            if (nodo.getNodeType() == RtfNodeType.TEXT && nodo.getNodeKey().trim().equals(";"))
            {
                tablaColores.addColor(new Color(rojo,verde,azul));

                rojo = 0;
                verde = 0;
                azul = 0;
            }
            else if (nodo.getNodeType() == RtfNodeType.KEYWORD)
            {
            	if(nodo.getNodeKey().equals("red"))
            		rojo = nodo.getParameter();
            	else if(nodo.getNodeKey().equals("green"))
            		verde = nodo.getParameter();
            	else if(nodo.getNodeKey().equals("blue"))
            		azul = nodo.getParameter();
            	
            }
        }

        return tablaColores;
    }
    
    /**
     * Devuelve la tabla de hojas de estilo del documento RTF.
     * @return Tabla de hojas de estilo del documento RTF.
     */
    public RtfStyleSheetTable getStyleSheetTable()
    {
        RtfStyleSheetTable sstable = new RtfStyleSheetTable();

        RtfTreeNode sst = getMainGroup().selectSingleGroup("stylesheet");

        RtfNodeCollection styles = sst.getChildNodes();

        for (int i = 1; i < styles.size(); i++)
        {
            RtfTreeNode style = styles.get(i);

            RtfStyleSheet rtfss = ParseStyleSheet(style);

            sstable.addStyleSheet(rtfss.getIndex(), rtfss);
        }

        return sstable;
    }

    /**
     * Devuelve la información contenida en el grupo "\info" del documento RTF.
     * @return Objeto InfoGroup con la información del grupo "\info" del documento RTF.
     */
    public InfoGroup getInfoGroup()
    {
        InfoGroup info = null;

        RtfTreeNode infoNode = this.rootNode.selectSingleNode("info");

        //Si existe el nodo "\info" exraemos toda la información.
        if (infoNode != null)
        {
            RtfTreeNode auxnode = null;

            info = new InfoGroup();

            //Title
            if ((auxnode = this.rootNode.selectSingleNode("title")) != null)
                info.setTitle(auxnode.nextSibling().getNodeKey());

            //Subject
            if ((auxnode = this.rootNode.selectSingleNode("subject")) != null)
                info.setSubject(auxnode.nextSibling().getNodeKey());

            //Author
            if ((auxnode = this.rootNode.selectSingleNode("author")) != null)
                info.setAuthor(auxnode.nextSibling().getNodeKey());

            //Manager
            if ((auxnode = this.rootNode.selectSingleNode("manager")) != null)
                info.setManager(auxnode.nextSibling().getNodeKey());

            //Company
            if ((auxnode = this.rootNode.selectSingleNode("company")) != null)
                info.setCompany(auxnode.nextSibling().getNodeKey());

            //Operator
            if ((auxnode = this.rootNode.selectSingleNode("operator")) != null)
                info.setOperator(auxnode.nextSibling().getNodeKey());

            //Category
            if ((auxnode = this.rootNode.selectSingleNode("category")) != null)
                info.setCategory(auxnode.nextSibling().getNodeKey());

            //Keywords
            if ((auxnode = this.rootNode.selectSingleNode("keywords")) != null)
                info.setKeywords(auxnode.nextSibling().getNodeKey());

            //Comments
            if ((auxnode = this.rootNode.selectSingleNode("comment")) != null)
                info.setComment(auxnode.nextSibling().getNodeKey());

            //Document comments
            if ((auxnode = this.rootNode.selectSingleNode("doccomm")) != null)
                info.setDoccomm(auxnode.nextSibling().getNodeKey());

            //Hlinkbase (The base address that is used for the path of all relative hyperlinks inserted in the document)
            if ((auxnode = this.rootNode.selectSingleNode("hlinkbase")) != null)
                info.setHlinkbase(auxnode.nextSibling().getNodeKey());

            //Version
            if ((auxnode = this.rootNode.selectSingleNode("version")) != null)
                info.setVersion(auxnode.getParameter());

            //Internal Version
            if ((auxnode = this.rootNode.selectSingleNode("vern")) != null)
                info.setInternalVersion(auxnode.getParameter());

            //Editing Time
            if ((auxnode = this.rootNode.selectSingleNode("edmins")) != null)
                info.setEditingTime(auxnode.getParameter());

            //Number of Pages
            if ((auxnode = this.rootNode.selectSingleNode("nofpages")) != null)
                info.setNofpages(auxnode.getParameter());

            //Number of Chars
            if ((auxnode = this.rootNode.selectSingleNode("nofchars")) != null)
                info.setNofchars(auxnode.getParameter());

            //Number of Words
            if ((auxnode = this.rootNode.selectSingleNode("nofwords")) != null)
                info.setNofwords(auxnode.getParameter());

            //Id
            if ((auxnode = this.rootNode.selectSingleNode("id")) != null)
                info.setId(auxnode.getParameter());

            //Creation DateTime
            if ((auxnode = this.rootNode.selectSingleNode("creatim")) != null)
                info.setCreationTime(parseDateTime(auxnode.getParentNode()));

            //Revision DateTime
            if ((auxnode = this.rootNode.selectSingleNode("revtim")) != null)
                info.setRevisionTime(parseDateTime(auxnode.getParentNode()));
            
            //Last Print Time
            if ((auxnode = this.rootNode.selectSingleNode("printim")) != null)
                info.setLastPrintTime(parseDateTime(auxnode.getParentNode()));

            //Backup Time
            if ((auxnode = this.rootNode.selectSingleNode("buptim")) != null)
                info.setBackupTime(parseDateTime(auxnode.getParentNode()));
        }

        return info;
    }
    
    /**
     * Devuelve la tabla de códigos con la que está codificado el documento RTF.
     * @return Tabla de códigos del documento RTF. Si no está especificada en el documento se devuelve la tabla de códigos actual del sistema.
     */
    public Charset getEncoding()
    {
        //Contributed by Jan Stuchlík

    	Charset encoding = Charset.defaultCharset();

        RtfTreeNode cpNode = rootNode.selectSingleNode("ansicpg");

        if (cpNode != null)
        {
            encoding = Charset.forName("Cp" + cpNode.getParameter());
        }

        return encoding;
    }

    //Métodos Privados
    
    /**
     * Analiza el documento y lo carga con estructura de árbol.
     * @return Se devuelve el valor 0 en caso de no producirse ningún error en la carga del documento. En caso contrario se devuelve el valor -1.
     */
    private int parseRtfTree()
    {
        //Resultado de la carga del documento
        int res = 0;
        
        //Codificación por defecto del documento
        Charset encoding = Charset.defaultCharset();

        //Nodo actual
        RtfTreeNode curNode = rootNode;

        //Nuevos nodos para construir el árbol RTF
        RtfTreeNode newNode = null;

        try
        {
	        //Se obtiene el primer token
	        tok = lex.nextToken();
	
	        while (tok.getTokenType() != RtfTokenType.EOF)
	        {
	            switch (tok.getTokenType())
	            {
	                case RtfTokenType.GROUP_START:
	                    newNode = new RtfTreeNode(RtfNodeType.GROUP, "GROUP", false, 0);
	                    curNode.appendChild(newNode);
	                    curNode = newNode;
	                    level++;
	                    break;
	                case RtfTokenType.GROUP_END:
	                    curNode = curNode.getParentNode();
	                    level--;
	                    break;
	                case RtfTokenType.KEYWORD:
	                case RtfTokenType.CONTROL:
	                case RtfTokenType.TEXT:
	                	if (mergeSpecialCharacters)
                        {
                            //Contributed by Jan Stuchlík
                            boolean isText = tok.getTokenType() == RtfTokenType.TEXT || (tok.getTokenType() == RtfTokenType.CONTROL && tok.getKey().equals("'"));
                            if (curNode.lastChild() != null && (curNode.lastChild().getNodeType() == RtfNodeType.TEXT && isText))
                            {
                                if (tok.getTokenType() == RtfTokenType.TEXT)
                                {
                                    curNode.lastChild().setNodeKey(curNode.lastChild().getNodeKey() + tok.getKey());
                                    break;
                                }
                                if (tok.getTokenType() == RtfTokenType.CONTROL && tok.getKey().equals("'"))
                                {
                                    curNode.lastChild().setNodeKey(curNode.lastChild().getNodeKey() + decodeControlChar(tok.getParam(), encoding));
                                    break;
                                }
                            }
                            else
                            {
                                //Primer caracter especial \'
                                if (tok.getTokenType() == RtfTokenType.CONTROL && tok.getKey().equals("'"))
                                {
                                    newNode = new RtfTreeNode(RtfNodeType.TEXT, decodeControlChar(tok.getParam(), encoding), false, 0);
                                    curNode.appendChild(newNode);
                                    break;
                                }
                            }
                        }

                        newNode = new RtfTreeNode(tok);
                        curNode.appendChild(newNode);

                        if (mergeSpecialCharacters)
                        {
                            //Contributed by Jan Stuchlík
                            if (level == 1 && newNode.getNodeType() == RtfNodeType.KEYWORD && newNode.getNodeKey().equals("ansicpg"))
                            {
                                encoding = Charset.forName("Cp" + newNode.getParameter());
                            }
                        }

	                    break;
	                default:
	                    res = -1;
	                    break;
	            }
	
	            //Se obtiene el siguiente token
	            tok = lex.nextToken();
	        }
	        
	        //Si el nivel actual no es 0 ( == Algun grupo no está bien formado )
	        if (level != 0)
	        {
	            res = -1;
	        }
        }
        catch(IOException ex)
        {
        	res = -1;
        }

        //Se devuelve el resultado de la carga
        return res;
    }

    /**
     * Decodifica un caracter especial indicado por su código decimal
     * @param code Código del caracter especial (\')
     * @param enc Codificación utilizada para decodificar el caracter especial.
     * @return Caracter especial decodificado.
     */
    private static String decodeControlChar(int code, Charset enc)
    {
        return enc.decode(ByteBuffer.wrap(new byte[] {(byte)code})).toString();                
    }
    
    /**
     * Método auxiliar para generar la representación Textual del documento RTF.
     * @param curNode Nodo actual del árbol.
     * @param level Nivel actual en árbol.
     * @param showNodeTypes Indica si se mostrará el tipo de cada nodo del árbol.
     * @return Representación Textual del nodo 'curNode' con nivel 'level'.
     */
    private String toStringInm(RtfTreeNode curNode, int level, boolean showNodeTypes)
    {
        StringBuilder res = new StringBuilder();

        RtfNodeCollection children = curNode.getChildNodes();

        for (int i = 0; i < level; i++)
            res.append("  ");

        if (curNode.getNodeType() == RtfNodeType.ROOT)
            res.append("ROOT\r\n");
        else if (curNode.getNodeType() == RtfNodeType.GROUP)
            res.append("GROUP\r\n");
        else
        {
            if (showNodeTypes)
            {
                res.append(RtfNodeType.toString(curNode.getNodeType()));
                res.append(": ");
            }

            res.append(curNode.getNodeKey());

            if (curNode.getHasParameter())
            {
                res.append(" ");
                res.append(String.valueOf(curNode.getParameter()));
            }

            res.append("\r\n");
        }

        if (children != null)
        {
	        for(int i=0; i<children.size(); i++)
	        {
	        	RtfTreeNode node = children.get(i); 
	            res.append(toStringInm(node, level + 1, showNodeTypes));
	        }
        }

        return res.toString();
    }

    /**
     * Parsea una fecha con formato "\yr2005\mo12\dy2\hr22\min56\sec15"
     * @param group Grupo RTF con la fecha.
     * @return Objeto DateTime con la fecha leida.
     */
    private Calendar parseDateTime(RtfTreeNode group)
    {
        Calendar dt = null;

        int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

        for(int i=0; i<group.getChildNodes().size(); i++)
        {
        	RtfTreeNode node =  group.getChildNodes().get(i);
        	
            if(node.getNodeKey().equals("yr"))
          		year = node.getParameter();
            else if(node.getNodeKey().equals("mo"))
                month = node.getParameter();
            else if(node.getNodeKey().equals("dy"))
                day = node.getParameter();
            else if(node.getNodeKey().equals("hr"))
                hour = node.getParameter();
            else if(node.getNodeKey().equals("min"))
                min = node.getParameter();
            else if(node.getNodeKey().equals("sec"))
                sec = node.getParameter();
        }

        dt = new GregorianCalendar(year, (month != 0 ? month-1 : 0), day, hour, min, sec);

        return dt;
    }
    
    /**
     * Extrae el texto de un árbol RTF.
     * @return Texto plano del documento.
     */
    private String convertToText()
    {
        RtfTreeNode pardNode =
            this.rootNode.firstChild().selectSingleChildNode("pard");

        int pPard = this.rootNode.firstChild().getChildNodes().indexOf(pardNode);

        Charset enc = this.getEncoding();

        return convertToTextAux(this.rootNode.firstChild(), pPard, enc);
    }
    
    /**
     * Extrae el texto de un nodo RTF (Auxiliar de ConvertToText())
     * @param curNode Nodo actual.
     * @param prim Nodo a partir del que convertir.
     * @param enc Codificación del documento.
     * @return Texto plano del documento.
     */
    private String convertToTextAux(RtfTreeNode curNode, int prim, Charset enc)
    {
        StringBuilder res = new StringBuilder("");

        RtfTreeNode nprin = curNode;

        RtfTreeNode nodo = new RtfTreeNode();

        for (int i = prim; i < nprin.getChildNodes().size(); i++)
        {
            nodo = nprin.getChildNodes().get(i);

            if (nodo.getNodeType() == RtfNodeType.GROUP)
            {
                int indkw = nodo.firstChild().getNodeKey().equals("*") ? 1 : 0;

                if (!nodo.getChildNodes().get(indkw).getNodeKey().equals("pict") &&
                    !nodo.getChildNodes().get(indkw).getNodeKey().equals("object") &&
                    !nodo.getChildNodes().get(indkw).getNodeKey().equals("fldinst"))
                {
                    res.append(convertToTextAux(nodo, 0, enc));
                }
            }
            else if (nodo.getNodeType() == RtfNodeType.CONTROL)
            {
                if (nodo.getNodeKey().equals("'"))
                    res.append(decodeControlChar(nodo.getParameter(), enc));
            }
            else if (nodo.getNodeType() == RtfNodeType.TEXT)
            {
                res.append(nodo.getNodeKey());
            }
            else if (nodo.getNodeType() == RtfNodeType.KEYWORD)
            {
                if (nodo.getNodeKey().equals("par"))
                    res.append("\r\n");
            }
        }

        return res.toString();
    }
    
    private RtfStyleSheet ParseStyleSheet(RtfTreeNode ssnode)
    {
        RtfStyleSheet rss = new RtfStyleSheet();

        for(int i=0; i<ssnode.getChildNodes().size(); i++)
        {
        	RtfTreeNode node = ssnode.getChildNodes().get(i);
        	
            if (node.getNodeKey().equals("cs"))
            {
                rss.setType(RtfStyleSheetType.CHARACTER);
                rss.setIndex(node.getParameter());
            }
            else if (node.getNodeKey().equals("s"))
            {
                rss.setType(RtfStyleSheetType.PARAGRAPH);
                rss.setIndex(node.getParameter());
            }
            else if (node.getNodeKey().equals("ds"))
            {
                rss.setType(RtfStyleSheetType.SECTION);
                rss.setIndex(node.getParameter());
            }
            else if (node.getNodeKey().equals("ts"))
            {
                rss.setType(RtfStyleSheetType.TABLE);
                rss.setIndex(node.getParameter());
            }
            else if (node.getNodeKey().equals("additive"))
            {
                rss.setAdditive(true);
            }
            else if (node.getNodeKey().equals("sbasedon"))
            {
                rss.setBasedOn(node.getParameter());
            }
            else if (node.getNodeKey().equals("snext"))
            {
                rss.setNext(node.getParameter());
            }
            else if (node.getNodeKey().equals("sautoupd"))
            {
                rss.setAutoUpdate(true);
            }
            else if (node.getNodeKey().equals("shidden"))
            {
                rss.setHidden(true);
            }
            else if (node.getNodeKey().equals("slink"))
            {
                rss.setLink(node.getParameter());
            }
            else if (node.getNodeKey().equals("slocked"))
            {
                rss.setLocked(true);
            }
            else if (node.getNodeKey().equals("spersonal"))
            {
                rss.setPersonal(true);
            }
            else if (node.getNodeKey().equals("scompose"))
            {
                rss.setCompose(true);
            }
            else if (node.getNodeKey().equals("sreply"))
            {
                rss.setReply(true);
            }
            else if (node.getNodeKey().equals("styrsid"))
            {
                rss.setStyrsid(node.getParameter());
            }
            else if (node.getNodeKey().equals("ssemihidden"))
            {
                rss.setSemiHidden(true);
            }
            else if (node.getNodeType() == RtfNodeType.GROUP &&
                     (node.getChildNodes().get(0).getNodeKey().equals("*") && 
                    		 node.getChildNodes().get(1).getNodeKey().equals("keycode")))
            {
            	RtfNodeCollection col = new RtfNodeCollection();

                for (int j = 2; j < node.getChildNodes().size(); j++)
                {
                    col.add(node.getChildNodes().get(j).cloneNode(true));
                }
                
                rss.setKeyCode(col);
            }
            else if (node.getNodeType() == RtfNodeType.TEXT)
            {
                rss.setName(node.getNodeKey().substring(0,node.getNodeKey().length()-1));
            }
            else
            {
                if(!node.getNodeKey().equals("*"))
                    rss.getFormatting().add(node);
            }
        }

        return rss;
    }
    
    //Propiedades
    
    /**
     * Obtiene el nodo raíz del árbol RTF.
     */
    public RtfTreeNode getRootNode()
    {
    	return rootNode;
    }
    
    /**
     * Devuelve el grupo principal del documento.
     * @return Grupo principal del documento.
     */
    public RtfTreeNode getMainGroup()
    {
    	//Se devuelve el grupo principal (null en caso de no existir)
        if (rootNode.hasChildNodes())
            return rootNode.getChildNodes().get(0);
        else
            return null;
    }
    
    /**
     * Obtiene el código RTF completo del documento.
     * @return Código RTF del documento completo.
     */
    public String getRtf()
    {
    	return rootNode.getRtf();
    }
    
    /**
     * Indica si se decodifican los caracteres especiales (\') uniéndolos a nodos de texto contiguos.
     * @return
     */
    public boolean getMergeSpecialCharacters()
    {
    	return mergeSpecialCharacters;
    }
    
    /**
     * Indica si se decodifican los caracteres especiales (\') uniéndolos a nodos de texto contiguos.
     * @param value
     */
    public void setMergeSpecialCharacters(boolean value)
    {
    	mergeSpecialCharacters = value;
    }
    
    /**
     * Devuelve el texto plano del documento.
     * @return Texto plano del documento.
     */
    public String getText()
    {
    	return convertToText();
    }
}
