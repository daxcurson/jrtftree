package net.sgoliver.jrtftree.core;

import java.awt.Color;
import java.util.Hashtable;

import net.sgoliver.jrtftree.util.RtfColorTable;
import net.sgoliver.jrtftree.util.RtfFontTable;

public class RtfMerger 
{
    private RtfTree baseRtfDoc = null;
    private boolean removeLastPar;

    private Hashtable<String, RtfTree> placeHolder = null;

    /**
     * Constructor de la clase RtfMerger. 
     * @param sSourceDocFullPathName Ruta del documento plantilla.
     * @param bolRemoveLastParCmd Indica si se debe eliminar el �ltimo nodo \par de los documentos insertados en la plantilla.
     */
    public RtfMerger(String sSourceDocFullPathName, boolean bolRemoveLastParCmd)
    {
        //Se carga el documento origen
        baseRtfDoc = new RtfTree();
        baseRtfDoc.loadRtfFile(sSourceDocFullPathName);

        //Indicativo de eliminaci�n del �ltimo nodo \par para documentos insertados
        removeLastPar = bolRemoveLastParCmd;

        //Se crea la lista de par�metros de sustituci�n (placeholders)
        placeHolder = new Hashtable<String, RtfTree>();
    }

    /**
     * Asocia un nuevo par�metro de sustituci�n (placeholder) con la ruta del documento a insertar. 
     * @param ph Nombre del placeholder.
     * @param path Ruta del documento a insertar.
     */
    public void addPlaceHolder(String ph, String path)
    {
        RtfTree tree = new RtfTree();

        int res = tree.loadRtfFile(path);

        if (res == 0)
        {
            placeHolder.put(ph, tree);
        }
    }

    /**
     * Desasocia un par�metro de sustituci�n (placeholder) con la ruta del documento a insertar.
     * @param ph Nombre del placeholder.
     */
    public void removePlaceHolder(String ph)
    {
        placeHolder.remove(ph);
    }

    /**
     * Realiza la combinaci�n de los documentos RTF.
     * @return Devuelve el �rbol RTF resultado de la fusi�n.
     */
    public RtfTree merge()
    {
        //Se obtiene el grupo principal del �rbol
        RtfTreeNode parentNode = baseRtfDoc.getMainGroup();

        //Si el documento tiene grupo principal
        if (parentNode != null)
        {
            //Se analiza el texto del documento en busca de par�metros de reemplazo y se combinan los documentos
            analizeTextContent(parentNode);
        }

        return baseRtfDoc;
    }

    /**
     * Devuelve la lista de par�metros de sustituci�n con el formato: [string, RtfTree]
     * @return Lista de par�metros de sustituci�n con el formato: [string, RtfTree]
     */
    public Hashtable<String, RtfTree> getPlaceholders()
    {
    	return placeHolder;
    }

    /**
     * Analiza el texto del documento en busca de par�metros de reemplazo y combina los documentos.
     * @param parentNode Nodo del �rbol a procesar.
     */
    private void analizeTextContent(RtfTreeNode parentNode)
    {
        RtfTree docToInsert = null;
        int indPH;

        //Si el nodo es de tipo grupo y contiene nodos hijos
        if (parentNode != null && parentNode.hasChildNodes())
        {
            //Se recorren todos los nodos hijos
            for (int iNdIndex = 0; iNdIndex < parentNode.getChildNodes().size(); iNdIndex++)
            {
                //Nodo actual
                RtfTreeNode currNode = parentNode.getChildNodes().get(iNdIndex);

                //Si el nodo actual es de tipo Texto se buscan etiquetas a reemplazar
                if (currNode.getNodeType() == RtfNodeType.TEXT)
                {
                    docToInsert = null;

                    Object[] keys = placeHolder.keySet().toArray();
                    
                    //Se recorren todas las etiquetas configuradas
                    for(int i=0; i<keys.length; i++)
                    {	
                    	String ph = (String)keys[i];
                    	
                        //Se busca la siguiente ocurrencia de la etiqueta actual
                        indPH = currNode.getNodeKey().indexOf(ph);

                        //Si se ha encontrado una etiqueta
                        if (indPH != -1)
                        {
                            //Se recupera el �rbol a insertar en la etiqueta actual
                            docToInsert = placeHolder.get(ph).cloneTree();

                            //Se inserta el nuevo �rbol en el �rbol base
                            mergeCore(parentNode, iNdIndex, docToInsert, ph, indPH);

                            //Como puede que el nodo actual haya cambiado decrementamos el �ndice
                            //y salimos del bucle para analizarlo de nuevo
                            iNdIndex--;
                            break;
                        }
                    }
                }
                else
                {
                    //Si el nodo actual tiene hijos se analizan los nodos hijos
                    if (currNode.hasChildNodes())
                    {
                        analizeTextContent(currNode);
                    }
                }
            }
        }
    }

