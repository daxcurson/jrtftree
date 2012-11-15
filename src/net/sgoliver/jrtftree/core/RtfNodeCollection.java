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
 * Class:		RtfNodeCollection
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.util.ArrayList;

/**
 * Colección de nodos de un documento RTF. 
 */
public class RtfNodeCollection //In Sync
{
	private ArrayList<RtfTreeNode> collection;
	
	/**
	 * Constructor de la colección. 
	 */
	public RtfNodeCollection()
	{
		collection = new ArrayList<RtfTreeNode>();
	}
	
	/**
	 * Añade un nuevo nodo a la colección actual.
	 * @param node Nuevo nodo a añadir.
	 * @return Posición en la que se ha insertado el nuevo nodo.
	 */
    public int add(RtfTreeNode node)
    {
        collection.add(node);

        return (collection.size() - 1);
    }
    
    /**
     * Inserta un nuveo nodo en una posición determinada de la colección.
     * @param index Posición en la que insertar el nodo.
     * @param node Nuevo nodo a insertar.
     */
    public void insert(int index, RtfTreeNode node)
    {
    	collection.add(index, node);
    }
    
    /**
     * Obtiene un elemento de la colección.
     * @param index Indice del nodo a obtener.
     * @return Devuelve el nodo que ocupa la posición 'index' dentro de la colección.
     */
    public RtfTreeNode get(int index)
    {
    	return collection.get(index);
    }
    
    /**
     * Asigna un elemento de la colección.
     * @param index Indice del nodo a asignar.
     * @param node Nodo a asignar.
     */
    public void set(int index, RtfTreeNode node)
    {
    	collection.set(index,node);
    }
    
    /**
     * Devuelve el índice del nodo pasado como parámetro dentro de la lista de nodos de la colección.
     * @param node Nodo a buscar en la colección.
     * @return Indice del nodo buscado. Devolverá el valor -1 en caso de no encontrarse el nodo dentro de la colección.
     */
    public int indexOf(RtfTreeNode node)
    {
    	return collection.indexOf(node);
    }
    
    /**
     * Devuelve el índice del nodo pasado como parámetro dentro de la lista de nodos de la colección.
     * @param node Nodo a buscar en la colección.
     * @param startIndex Posición dentro de la colección a partir del que se buscará.
     * @return Indice del nodo buscado. Devolverá el valor -1 en caso de no encontrarse el nodo dentro de la colección.
     */
    public int indexOf(RtfTreeNode node, int startIndex)
    {
        int res = -1;
        
        for(int index = startIndex; index < collection.size(); index++)
        {
        	if(collection.get(index) == node)
        	{
        		res = index;
        		break;
        	}
        }
        
        return res;
    }
    
    /**
     * Devuelve el índice del primer nodo de la colección cuya clave sea la pasada como parámetro.
     * @param key Clave a buscar en la colección.
     * @return Indice del nodo buscado. Devolverá el valor -1 en caso de no encontrarse el nodo dentro de la colección.
     */
    public int indexOf(String key)
    {
        int intFoundAt = -1;

        if (collection.size() > 0)
        {
            for (int intIndex = 0; intIndex < collection.size(); intIndex++)
            {
                if (((RtfTreeNode)collection.get(intIndex)).getNodeKey().equals(key))
                {
                    intFoundAt = intIndex;
                    break;
                }
            }
        }

        return intFoundAt;
    }
    
    /**
     * Devuelve el índice del primer nodo de la colección cuya clave sea la pasada como parámetro.
     * @param key Clave a buscar en la colección.
     * @param startIndex Posición dentro de la colección a partir del que se buscará.
     * @return Indice del nodo buscado. Devolverá el valor -1 en caso de no encontrarse el nodo dentro de la colección.
     */
    public int indexOf(String key, int startIndex)
    {
        int intFoundAt = -1;

        if (collection.size() > 0)
        {
            for (int intIndex = startIndex; intIndex < collection.size(); intIndex++)
            {
                if (((RtfTreeNode)collection.get(intIndex)).getNodeKey().equals(key))
                {
                    intFoundAt = intIndex;
                    break;
                }
            }
        }

        return intFoundAt;
    }
    
    /**
     * Añade al final de la colección una nueva lista de nodos.
     * @param col Nueva lista de nodos a añadir a la colección actual.
     */
    public void addRange(RtfNodeCollection col)
    {
    	collection.addAll(col.collection);
    }
    
    /**
     * Elimina un conjunto de nodos adyacentes de la colección.
     * @param index Índice del primer nodo del conjunto a eliminar.
     * @param count Número de nodos a eliminar.
     */
    public void removeRange(int index, int count)
    {
    	for(int i=0; i<count; i++)
    		collection.remove(index);
    }
    
    /**
     * Elimina el nodo i-esimo de la colección.
     * @param index Índice del nodo a eliminar.
     */
    public void removeAt(int index)
    {
    	collection.remove(index);
    }
    
    /**
     * Elimina todos los nodos de la colección.
     */
    public void clear()
    {
    	collection.clear();
    }

    /**
     * Obtiene el tamaño de la colección de nodos.
     * @return Devuelve el número de nodos de la colección.
     */
    public int size()
    {
    	return collection.size();
    }
    
    /**
     * Elimina un nodo de la colección a partir de su índice.
     * @param index Indice del nodo a eliminar.
     */
    public void remove(int index)
    {
    	collection.remove(index);
    }
}
