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
 * Class:		RtfTreeNode
 * Copyright:   2005 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * SF Project:	http://nrtftree.sourceforge.net
 *				http://sourceforge.net/projects/nrtftree
 * Date:		15/01/2006
 * Description:	Representa un documento RTF en forma de �rbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Nodo RTF de la representaci�n en �rbol de un documento. 
 */
public class RtfTreeNode
{
	//Atributos
	private int nodeType;
	private String key;
	private boolean hasParam;
	private int param;
	private RtfNodeCollection children;
	private RtfTreeNode parent;
	private RtfTreeNode root;
	private RtfTree tree;
	
	//Constructores
	
	/**
	 * Constructor por defecto de la clase.
	 */
    public RtfTreeNode()
    {
        this.children = null;

        this.nodeType = RtfNodeType.NONE;
        this.key = "";
        this.hasParam = false;
        this.param = 0;

        this.parent = null;
        this.root = null;
        this.tree = null;
    }

    /**
     * Constructor de la clase. Crea un nodo vac�o del tipo pasado como par�metro.
     * @param nodeType Tipo de nodo a crear.
     */
    public RtfTreeNode(int nodeType)
    {
        this.nodeType = nodeType;
        this.key = "";
        this.hasParam = false;
        this.param = 0;

        this.parent = null;
        this.tree = null;
        
        if(nodeType == RtfNodeType.GROUP || nodeType == RtfNodeType.ROOT)
        	this.children = new RtfNodeCollection();
        else
        	this.children = null;
        
        if (nodeType == RtfNodeType.ROOT)
            this.root = this;
        else
        	this.root = null;
    }

    /**
     * Constructor de la clase. Crea un nodo completo con los datos suministrados como par�ametros.
     * @param nodeType Tipo de nodo.
     * @param key Clave del nodo.
     * @param hasParam Indicativo de existencia de par�metro.
     * @param param Par�metro del nodo, en caso de existir.
     */
    public RtfTreeNode(int nodeType, String key, boolean hasParam, int param)
    {
        this.nodeType = nodeType;
        this.key = key;
        this.hasParam = hasParam;
        this.param = param;
        
        this.parent = null;
        this.tree = null;
        
        if(nodeType == RtfNodeType.GROUP || nodeType == RtfNodeType.ROOT)
        	this.children = new RtfNodeCollection();
        else
        	this.children = null;

        if (nodeType == RtfNodeType.ROOT)
            this.root = this;
        else
        	this.root = null;
    }
    
    /**
     * Constructor privado de la clase. Crea un nodo a partir de un token del analizador l�xico.
     * @param token Token RTF devuelto por el analizador l�xico.
     */
    protected RtfTreeNode(RtfToken token)
    {
        this.nodeType = token.getTokenType();
        this.key = token.getKey();
        this.hasParam = token.getHasParam();
        this.param = token.getParam();

        this.children = null;
        this.parent = null;
        this.root = null;
        this.tree = null;
    }
    
    //M�todos P�blicos
    
    /**
     * A�ade un nodo al final de la lista de hijos.
     * @param newNode Nuevo nodo a a�adir.
     */
    public void appendChild(RtfTreeNode newNode)
    {
    	if(newNode != null)
		{
    		//Si a�n no ten�a hijos se inicializa la colecci�n
            if (children == null)
                children = new RtfNodeCollection();
    		
	        //Se asigna como nodo padre el nodo actual
	        newNode.parent = this;
	
	        //Se actualizan las propiedades Root y Tree del nuevo nodo y sus posibles hijos
            updateNodeRoot(newNode);
	
	        //Se a�ade el nuevo nodo al final de la lista de nodos hijo
	        children.add(newNode);
		}
    }
    
    /**
     * Inserta un nuevo nodo en una posici�n determinada de la lista de hijos.
     * @param index Posici�n en la que se insertar� el nodo.
     * @param newNode Nuevo nodo a insertar.
     */
    public void insertChild(int index, RtfTreeNode newNode)
    {
        if (newNode != null)
        {
            //Si a�n no ten�a hijos se inicializa la colecci�n
            if (children == null)
                children = new RtfNodeCollection();

            if (index >= 0 && index <= children.size())
            {
                //Se asigna como nodo padre el nodo actual
                newNode.parent = this;

                //Se actualizan las propiedades Root y Tree del nuevo nodo y sus posibles hijos
                updateNodeRoot(newNode);

                //Se a�ade el nuevo nodo al final de la lista de nodos hijo
                children.insert(index, newNode);
            }
        }
    }