    /**
     * Inserta un nuevo �rbol en el lugar de una etiqueta de texto del �rbol base.
     * @param parentNode Nodo de tipo grupo que se est� procesando.
     * @param iNdIndex �ndice (dentro del grupo padre) del nodo texto que se est� procesando.
     * @param docToInsert Nuevo �rbol RTF a insertar.
     * @param strCompletePlaceholder Texto del la etiqueta que se va a reemplazar.
     * @param intPlaceHolderNodePos Posici�n de la etiqueta que se va a reemplazar dentro del nodo texto que se est� procesando.
     */
    private void mergeCore(RtfTreeNode parentNode, int iNdIndex, RtfTree docToInsert, String strCompletePlaceholder, int intPlaceHolderNodePos)
    {
        //Si el documento a insertar no est� vac�o
        if (docToInsert.getRootNode().hasChildNodes() == true)
        {
            int currentIndex = iNdIndex + 1;

            //Se combinan las tablas de colores y se ajustan los colores del documento a insertar
            mainAdjustColor(docToInsert);

            //Se combinan las tablas de fuentes y se ajustan las fuentes del documento a insertar
            mainAdjustFont(docToInsert);

            //Se elimina la informaci�n de cabecera del documento a insertar (colores, fuentes, info, ...)
            cleanToInsertDoc(docToInsert);

            //Si el documento a insertar tiene contenido
            if (docToInsert.getRootNode().firstChild().hasChildNodes())
            {
                //Se inserta el documento nuevo en el �rbol base
                execMergeDoc(parentNode, docToInsert, currentIndex);
            }

            //Si la etiqueta no est� al final del nodo texto:
            //Se inserta un nodo de texto con el resto del texto original (eliminando la etiqueta)
            if (parentNode.getChildNodes().get(iNdIndex).getNodeKey().length() != (intPlaceHolderNodePos + strCompletePlaceholder.length()))
            {
                //Se inserta un nodo de texto con el resto del texto original (eliminando la etiqueta)
                String remText = 
                    parentNode.getChildNodes().get(iNdIndex).getNodeKey().substring(
                        parentNode.getChildNodes().get(iNdIndex).getNodeKey().indexOf(strCompletePlaceholder) + strCompletePlaceholder.length());

                parentNode.insertChild(currentIndex + 1, new RtfTreeNode(RtfNodeType.TEXT, remText, false, 0));
            }

            //Si la etiqueta reemplazada estaba al principio del nodo de texto eliminamos el nodo
            //original porque ya no es necesario
            if (intPlaceHolderNodePos == 0)
            {
                parentNode.removeChild(iNdIndex);
            }
            //En otro caso lo sustituimos por el texto previo a la etiqueta
            else  
            {
                parentNode.getChildNodes().get(iNdIndex).setNodeKey( 
                    parentNode.getChildNodes().get(iNdIndex).getNodeKey().substring(0, intPlaceHolderNodePos));
            }
        }
    }

