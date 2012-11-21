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
 * Class:		RtfDocument
 * Description:	Clase para la generación de documentos RTF.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import net.sgoliver.jrtftree.core.RtfNodeType;
import net.sgoliver.jrtftree.core.RtfTree;
import net.sgoliver.jrtftree.core.RtfTreeNode;

public class RtfDocument //In Sync
{
    private Charset encoding;
    private RtfFontTable fontTable;
    private RtfColorTable colorTable;
    private RtfTreeNode mainGroup;
    private RtfCharFormat currentFormat;
    private RtfParFormat currentParFormat;
    private RtfDocumentFormat docFormat;
    
    /**
     * Constructor de la clase RtfDocument.
     * @param enc Codificación del documento a generar.
     */
    public RtfDocument(Charset enc)
    {
        this.encoding = enc;

        fontTable = new RtfFontTable();
        fontTable.AddFont("Arial");  //Default font

        colorTable = new RtfColorTable();
        colorTable.addColor(Color.BLACK);  //Default color

        currentFormat = null;
        currentParFormat = new RtfParFormat();
        docFormat = new RtfDocumentFormat();

        mainGroup = new RtfTreeNode(RtfNodeType.GROUP);

        initializeTree();
    }

    /**
     * Constructor de la clase RtfDocument. Se utilizará la codificación por defecto del sistema.
     */
    public RtfDocument()
    {
    	this(Charset.forName("Cp1252"));
    }
    
    /**
     * Guarda el documento como fichero RTF en la ruta indicada.
     * @param path Ruta del fichero a crear.
     */
    public void save(String path) throws IOException
    {
    	RtfTree tree = GetTree();
    	
    	tree.saveRtf(path);
    }
    
    /**
     * Inserta un fragmento de texto en el documento con un formato de texto determinado.
     * @param text Texto a insertar.
     * @param format Formato del texto a insertar.
     */
    public void addText(String text, RtfCharFormat format)
    {
        updateFontTable(format);
        updateColorTable(format);

        updateCharFormat(format);

        insertText(text);
    }
    
    /**
     * Inserta un fragmento de texto en el documento con el formato de texto actual.
     * @param text Texto a insertar.
     */
    public void addText(String text)
    {
        insertText(text);
    }
    