    /**
     * Elimina un nodo de la lista de hijos.
     * @param index Indice del nodo a eliminar.
     */
    public void removeChild(int index)
    {
    	//Si el nodo actual tiene hijos
        if (children != null)
        {
        	if (index >= 0 && index < children.size())
            {
        		//Se elimina el i-�simo hijo
        		children.remove(index);
            }
        }
    }

    /**
     * Elimina un nodo de la lista de hijos.
     * @param node Nodo a eliminar.
     */
    public void removeChild(RtfTreeNode node)
    {
    	//Si el nodo actual tiene hijos
        if (children != null)
        {
	        //Se busca el nodo a eliminar
	        int index = children.indexOf(node);
	
	        //Si lo encontramos
            if (index != -1)
            {
		        //Se elimina el i-�simo hijo
		        children.remove(index);
            }
        }
    }

    /**
     * Realiza una copia exacta del nodo actual.
     * @param cloneChildren Si este par�metro recibe el valor true se clonar�n tambi�n todos los nodos hijo del nodo actual.
     * @return Devuelve una copia exacta del nodo actual.
     */
    public RtfTreeNode cloneNode(boolean cloneChildren)
    {
        RtfTreeNode clon = new RtfTreeNode();

        clon.key = this.key;
        clon.hasParam = this.hasParam;
        clon.param = this.param;
        clon.parent = this.parent;
        clon.root = this.root;
        clon.nodeType = this.nodeType;
        clon.tree = this.tree;

        //Si cloneChildren=false se copia directamente la lista de hijos
        if(!cloneChildren)
        {
            clon.children = this.children;
        }
        else  //En caso contrario se clonan tambi�n cada uno de los hijos, propagando el par�metro cloneChildren=true
        {
            clon.children = null;

            if (this.children != null)
            {
                clon.children = new RtfNodeCollection();
                
	            for(int i=0; i<children.size(); i++)
	            {
	            	clon.children.add(children.get(i).cloneNode(true));
	            }
            }
        }

        return clon;
    }

    /**
     * Indica si el nodo actual tiene nodos hijos.
     * @return Devuelve true si el nodo actual tiene alg�n nodo hijo.
     */
    public boolean hasChildNodes()
    {
    	boolean res = false;
    	
    	if (children != null && children.size() > 0)
            res = true;

        return res;
    }