    /**
     * Obtiene el c�digo de la fuente pasada como par�metro, insert�ndola en la tabla de fuentes si es necesario.
     * @param fontDestTbl Tabla de fuentes resultante.
     * @param sFontName Fuente buscada.
     * @return C�digo de la fuente pasada como par�metro
     */
    private int getFontID(RtfFontTable fontDestTbl, String sFontName)
    {
        int iExistingFontID = -1;

        if ((iExistingFontID = fontDestTbl.indexOf(sFontName)) == -1)
        {
            fontDestTbl.AddFont(sFontName);
            iExistingFontID = fontDestTbl.indexOf(sFontName);

            RtfNodeCollection nodeListToInsert = baseRtfDoc.getRootNode().selectNodes("fonttbl");

            RtfTreeNode ftFont = new RtfTreeNode(RtfNodeType.GROUP);
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "f", true, iExistingFontID));
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "fnil", false, 0));
            ftFont.appendChild(new RtfTreeNode(RtfNodeType.TEXT, sFontName + ";", false, 0));
            
            nodeListToInsert.get(0).getParentNode().appendChild(ftFont);
        }

        return iExistingFontID;
    }

    /**
     * Obtiene el c�digo del color pasado como par�metro, insert�ndolo en la tabla de colores si es necesario.
     * @param colorDestTbl Tabla de colores resultante.
     * @param iColorName Color buscado.
     * @return C�digo del color pasado como par�metro
     */
    private int getColorID(RtfColorTable colorDestTbl, Color iColorName)
    {
        int iExistingColorID;

        if ((iExistingColorID = colorDestTbl.IndexOf(iColorName)) == -1)
        {
            iExistingColorID = colorDestTbl.size();
            colorDestTbl.addColor(iColorName);

            RtfNodeCollection nodeListToInsert = baseRtfDoc.getRootNode().selectNodes("colortbl");

            nodeListToInsert.get(0).getParentNode().appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "red", true, iColorName.getRed()));
            nodeListToInsert.get(0).getParentNode().appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "green", true, iColorName.getGreen()));
            nodeListToInsert.get(0).getParentNode().appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "blue", true, iColorName.getBlue()));
            nodeListToInsert.get(0).getParentNode().appendChild(new RtfTreeNode(RtfNodeType.TEXT, ";", false, 0));
        }

        return iExistingColorID;
    }

    /**
     * Ajusta las fuentes del documento a insertar.
     * @param docToInsert Documento a insertar.
     */
    private void mainAdjustFont(RtfTree docToInsert)
    {
        RtfFontTable fontDestTbl = baseRtfDoc.getFontTable();
        RtfFontTable fontToCopyTbl = docToInsert.getFontTable();

        adjustFontRecursive(docToInsert.getRootNode(), fontDestTbl, fontToCopyTbl);
    }

    /**
     * Ajusta las fuentes del documento a insertar.
     * @param parentNode Nodo grupo que se est� procesando.
     * @param fontDestTbl Tabla de fuentes resultante.
     * @param fontToCopyTbl Tabla de fuentes del documento a insertar.
     */
    private void adjustFontRecursive(RtfTreeNode parentNode, RtfFontTable fontDestTbl, RtfFontTable fontToCopyTbl)
    {
        if (parentNode != null && parentNode.hasChildNodes())
        {
            for (int iNdIndex = 0; iNdIndex < parentNode.getChildNodes().size(); iNdIndex++)
            {
                if (parentNode.getChildNodes().get(iNdIndex).getNodeType() == RtfNodeType.KEYWORD &&
                    (parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("f") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("stshfdbch") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("stshfloch") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("stshfhich") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("stshfbi") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("deff") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("af")) &&
                     parentNode.getChildNodes().get(iNdIndex).getHasParameter() == true)
                {
                    parentNode.getChildNodes().get(iNdIndex).setParameter(
                    		getFontID(fontDestTbl, 
                    				  fontToCopyTbl.get(parentNode.getChildNodes().get(iNdIndex).getParameter())));
                }

                adjustFontRecursive(parentNode.getChildNodes().get(iNdIndex), fontDestTbl, fontToCopyTbl);
            }
        }
    }

    /**
     * Ajusta los colores del documento a insertar.
     * @param docToInsert Documento a insertar.
     */
    private void mainAdjustColor(RtfTree docToInsert)
    {
        RtfColorTable colorDestTbl = baseRtfDoc.getColorTable();
        RtfColorTable colorToCopyTbl = docToInsert.getColorTable();

        adjustColorRecursive(docToInsert.getRootNode(), colorDestTbl, colorToCopyTbl);
    }

    /**
     * Ajusta los colores del documento a insertar.
     * @param parentNode Nodo grupo que se est� procesando.
     * @param colorDestTbl Tabla de colores resultante.
     * @param colorToCopyTbl Tabla de colores del documento a insertar.
     */
    private void adjustColorRecursive(RtfTreeNode parentNode, RtfColorTable colorDestTbl, RtfColorTable colorToCopyTbl)
    {
        if (parentNode != null && parentNode.hasChildNodes())
        {
            for (int iNdIndex = 0; iNdIndex < parentNode.getChildNodes().size(); iNdIndex++)
            {
                if (parentNode.getChildNodes().get(iNdIndex).getNodeType() == RtfNodeType.KEYWORD &&
                    (parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("cf") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("cb") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("pncf") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("brdrcf") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("cfpat") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("cbpat") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("clcfpatraw") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("clcbpatraw") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("ulc") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("chcfpat") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("chcbpat") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("highlight") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("clcbpat") ||
                     parentNode.getChildNodes().get(iNdIndex).getNodeKey().equals("clcfpat")) &&
                     parentNode.getChildNodes().get(iNdIndex).getHasParameter() == true)
                {
                    parentNode.getChildNodes().get(iNdIndex).setParameter(
                    		getColorID(colorDestTbl, 
                    				   colorToCopyTbl.get(parentNode.getChildNodes().get(iNdIndex).getParameter())));
                }

                adjustColorRecursive(parentNode.getChildNodes().get(iNdIndex), colorDestTbl, colorToCopyTbl);
            }
        }
    }

    /**
     * Inserta el nuevo �rbol en el �rbol base (como un nuevo grupo) eliminando toda la cabecera del documento insertado.
     * @param parentNode Grupo base en el que se insertar� el nuevo arbol.
     * @param treeToCopyParent Nuevo �rbol a insertar.
     * @param intCurrIndex �ndice en el que se insertar� el nuevo �rbol dentro del grupo base.
     */
    private void execMergeDoc(RtfTreeNode parentNode, RtfTree treeToCopyParent, int intCurrIndex)
    {
        //Se busca el primer "\pard" del documento (comienzo del texto)
        RtfTreeNode nodePard = treeToCopyParent.getRootNode().firstChild().selectSingleChildNode("pard");

        //Se obtiene el �ndice del nodo dentro del principal
        int indPard = treeToCopyParent.getRootNode().firstChild().getChildNodes().indexOf(nodePard);

        //Se crea el nuevo grupo
        RtfTreeNode newGroup = new RtfTreeNode(RtfNodeType.GROUP);

        //Se resetean las opciones de p�rrafo y fuente
        newGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pard", false, 0));
        newGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "plain", false, 0));

        //Se inserta cada nodo hijo del documento nuevo en el documento base
        for (int i = indPard + 1; i < treeToCopyParent.getRootNode().firstChild().getChildNodes().size(); i++)
        {
            RtfTreeNode newNode = 
                treeToCopyParent.getRootNode().firstChild().getChildNodes().get(i).cloneNode(true);

            newGroup.appendChild(newNode);
        }

        //Se inserta el nuevo grupo con el nuevo documento
        parentNode.insertChild(intCurrIndex, newGroup);
    }

    /**
     * Elimina los elementos no deseados del documento a insertar, por ejemplo los nodos "\par" finales.
     * @param docToInsert Documento a insertar.
     */
    private void cleanToInsertDoc(RtfTree docToInsert)
    {
        //Borra el �ltimo "\par" del documento si existe
        RtfTreeNode lastNode = docToInsert.getRootNode().firstChild().lastChild();

        if (removeLastPar)
        {
            if (lastNode.getNodeType() == RtfNodeType.KEYWORD && lastNode.getNodeKey().equals("par"))
            {
                docToInsert.getRootNode().firstChild().removeChild(lastNode);
            }
        }
    }
}