    /**
     * Inserta un número determinado de saltos de línea en el documento.
     * @param n Número de saltos de línea a insertar.
     */
    public void addNewLine(int n)
    {
        for(int i=0; i<n; i++)
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "line", false, 0));
    }
    
    /**
     * Inserta un salto de línea en el documento.
     */
    public void addNewLine()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "line", false, 0));
    }
    
    /**
     * Inicia un nuevo párrafo.
     */
    public void addNewParagraph()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "par", false, 0));
    }
    
    /**
     * Inserta un número determinado de saltos de párrafo en el documento.
     * @param n Número de saltos de párrafo a insertar.
     */
    public void addNewParagraph(int n)
    {
        for (int i = 0; i < n; i++)
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "par", false, 0));
    }
  
    /**
     * Inicia un nuevo párrafo con el formato especificado.
     * @param format Formato del párrafo a insertar.
     */
    public void addNewParagraph(RtfParFormat format)
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "par", false, 0));

        updateParFormat(format);
    }
    
    /**
     * Inserta una imagen en el documento.
     * @param path Ruta de la imagen a insertar.
     * @param width Ancho deseado de la imagen en el documento.
     * @param height Alto deseado de la imagen en el documento.
     * @throws IOException 
     */
    public void addImage(String path, int width, int height) throws IOException
    {
        FileInputStream fStream = null;

        try
        {          
            File fInfo = new File(path);
            int numBytes = (int)fInfo.length();

            fStream = new FileInputStream(path);

            byte[] data = new byte[numBytes];
            
            fStream.read(data);

            StringBuilder hexdata = new StringBuilder();

            for (int i = 0; i < data.length; i++)
            {
                hexdata.append(getHexa(data[i]));
            }

            BufferedImage img = ImageIO.read(fInfo);

            RtfTreeNode imgGroup = new RtfTreeNode(RtfNodeType.GROUP);
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pict", false, 0));

            String format = "";
            if (path.toLowerCase().endsWith("wmf"))
                format = "emfblip";
            else
                format = "jpegblip";

            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, format, false, 0));
            
            
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "picw", true, img.getWidth() * 20));
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pich", true, img.getHeight() * 20));
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "picwgoal", true, width * 20));
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pichgoal", true, height * 20));
            imgGroup.appendChild(new RtfTreeNode(RtfNodeType.TEXT, hexdata.toString(), false, 0));

            mainGroup.appendChild(imgGroup);
        }
        finally
        {
            fStream.close();
        }
    }
    
    /**
     * Establece el formato de caracter por defecto.
     */
    public void resetCharFormat()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "plain", false, 0));
    }
    
    /**
     * Establece el formato de párrafo por defecto.
     */
    public void resetParFormat()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pard", false, 0));
    }
    
    /**
     * Establece el formato de caracter y párrafo por defecto.
     */
    public void resetFormat()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pard", false, 0));
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "plain", false, 0));
    }
    
    /**
     * Actualiza los valores de las propiedades de formato de documento.
     * @param format Formato de documento.
     */
    public void updateDocFormat(RtfDocumentFormat format)
    {
        docFormat.setMarginL(format.getMarginL());
        docFormat.setMarginR(format.getMarginR());
        docFormat.setMarginT(format.getMarginT());
        docFormat.setMarginB(format.getMarginB());
    }
   
    /**
     * Actualiza los valores de las propiedades de formato de texto y párrafo.
     * @param format Formato de texto a insertar.
     */
    public void updateCharFormat(RtfCharFormat format)
    {
        if (currentFormat != null)
        {
            setFormatColor(format.getColor());
            setFormatSize(format.getSize());
            setFormatFont(format.getFont());

            setFormatBold(format.isBold());
            setFormatItalic(format.isItalic());
            setFormatUnderline(format.isUnderline());
        }
        else //currentFormat == null
        {
            int indColor = colorTable.IndexOf(format.getColor());

            if (indColor == -1)
            {
                colorTable.addColor(format.getColor());
                indColor = colorTable.IndexOf(format.getColor());
            }

            int indFont = fontTable.indexOf(format.getFont());

            if (indFont == -1)
            {
                fontTable.AddFont(format.getFont());
                indFont = fontTable.indexOf(format.getFont());
            }

            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "cf", true, indColor));
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "fs", true, format.getSize() * 2));
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "f", true, indFont));

            if (format.isBold())
                mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "b", false, 0));

            if (format.isItalic())
                mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "i", false, 0));

            if (format.isUnderline())
                mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "ul", false, 0));

            currentFormat = new RtfCharFormat();
            currentFormat.setColor(format.getColor());
            currentFormat.setSize(format.getSize());
            currentFormat.setFont(format.getFont());
            currentFormat.setBold(format.isBold());
            currentFormat.setItalic(format.isItalic());
            currentFormat.setUnderline(format.isUnderline());
        }
    }
    
    /**
     * Establece el formato de párrafo pasado como parámetro.
     * @param format Formato de párrafo a utilizar.
     */
    public void updateParFormat(RtfParFormat format)
    {
        setAlignment(format.getAlignment());
        setLeftIndentation(format.getLeftIndentation());
        setRightIndentation(format.getRightIndentation());
    }
    
    /**
     * Estable la alineación del texto dentro del párrafo.
     * @param align Tipo de alineación.
     */
    public void setAlignment(int align)
    {
        if (currentParFormat.getAlignment() != align)
        {
            String keyword = "";

            switch (align)
            { 
                case TextAlignment.LEFT:
                    keyword = "ql";
                    break;
                case TextAlignment.RIGHT:
                    keyword = "qr";
                    break;
                case TextAlignment.CENTERED:
                    keyword = "qc";
                    break;
                case TextAlignment.JUSTIFIED:
                    keyword = "qj";
                    break;
            }

            currentParFormat.setAlignment(align);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, keyword, false, 0));
        }
    }
    
    /**
     * Establece la sangría izquierda del párrafo.
     * @param val Sangría izquierda en centímetros.
     */
    public void setLeftIndentation(float val)
    {
        if (currentParFormat.getLeftIndentation() != val)
        {
            currentParFormat.setLeftIndentation(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "li", true, calcTwips(val)));
        }
    }
    
    /**
     * Establece la sangría derecha del párrafo.
     * @param val Sangría derecha en centímetros.
     */
    public void setRightIndentation(float val)
    {
        if (currentParFormat.getRightIndentation() != val)
        {
            currentParFormat.setRightIndentation(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "ri", true, calcTwips(val)));
        }
    }
    
    /**
     * Establece el indicativo de fuente negrita.
     * @param val Indicativo de fuente negrita.
     */
    public void setFormatBold(boolean val)
    {
        if (currentFormat.isBold() != val)
        {
            currentFormat.setBold(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "b", val ? false : true, 0));
        }
    }
   
    /**
     * Establece el indicativo de fuente cursiva.
     * @param val Indicativo de fuente cursiva.
     */
    public void setFormatItalic(boolean val)
    {
        if (currentFormat.isItalic() != val)
        {
            currentFormat.setItalic(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "i", val ? false : true, 0));
        }
    }
    
    /**
     * Establece el indicativo de fuente subrayada.
     * @param val Indicativo de fuente subrayada.
     */
    public void setFormatUnderline(boolean val)
    {
        if (currentFormat.isUnderline() != val)
        {
            currentFormat.setUnderline(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "ul", val ? false : true, 0));
        }
    }
    
    /**
     * Establece el color de fuente actual.
     * @param val Color de la fuente.
     */
    public void setFormatColor(Color val)
    {
        if (currentFormat.getColor() != val)
        {
            int indColor = colorTable.IndexOf(val);

            if (indColor == -1)
            {
                colorTable.addColor(val);
                indColor = colorTable.IndexOf(val);
            }

            currentFormat.setColor(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "cf", true, indColor));
        }
    }
    
    /**
     * Establece el tamaño de fuente actual.
     * @param val Tamaño de la fuente.
     */
    public void setFormatSize(int val)
    {
        if (currentFormat.getSize() != val)
        {
            currentFormat.setSize(val);

            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "fs", true, val * 2));
        }
    }
    
    /**
     * Establece el tipo de letra actual.
     * @param val Tipo de letra.
     */
    public void setFormatFont(String val)
    {
        if (currentFormat.getFont() != val)
        {
            int indFont = fontTable.indexOf(val);

            if (indFont == -1)
            {
                fontTable.AddFont(val);
                indFont = fontTable.indexOf(val);
            }

            currentFormat.setFont(val);
            mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "f", true, indFont));
        }
    }
    
    /**
     * Obtiene el texto plano contenido en el documento RTF.
     * @return Texto plano contenido en el documento RTF.
     */
    public String getText()
    {
    	return GetTree().getText();
    }
    
    /**
     * Obtiene el código RTF del documento RTF.
     * @return Código RTF del documento RTF.
     */
    public String getRtf()
    {
    	return GetTree().getRtf();
    }
    
    /**
     * Obtiene el árbol RTF del documento actual.
     * @return Arbol RTF del documento actual.
     */
    public RtfTree getTree()
    {
    	return GetTree();
    }
    
    /**
     * Obtiene el árbol RTF equivalente al documento actual.
     * @return Árbol RTF equivalente al documento en el estado actual.
     */
    private RtfTree GetTree()
    {
        RtfTree tree = new RtfTree();
        tree.getRootNode().appendChild(mainGroup.cloneNode());

        insertFontTable(tree);
        insertColorTable(tree);
        insertGenerator(tree);
        insertDocSettings(tree);

        tree.getMainGroup().appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "par", false, 0));

        return tree;
    }
    
    /**
     * Obtiene el código hexadecimal de un entero.
     * @param code Número entero.
     * @return Código hexadecimal del entero pasado como parámetro.
     */
    private String getHexa(int code)
    {
        String hexa = Integer.toHexString(code);

        if (hexa.length() == 1)
        {
            hexa = "0" + hexa;
        }

        return hexa;
    }
    
    /**
     * Inserta el código RTF de la tabla de fuentes en el documento.
     */
    private void insertFontTable(RtfTree tree)
    {
        RtfTreeNode ftGroup = new RtfTreeNode(RtfNodeType.GROUP);
        
        ftGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "fonttbl", false, 0));

        for(int i=0; i<fontTable.size(); i++)
        {
            RtfTreeNode ftFont = new RtfTreeNode(RtfNodeType.GROUP);
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "f", true, i));
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "fnil", false, 0));
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.TEXT, fontTable.get(i) + ";", false, 0));

            ftGroup.appendChild(ftFont);
        }

        tree.getMainGroup().insertChild(5, ftGroup);
    }
    
    /**
     * Inserta el código RTF de la tabla de colores en el documento.
     */
    private void insertColorTable(RtfTree tree)
    {
        RtfTreeNode ctGroup = new RtfTreeNode(RtfNodeType.GROUP);

        ctGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "colortbl", false, 0));

        for (int i = 0; i < colorTable.size(); i++)
        {
            ctGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "red", true, colorTable.get(i).getRed()));
            ctGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "green", true, colorTable.get(i).getGreen()));
            ctGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "blue", true, colorTable.get(i).getBlue()));
            ctGroup.appendChild(new RtfTreeNode(RtfNodeType.TEXT, ";", false, 0));
        }

        tree.getMainGroup().insertChild(6, ctGroup);
    }
    
    /**
     * Inserta el código RTF de la aplicación generadora del documento.
     */
    private void insertGenerator(RtfTree tree)
    {
        RtfTreeNode genGroup = new RtfTreeNode(RtfNodeType.GROUP);

        genGroup.appendChild(new RtfTreeNode(RtfNodeType.CONTROL, "*", false, 0));
        genGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "generator", false, 0));
        genGroup.appendChild(new RtfTreeNode(RtfNodeType.TEXT, "NRtfTree Library 0.3.0;", false, 0));

        tree.getMainGroup().insertChild(7, genGroup);
    }
    
    /**
     * Inserta todos los nodos de texto y control necesarios para representar un texto determinado.
     * @param text Texto a insertar.
     */
    private void insertText(String text)
    {
        int i = 0;
        int code = 0;

        while(i < text.length())
        {
        	code = Character.codePointAt(text, i);

            if(code >= 32 && code < 128)
            {
                StringBuilder s = new StringBuilder("");

                while (i < text.length() && code >= 32 && code < 128)
                {
                    s.append(text.charAt(i));

                    i++;

                    if(i < text.length())
                    	code = Character.codePointAt(text, i);
                }

                mainGroup.appendChild(new RtfTreeNode(RtfNodeType.TEXT, s.toString(), false, 0));
            }
            else
            {           	
            	if (text.charAt(i) == '\t')
                {
                    mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "tab", false, 0));
                }
                else if (text.charAt(i) == '\n')
                {
                    mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "line", false, 0));
                }
                else
                {
                    if (code <= 255)
                    {
                    	ByteBuffer bytes = encoding.encode(CharBuffer.wrap(text, i, i+1));

                        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.CONTROL, "'", true, bytes.get(0) & 0xff));  //"& 0xff" para evitar bytes negativos
                    }
                    else
                    {
                        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "u", true, code));
                        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.TEXT, "?", false, 0));
                    }
                }

                i++;
            }
        }
    }
    
    /**
     * Actualiza la tabla de fuentes con una nueva fuente si es necesario.
     * @param format Formato a insertar en la tabla de fuentes.
     */
    private void updateFontTable(RtfCharFormat format)
    {
        if (fontTable.indexOf(format.getFont()) == -1)
        {
            fontTable.AddFont(format.getFont());
        }
    }

    /**
     * Actualiza la tabla de colores con un nuevo color si es necesario.
     * @param format Formato a insertar en la tabla de colores.
     */
    private void updateColorTable(RtfCharFormat format)
    {
        if (colorTable.IndexOf(format.getColor()) == -1)
        {
            colorTable.addColor(format.getColor());
        }
    }

    /// <summary>
    /// Inicializa el arbol RTF con todas las claves de la cabecera del documento.
    /// </summary>
    private void initializeTree()
    {
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "rtf", true, 1));
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "ansi", false, 0));
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "ansicpg", true, 1252)); //encoding.CodePage
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "deff", true, 0));
        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "deflang", true, 3082)); //CultureInfo.CurrentCulture.LCID

        mainGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pard", false, 0));
    }

    /// <summary>
    /// Inserta las propiedades de formato del documento
    /// </summary>
    private void insertDocSettings(RtfTree tree)
    {
        int indInicioTexto = tree.getMainGroup().getChildNodes().indexOf("pard");

        //Generic Properties
        
        tree.getMainGroup().insertChild(indInicioTexto, new RtfTreeNode(RtfNodeType.KEYWORD, "viewkind", true, 4));
        tree.getMainGroup().insertChild(indInicioTexto++, new RtfTreeNode(RtfNodeType.KEYWORD, "uc", true, 1));

        //RtfDocumentFormat Properties

        tree.getMainGroup().insertChild(indInicioTexto++, new RtfTreeNode(RtfNodeType.KEYWORD, "margl", true, calcTwips(docFormat.getMarginL())));
        tree.getMainGroup().insertChild(indInicioTexto++, new RtfTreeNode(RtfNodeType.KEYWORD, "margr", true, calcTwips(docFormat.getMarginR())));
        tree.getMainGroup().insertChild(indInicioTexto++, new RtfTreeNode(RtfNodeType.KEYWORD, "margt", true, calcTwips(docFormat.getMarginT())));
        tree.getMainGroup().insertChild(indInicioTexto++, new RtfTreeNode(RtfNodeType.KEYWORD, "margb", true, calcTwips(docFormat.getMarginB())));
    }

    /// <summary>
    /// Convierte entre centímetros y twips.
    /// </summary>
    /// <param name="centimeters">Valor en centímetros.</param>
    /// <returns>Valor en twips.</returns>
    private int calcTwips(float centimeters)
    {
        //1 inches = 2.54 centimeters
        //1 inches = 1440 twips
        //20 twip = 1 pixel

        //X centimetros --> (X*1440)/2.54 twips

        return (int)((centimeters * 1440F) / 2.54F);
    }
}
