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
 * Description:	Representa un documento RTF en forma de �rbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.core;

import java.util.ArrayList;

/**
 * Colecci�n de nodos de un documento RTF. 
 */
public class RtfNodeCollection //In Sync
{
	private ArrayList<RtfTreeNode> collection;
	
	/**
	 * Constructor de la colecci�n. 
	 */
	public RtfNodeCollection()
	{
		collection = new ArrayList<RtfTreeNode>();
	}
	
	/**
	 * A�ade un nuevo nodo a la colecci�n actual.
	 * @param node Nuevo nodo a a�adir.
	 * @return Posici�n en la que se ha insertado el nuevo nodo.
	 */
    public int add(RtfTreeNode node)
    {
        collection.add(node);

        return (collection.size() - 1);
    }
    
    /**
     * Inserta un nuveo nodo en una posici�n determinada de la colecci�n.
     * @param index Posici�n en la que insertar el nodo.
     * @param node Nuevo nodo a insertar.
     */
    public void insert(int index, RtfTreeNode node)
    {
    	collection.add(index, node);
    }
    
    /**
     * Obtiene un elemento de la colecci�n.
     * @param index Indice del nodo a obtener.
     * @return Devuelve el nodo que ocupa la posici�n 'index' dentro de la colecci�n.
     */
    public RtfTreeNode get(int index)
    {
    	return collection.get(index);
    }
    
    /**
     * Asigna un elemento de la colecci�n.
     * @param index Indice del nodo a asignar.
     * @param node Nodo a asignar.
     */
    public void set(int index, RtfTreeNode node)
    {
    	collection.set(index,node);
    }
    
    /**
     * Devuelve el �ndice del nodo pasado como par�metro dentro de la lista de nodos de la colecci�n.
     * @param node Nodo a buscar en la colecci�n.
     * @return Indice del nodo buscado. Devolver� el valor -1 en caso de no encontrarse el nodo dentro de la colecci�n.
     */
    public int indexOf(RtfTreeNode node)
    {
    	return collection.indexOf(node);
    }
    
    /**
     * Devuelve el �ndice del nodo pasado como par�metro dentro de la lista de nodos de la colecci�n.
     * @param node Nodo a buscar en la colecci�n.
     * @param startIndex Posici�n dentro de la colecci�n a partir del que se buscar�.
     * @return Indice del nodo buscado. Devolver� el valor -1 en caso de no encontrarse el nodo dentro de la colecci�n.
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
     * Devuelve el �ndice del primer nodo de la colecci�n cuya clave sea la pasada como par�metro.
     * @param key Clave a buscar en la colecci�n.
     * @return Indice del nodo buscado. Devolver� el valor -1 en caso de no encontrarse el nodo dentro de la colecci�n.
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
     * Devuelve el �ndice del primer nodo de la colecci�n cuya clave sea la pasada como par�metro.
     * @param key Clave a buscar en la colecci�n.
     * @param startIndex Posici�n dentro de la colecci�n a partir del que se buscar�.
     * @return Indice del nodo buscado. Devolver� el valor -1 en caso de no encontrarse el nodo dentro de la colecci�n.
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
     * A�ade al final de la colecci�n una nueva lista de nodos.
     * @param col Nueva lista de nodos a a�adir a la colecci�n actual.
     */
    public void addRange(RtfNodeCollection col)
    {
    	collection.addAll(col.collection);
    }
    
    /**
     * Elimina un conjunto de nodos adyacentes de la colecci�n.
     * @param index �ndice del primer nodo del conjunto a eliminar.
     * @param count N�mero de nodos a eliminar.
     */
    public void removeRange(int index, int count)
    {
    	for(int i=0; i<count; i++)
    		collection.remove(index);
    }
    
    /**
     * Elimina el nodo i-esimo de la colecci�n.
     * @param index �ndice del nodo a eliminar.
     */
    public void removeAt(int index)
    {
    	collection.remove(index);
    }
    
    /**
     * Elimina todos los nodos de la colecci�n.
     */
    public void clear()
    {
    	collection.clear();
    }

    /**
     * Obtiene el tama�o de la colecci�n de nodos.
     * @return Devuelve el n�mero de nodos de la colecci�n.
     */
    public int size()
    {
    	return collection.size();
    }
    
    /**
     * Elimina un nodo de la colecci�n a partir de su �ndice.
     * @param index Indice del nodo a eliminar.
     */
    public void remove(int index)
    {
    	collection.remove(index);
    }
}