    /**
     * Devuelve el primer nodo de la lista de nodos hijos del nodo actual cuya palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Primer nodo de la lista de nodos hijos del nodo actual cuya palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleChildNode(String keyword)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
	        while (i < children.size() && !found)
	        {
	            if (children.get(i).key.equals(keyword))
	            {
	                node = children.get(i);
	                found = true;
	            }
	
	            i++;
	        }
        }

        return node;
    }

    /**
     * Devuelve el primer nodo de la lista de nodos hijos del nodo actual cuyo tipo es el indicado como par�metro.
     * @param nodeType Tipo de nodo buscado.
     * @return Primer nodo de la lista de nodos hijos del nodo actual cuyo tipo es el indicado como par�metro.
     */
    public RtfTreeNode selectSingleChildNode(int nodeType)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
	        while (i < children.size() && !found)
	        {
	            if (children.get(i).nodeType == nodeType)
	            {
	                node = children.get(i);
	                found = true;
	            }
	
	            i++;
	        }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo de la lista de nodos hijos del nodo actual cuya palabra clave y par�metro son los indicados como par�metros.
     * @param keyword Palabra clave buscada.
     * @param param Par�metro buscado.
     * @return Primer nodo de la lista de nodos hijos del nodo actual cuya palabra clave y par�metro son los indicados como par�metros.
     */
    public RtfTreeNode selectSingleChildNode(String keyword, int param)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).key.equals(keyword) && children.get(i).param == param)
                {
                    node = children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Primer nodo grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleChildGroup(String keyword)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).getNodeType() == RtfNodeType.GROUP &&
                    children.get(i).hasChildNodes() &&
                    children.get(i).firstChild().getNodeKey().equals(keyword))
                {
                    node = children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @param ignoreSpecial Si est� activo se ignorar�n los nodos de control '\*' previos a algunas palabras clave.
     * @return Primer nodo grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleChildGroup(String keyword, boolean ignoreSpecial)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).getNodeType() == RtfNodeType.GROUP && children.get(i).hasChildNodes() &&
                    (
                     (children.get(i).firstChild().getNodeKey().equals(keyword)) ||
                     (ignoreSpecial && children.get(i).getChildNodes().get(0).getNodeKey().equals("*") && 
                    		 children.get(i).getChildNodes().get(1).getNodeKey().equals(keyword)))
                    )
                {
                    node = children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
 
    /**
     * Devuelve el primer nodo del �rbol, a partir del nodo actual, cuyo tipo es el indicado como par�metro.
     * @param nodeType Tipo del nodo buscado.
     * @return Primer nodo del �rbol, a partir del nodo actual, cuyo tipo es el indicado como par�metro.
     */
    public RtfTreeNode selectSingleNode(int nodeType)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
	        while (i < children.size() && !found)
	        {
	            if (children.get(i).nodeType == nodeType)
	            {
	                node = children.get(i);
	                found = true;
	            }
	            else
	            {
	                node = children.get(i).selectSingleNode(nodeType);
	
	                if (node != null)
	                {
	                    found = true;
	                }
	            }
	
	            i++;
	        }
        }

        return node;
    }

    /**
     * Devuelve el primer nodo del �rbol, a partir del nodo actual, cuya palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Primer nodo del �rbol, a partir del nodo actual, cuya palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleNode(String keyword)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
	        while (i < children.size() && !found)
	        {
	            if (children.get(i).key.equals(keyword))
	            {
	                node = children.get(i);
	                found = true;
	            }
	            else
	            {
	                node = children.get(i).selectSingleNode(keyword);
	
	                if (node != null)
	                {
	                    found = true;
	                }
	            }
	
	            i++;
	        }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo grupo del �rbol, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Primer nodo grupo del �rbol, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleGroup(String keyword)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).getNodeType() == RtfNodeType.GROUP &&
                    children.get(i).hasChildNodes() &&
                    children.get(i).firstChild().getNodeKey().equals(keyword))
                {
                    node = children.get(i);
                    found = true;
                }
                else
                {
                    node = children.get(i).selectSingleGroup(keyword);

                    if (node != null)
                    {
                        found = true;
                    }
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo grupo del �rbol, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @param ignoreSpecial Si est� activo se ignorar�n los nodos de control '\*' previos a algunas palabras clave.
     * @return Primer nodo grupo del �rbol, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSingleGroup(String keyword, boolean ignoreSpecial)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).getNodeType() == RtfNodeType.GROUP && children.get(i).hasChildNodes() &&
                    (
                     (children.get(i).firstChild().getNodeKey().equals(keyword)) ||
                     (ignoreSpecial && children.get(i).getChildNodes().get(0).getNodeKey().equals("*") && 
                    		 children.get(i).getChildNodes().get(1).getNodeKey().equals(keyword)))
                    )
                {
                    node = children.get(i);
                    found = true;
                }
                else
                {
                    node = children.get(i).selectSingleGroup(keyword, ignoreSpecial);

                    if (node != null)
                    {
                        found = true;
                    }
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el primer nodo del �rbol, a partir del nodo actual, cuya palabra clave y par�metro son los indicados como par�metro.
     * @param keyword Palabra clave buscada.
     * @param param Par�metro buscado.
     * @return Primer nodo del �rbol, a partir del nodo actual, cuya palabra clave y par�metro son ls indicados como par�metro.
     */
    public RtfTreeNode selectSingleNode(String keyword, int param)
    {
        int i = 0;
        boolean found = false;
        RtfTreeNode node = null;

        if (children != null)
        {
            while (i < children.size() && !found)
            {
                if (children.get(i).key.equals(keyword) && children.get(i).param == param)
                {
                    node = children.get(i);
                    found = true;
                }
                else
                {
                    node = children.get(i).selectSingleNode(keyword, param);

                    if (node != null)
                    {
                        found = true;
                    }
                }

                i++;
            }
        }

        return node;
    }

    /**
     * Devuelve todos los nodos, a partir del nodo actual, cuya palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Colecci�n de nodos, a partir del nodo actual, cuya palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectNodes(String keyword)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
	        for(int i=0; i<children.size(); i++)
	        {
	            if (children.get(i).key.equals(keyword))
	            {
	                nodes.add(children.get(i));
	            }
	
	            nodes.addRange(children.get(i).selectNodes(keyword));
	        }
        }

        return nodes;
    }

    /**
     * Devuelve todos los nodos, a partir del nodo actual, cuyo tipo es el indicado como par�metro.
     * @param nodeType Tipo del nodo buscado.
     * @return Colecci�n de nodos, a partir del nodo actual, cuyo tipo es la indicado como par�metro.
     */
    public RtfNodeCollection selectNodes(int nodeType)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
	        for(int i=0; i<children.size(); i++)
	        {
	            if (children.get(i).nodeType == nodeType)
	            {
	                nodes.add(children.get(i));
	            }
	
	            nodes.addRange(children.get(i).selectNodes(nodeType));
	        }
        }

        return nodes;
    }
    
    /**
     * Devuelve todos los nodos grupo, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Colecci�n de nodos grupo, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectGroups(String keyword)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
	        {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.GROUP &&
                    node.hasChildNodes() &&
                    node.firstChild().getNodeKey().equals(keyword))
                {
                    nodes.add(node);
                }

                nodes.addRange(node.selectGroups(keyword));
            }
        }

        return nodes;
    }
    
    /**
     * Devuelve todos los nodos grupo, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @param ignoreSpecial Si est� activo se ignorar�n los nodos de control '\*' previos a algunas palabras clave.
     * @return Colecci�n de nodos grupo, a partir del nodo actual, cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectGroups(String keyword, boolean ignoreSpecial)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
	        {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.GROUP && node.hasChildNodes() &&
                    (
                     (node.firstChild().getNodeKey().equals(keyword)) ||
                     (ignoreSpecial && node.getChildNodes().get(0).getNodeKey().equals("*") && node.getChildNodes().get(1).getNodeKey().equals(keyword)))
                    )
                {
                    nodes.add(node);
                }

                nodes.addRange(node.selectGroups(keyword, ignoreSpecial));
            }
        }

        return nodes;
    }
    
    /**
     * Devuelve todos los nodos, a partir del nodo actual, cuya palabra clave y par�metro son los indicados como par�metro.
     * @param keyword Palabra clave buscada.
     * @param param Par�metro buscado.
     * @return Colecci�n de nodos, a partir del nodo actual, cuya palabra clave y par�metro son los indicados como par�metro.
     */
    public RtfNodeCollection selectNodes(String keyword, int param)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.key.equals(keyword) && node.param == param)
                {
                    nodes.add(node);
                }

                nodes.addRange(node.selectNodes(keyword, param));
            }
        }

        return nodes;
    }

    /**
     * Devuelve todos los nodos de la lista de nodos hijos del nodo actual cuya palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Colecci�n de nodos de la lista de nodos hijos del nodo actual cuya palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectChildNodes(String keyword)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
	        for(int i=0; i<children.size(); i++)
	        {
	            if (children.get(i).key.equals(keyword))
	            {
	                nodes.add(children.get(i));
	            }
	        }
        }

        return nodes;
    }
    
    /**
     * Devuelve todos los nodos grupos de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Colecci�n de nodos grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectChildGroups(String keyword)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.GROUP &&
                    node.hasChildNodes() &&
                    node.firstChild().getNodeKey().equals(keyword))
                {
                    nodes.add(node);
                }
            }
        }

        return nodes;
    }
    
    /**
     * Devuelve todos los nodos grupos de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @param ignoreSpecial Si est� activo se ignorar�n los nodos de control '\*' previos a algunas palabras clave.
     * @return Colecci�n de nodos grupo de la lista de nodos hijos del nodo actual cuya primera palabra clave es la indicada como par�metro.
     */
    public RtfNodeCollection selectChildGroups(String keyword, boolean ignoreSpecial)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.GROUP && node.hasChildNodes() && 
                    (
                     (node.firstChild().getNodeKey().equals(keyword)) ||
                     (ignoreSpecial && node.getChildNodes().get(0).getNodeKey().equals("*") && node.getChildNodes().get(1).getNodeKey().equals(keyword)))
                    )
                {
                    nodes.add(node);
                }
            }
        }

        return nodes;
    }

    /**
     * Devuelve todos los nodos de la lista de nodos hijos del nodo actual cuyo tipo es el indicado como par�metro.
     * @param nodeType Tipo del nodo buscado.
     * @return Colecci�n de nodos de la lista de nodos hijos del nodo actual cuyo tipo es el indicado como par�metro.
     */
    public RtfNodeCollection selectChildNodes(int nodeType)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
	        for(int i=0; i<children.size(); i++)
	        {
	            if (children.get(i).nodeType == nodeType)
	            {
	                nodes.add(children.get(i));
	            }
	        }
        }
        return nodes;
    }
    
    /**
     * Devuelve todos los nodos de la lista de nodos hijos del nodo actual cuya palabra clave y par�metro son los indicados como par�metro.
     * @param keyword Palabra clave buscada.
     * @param param Par�metro buscado.
     * @return Colecci�n de nodos de la lista de nodos hijos del nodo actual cuya palabra clave y par�metro son los indicados como par�metro.
     */
    public RtfNodeCollection selectChildNodes(String keyword, int param)
    {
        RtfNodeCollection nodes = new RtfNodeCollection();

        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.key.equals(keyword) && node.param == param)
                {
                    nodes.add(node);
                }
            }
        }

        return nodes;
    }
    
    /**
     * Devuelve el siguiente nodo hermano del actual cuya palabra clave es la indicada como par�metro.
     * @param keyword Palabra clave buscada.
     * @return Primer nodo hermano del actual cuya palabra clave es la indicada como par�metro.
     */
    public RtfTreeNode selectSibling(String keyword)
    {
        RtfTreeNode node = null;
        RtfTreeNode par = this.parent;

        if (par != null)
        {
            int curInd = par.children.indexOf(this);

            int i = curInd + 1;
            boolean found = false;

            while (i < par.children.size() && !found)
            {
                if (par.children.get(i).key.equals(keyword))
                {
                    node = par.children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el siguiente nodo hermano del actual cuyo tipo es el indicado como par�metro.
     * @param nodeType Tipo de nodo buscado.
     * @return Primer nodo hermano del actual cuyo tipo es el indicado como par�metro.
     */
    public RtfTreeNode selectSibling(int nodeType)
    {
        RtfTreeNode node = null;
        RtfTreeNode par = this.parent;

        if (par != null)
        {
            int curInd = par.children.indexOf(this);

            int i = curInd + 1;
            boolean found = false;

            while (i < par.children.size() && !found)
            {
                if (par.children.get(i).nodeType == nodeType)
                {
                    node = par.children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Devuelve el siguiente nodo hermano del actual cuya palabra clave y par�metro son los indicados como par�metro.
     * @param keyword Palabra clave buscada.
     * @param param Par�metro buscado.
     * @return Primer nodo hermano del actual cuya palabra clave y par�metro son los indicados como par�metro.
     */
    public RtfTreeNode selectSibling(String keyword, int param)
    {
        RtfTreeNode node = null;
        RtfTreeNode par = this.parent;

        if (par != null)
        {
            int curInd = par.children.indexOf(this);

            int i = curInd + 1;
            boolean found = false;

            while (i < par.children.size() && !found)
            {
                if (par.children.get(i).key.equals(keyword) && par.children.get(i).param == param)
                {
                    node = par.children.get(i);
                    found = true;
                }

                i++;
            }
        }

        return node;
    }
    
    /**
     * Busca todos los nodos de tipo Texto que contengan el texto buscado.
     * @param text Texto buscado en el documento.
     * @return Lista de nodos, a partir del actual, que contienen el texto buscado.
     */
    public RtfNodeCollection findText(String text)
    {
        RtfNodeCollection list = new RtfNodeCollection();

        //Si el nodo actual tiene hijos
        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.TEXT && node.getNodeKey().indexOf(text) != -1)
                    list.add(node);
                else if(node.getNodeType() == RtfNodeType.GROUP)
                    list.addRange(node.findText(text));
            }
        }

        return list;
    }
    
    /**
     * Busca y reemplaza un texto determinado en todos los nodos de tipo Texto a partir del actual.
     * @param oldValue Texto buscado.
     * @param newValue Texto de reemplazo.
     */
    public void replaceText(String oldValue, String newValue)
    {
        //Si el nodo actual tiene hijos
        if (children != null)
        {
        	for(int i=0; i<children.size(); i++)
            {
        		RtfTreeNode node = children.get(i);
        		
                if (node.getNodeType() == RtfNodeType.TEXT)
                    node.setNodeKey(node.getNodeKey().replace(oldValue, newValue));
                else if (node.getNodeType() == RtfNodeType.GROUP)
                    node.replaceText(oldValue, newValue);
            }
        }
    }
    
    /**
     * Devuelve una representaci�n del nodo donde se indica su tipo, clave, indicador de par�metro y valor de par�metro
     * @return Cadena de caracteres del tipo [TIPO, CLAVE, IND_PARAMETRO, VAL_PARAMETRO]
     */
    public String toString()
	{
    	return "[" + RtfNodeType.toString(this.nodeType) + ", " + this.key + ", " + this.hasParam + ", " + this.param + "]";
	}
    
    //----------------------------------------------------------------
    
    //M�todos Privados
    
    /**
     * Devuelve el c�digo RTF del nodo actual y todos sus nodos hijos.
     * @return C�digo RTF del nodo actual y todos sus nodos hijos.
     */
    public String getRtf()
    {
        String res = "";
        
        Charset enc = this.tree.getEncoding();

        res = getRtfInm(this, null, enc);

        return res;
    }
    
    /**
     * M�todo auxiliar para obtener el Texto RTF del nodo actual a partir de su representaci�n en �rbol.
     * @param curNode Nodo actual del �rbol.
     * @param prevNode Nodo anterior tratado.
     * @param enc Codificaci�n del documento.
     * @return Texto en formato RTF del nodo. 
     */
    private String getRtfInm(RtfTreeNode curNode, RtfTreeNode prevNode, Charset enc)
    {
    	StringBuilder res = new StringBuilder("");

        if (curNode.nodeType == RtfNodeType.ROOT)
            res.append("");
        else if (curNode.nodeType == RtfNodeType.GROUP)
            res.append("{");
        else
        {
            if (curNode.nodeType != RtfNodeType.TEXT)
            {
                res.append("\\");
            }
            else  //curNode.NodeType == RTF_NODE_TYPE.TEXT
            {
                if (prevNode != null && prevNode.nodeType == RtfNodeType.KEYWORD)
                {
                	int code = Character.codePointAt(curNode.getNodeKey(), 0);

                    if (code >= 32 && code < 128)
                        res.append(" ");
                }
            }

            appendEncoded(res, curNode.key, enc);

            if (curNode.hasParam)
            {
                if (curNode.nodeType == RtfNodeType.KEYWORD)
                {
                    res.append(String.valueOf(curNode.param));
                }
                else if (curNode.nodeType == RtfNodeType.CONTROL)
                {
                    //Si es un caracter especial como las vocales acentuadas
                    if (curNode.key.equals("\'"))
                    {
                    	res.append(getHexa(curNode.param));
                    }
                }
            }
        }

        //Se obtienen los nodos hijos
        RtfNodeCollection children = curNode.children;

        //Si el nodo tiene hijos se obtiene el c�digo RTF de los hijos
        if (children != null)
        {
	        for (int i = 0; i < children.size(); i++)
	        {
	            RtfTreeNode node = children.get(i);
	
	            if (i > 0)
	                res.append(getRtfInm(node, children.get(i-1), enc));
	            else
	                res.append(getRtfInm(node, null, enc));
	        }
        }
        
        if (curNode.nodeType == RtfNodeType.GROUP)
        {
            res.append("}");
        }

        return res.toString();
    }
    
    /**
     * Concatena dos cadenas utilizando la codificaci�n del documento.
     * @param res Cadena original.
     * @param s Cadena a a�adir.
     * @param enc Codificaci�n del documento.
     */
    private void appendEncoded(StringBuilder res, String s, Charset enc)
    {
        //Contributed by Jan Stuchl�k

        for (int i = 0; i < s.length(); i++)
        {
            int code = Character.codePointAt(s, i);

            if (code >= 128 || code < 32)
            {
                res.append("\\'");
                ByteBuffer bytes = enc.encode(CharBuffer.wrap(s, i, i+1));
                res.append(getHexa(bytes.get(0) & 0xff));  //Para evitar bytes negativos
            }
            else
            {
                if ((s.charAt(i) == '{') || (s.charAt(i) == '}') || (s.charAt(i) == '\\'))
                {
                    res.append("\\");
                }

                res.append(s.charAt(i));
            }
        }
    }
    
    /**
     * Obtiene el c�digo hexadecimal de un entero.
     * @param code N�mero entero.
     * @return C�digo hexadecimal del entero pasado como par�metro.
     */
    private String getHexa(int code)
    {
        //Contributed by Jan Stuchl�k

        String hexa = Integer.toHexString(code);

        if (hexa.length() == 1)
        {
            hexa = "0" + hexa;
        }

        return hexa;
    }
    
    /**
     * Actualiza las propiedades Root y Tree de un nodo (y sus hijos) con las del nodo actual.
     * @param node Nodo a actualizar.
     */
    private void updateNodeRoot(RtfTreeNode node)
    {
        //Se asigna el nodo ra�z del documento
        node.root = this.root;

        //Se asigna el �rbol propietario del nodo
        node.tree = this.tree;

        //Si el nodo actualizado tiene hijos se actualizan tambi�n
        if (node.children != null)
        {
            //Se actualizan recursivamente los hijos del nodo actual
        	for(int i=0; i< node.children.size(); i++)
            {
                updateNodeRoot(node.children.get(i));
            }
        }
    }
    
    /**
     * Obtiene el nodo ra�z del �rbol RTF.
     * @return Nodo ra�z del �rbol RTF.
     */
    public RtfTreeNode getRootNode()
    {
    	return root;
    }
    
    /**
     * Establece el nodo ra�z del �rbol RTF.
     * @param node Nodo ra�z del �rbol RTF.
     */
    public void setRootNode(RtfTreeNode node)
    {
    	root = node;
    }
    
    /**
     * Obtiene el nodo padre del nodo actual.
     * @return Nodo padre del nodo actual.
     */
    public RtfTreeNode getParentNode()
    {
    	return parent;
    }    
    
    /**
     * Establece el nodo padre del nodo actual.
     * @param node Nodo padre del nodo actual.
     */
    public void setParentNode(RtfTreeNode node)
    {
    	parent = node;
    }
    
    /**
     * Obtiene el tipo de nodo actual.
     * @return Tipo del nodo actual.
     */
    public int getNodeType()
    {
    	return nodeType;
    }
    
    /**
     * Establece el tipo de nodo actual.
     * @param nodeType Tipo de nodo.
     */
    public void setNodeType(int nodeType)
    {
    	this.nodeType = nodeType; 
    }
    
    /**
     * Obtiene la clave del nodo actual.
     * @return Clave del nodo actual.
     */
    public String getNodeKey()
    {
    	return key;
    }
    
    /**
     * Establece la clave del nodo actual.
     * @param key Clave del nodo.
     */
    public void setNodeKey(String key)
    {
    	this.key = key;
    }
    
    /**
     * Obtiene el indicativo de existencia de par�metro asociado al nodo.
     * @return Indicativo de existencia de par�metro asociado al nodo.
     */
    public boolean getHasParameter()
    {
    	return hasParam;
    }
    
    /**
     * Establece el indicativo de existencia de par�metro asociado al nodo.
     * @param hasParam Indicativo de existencia de par�metro asociado al nodo.
     */
    public void setHasParamenter(boolean hasParam)
    {
    	this.hasParam = hasParam;
    }    
    
    /**
     * Obtiene el par�metro asociado al nodo.
     * @return Par�metro asociado al nodo.
     */
    public int getParameter()
    {
    	return param;
    }
    
    /**
     * Establece el par�metro asociado al nodo.
     * @param param Par�metro asociado al nodo.
     */
    public void setParameter(int param)
    {
    	this.param = param; 
    }
    
    /**
     * Obtiene el �rbol RTF padre del nodo.
     * @return �rbol RTF padre del nodo.
     */
    public RtfTree getTree()
    {
    	return tree;
    }
    
    /**
     * Establece el �rbol RTF padre del nodo.
     * @param tree �rbol RTF padre del nodo.
     */
    public void setTree(RtfTree tree)
    {
    	this.tree = tree; 
    }
    
    /**
     * Obtienen la colecci�n de nodos hijo del nodo actual.
     * @return Colecci�n de nodos hijo del nodo actual.
     */
    public RtfNodeCollection getChildNodes()
    {
    	return children;
    }
    
    /**
     * Establece la colecci�n de nodos hijo del nodo actual.
     * @param children Colecci�n de nodos hijo del nodo actual.
     */
    public void setChildNodes(RtfNodeCollection children)
    {
    	this.children = children;
    	
    	for(int i=0; i<children.size(); i++)
        {
    		RtfTreeNode node = children.get(i);
    		
            node.parent = this;

            //Se actualizan las propiedades Root y Tree del nuevo nodo y sus posibles hijos
            updateNodeRoot(node);
        }
    }
    
    /**
     * Obtiene el primer nodo hijo del nodo actual.
     * @return Primer nodo hijo del nodo actual.
     */
    public RtfTreeNode firstChild()
    {
    	RtfTreeNode res = null;
    	
        if (children != null && children.size() > 0)
            res = children.get(0);
        
        return res;
    }
    
    /**
     * Obtiene el �ltimo nodo hijo del nodo actual.
     * @return �ltimo nodo hijo del nodo actual.
     */
    public RtfTreeNode lastChild()
    {
    	RtfTreeNode res = null;
    	
    	if (children != null && children.size() > 0)
            res = children.get(children.size()-1);

    	return res;
    }
    
    /**
     * Obtiene el nodo hermano siguiente del nodo actual (Dos nodos son hermanos si tienen el mismo nodo padre [ParentNode]).
     * @return Siguiente nodo hermano del nodo actual.
     */
    public RtfTreeNode nextSibling()
    {
    	RtfTreeNode res = null;
    	
    	if (parent != null && parent.children != null)
        {
	        int currentIndex = parent.children.indexOf(this);
	
	        if (parent.children.size() > currentIndex + 1)
	            res = parent.children.get(currentIndex + 1);
        }
    	
    	return res;
    }
    
    /**
     * Obtiene el nodo hermano anterior del nodo actual (Dos nodos son hermanos si tienen el mismo nodo padre [ParentNode]).
     * @return Nodo hermano anterior del nodo actual.
     */
    public RtfTreeNode previousSibling()
    {
    	RtfTreeNode res = null;
    	
    	if (parent != null && parent.children != null)
        {
	        int currentIndex = parent.children.indexOf(this);
	
	        if (currentIndex > 0)
	            res = parent.children.get(currentIndex - 1);
        }
    	
    	return res;
    }
    
    /**
     * Devuelve el �ndice del nodo actual dentro de la lista de hijos de su nodo padre.
     * @return �ndice del nodo actual dentro de la lista de hijos de su nodo padre.
     */
    public int getIndex()
    {
    	int res = -1;

        if(parent != null)
            res = parent.children.indexOf(this);

        return res;
    }
    
    /**
     * Devuelve el fragmento de texto del documento contenido en el nodo actual.
     * @return Fragmento de texto del documento contenido en el nodo actual.
     */
    public String getText()
    {
    	return getTextAux(false);
    }
    
    /**
     * Devuelve todo el texto contenido en el nodo actual.
     * @return Texto contenido en el nodo actual.
     */
    public String getRawText()
    {
    	return getTextAux(true);
    }
    
    /**
     * Devuelve el nodo anterior del �rbol.
     * @return Nodo anterior del �rbol.
     */
    public RtfTreeNode previousNode()
    {
    	RtfTreeNode res = null;

        if (this.getNodeType() == RtfNodeType.ROOT)
        {
            res = null;
        }
        else if (parent != null && parent.children != null)
        {
            if (this.getIndex() > 0)
            {
                if (this.previousSibling().getNodeType() == RtfNodeType.GROUP)
                {
                    res = this.previousSibling().lastChild();
                }
                else
                {
                    res = this.previousSibling();
                }
            }
            else
            {
                res = parent;
            }
        }

        return res;
    }
    
    /**
     * Devuelve el nodo siguiente del �rbol.
     * @return Nodo siguiente del �rbol.
     */
    public RtfTreeNode nextNode()
    {
    	RtfTreeNode res = null;

        if (this.getNodeType() == RtfNodeType.ROOT)
        {
            res = this.firstChild();
        }
        else if (parent != null && parent.children != null)
        {
            if (this.getNodeType() == RtfNodeType.GROUP && this.children.size() > 0)
            {
                res = this.firstChild();
            }
            else
            {
                if (this.getIndex() < (parent.children.size() - 1))
                {
                    res = this.nextSibling();
                }
                else
                {
                    res = parent.nextSibling();
                }
            }
        }

        return res;
    }
    
    /**
     * Obtiene el texto contenido en el nodo actual.
     * @param raw Si este par�metro est� activado se extraer� todo el texto contenido en el nodo, independientemente de si �ste forma parte del texto real del documento.
     * @return Texto extraido del nodo.
     */
    private String getTextAux(boolean raw)
    {
        StringBuilder res = new StringBuilder("");

        if (this.getNodeType() == RtfNodeType.GROUP)
        {
            int indkw = this.firstChild().getNodeKey().equals("*") ? 1 : 0;

            if (raw ||
               (!this.getChildNodes().get(indkw).getNodeKey().equals("fonttbl") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("colortbl") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("stylesheet") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("generator") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("info") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("pict") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("object") &&
                !this.getChildNodes().get(indkw).getNodeKey().equals("fldinst")))
            {
                if (getChildNodes() != null)
                {
                	for(int i=0; i<getChildNodes().size(); i++)
                    {
                		RtfTreeNode node = getChildNodes().get(i);
                		
                        res.append(node.getTextAux(raw));
                    }
                }
            }
        }
        else if (this.getNodeType() == RtfNodeType.CONTROL)
        {
            if (this.getNodeKey().equals("'"))
                res.append(decodeControlChar(this.getParameter(), this.tree.getEncoding()));
        }
        else if (this.getNodeType() == RtfNodeType.TEXT)
        {
            res.append(this.getNodeKey());
        }
        else if (this.getNodeType() == RtfNodeType.KEYWORD)
        {
            if (this.getNodeKey().equals("par"))
                res.append("\n");
        }

        return res.toString();
    }
    
    /**
     * Decodifica un caracter especial indicado por su c�digo decimal
     * @param code C�digo del caracter especial (\')
     * @param enc Codificaci�n utilizada para decodificar el caracter especial.
     * @return Caracter especial decodificado.
     */
    private static String decodeControlChar(int code, Charset enc)
    {
        return enc.decode(ByteBuffer.wrap(new byte[] {(byte)code})).toString();                
    }
}
