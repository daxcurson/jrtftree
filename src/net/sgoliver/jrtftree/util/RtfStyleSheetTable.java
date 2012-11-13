package net.sgoliver.jrtftree.util;

import java.util.*;

public class RtfStyleSheetTable 
{
	private HashMap<Integer, RtfStyleSheet> stylesheets = null;
	
	/**
	 * Constructor de la tabla de estilos.
	 */
	public RtfStyleSheetTable()
    {
        stylesheets = new HashMap<Integer, RtfStyleSheet>();
    }
	
	/**
	 * Añade un nuevo estilo a la tabla de estilos. El estilo se añadirá con un nuevo índice no existente en la tabla.
	 * @param ss Nuevo estilo a añadir a la tabla.
	 */
	public void addStyleSheet(RtfStyleSheet ss)
    {
        ss.setIndex(newStyleSheetIndex());

        stylesheets.put(ss.getIndex(), ss);
    }
	
	/**
	 * Añade un nuevo estilo a la tabla de estilos. El estilo se añadirá con el índice de estilo pasado como parámetro.
	 * @param index Indice del estilo a añadir a la tabla.
	 * @param ss Nuevo estilo a añadir a la tabla.
	 */
	public void addStyleSheet(int index, RtfStyleSheet ss)
    {
        ss.setIndex(index);

        stylesheets.put(index, ss);
    }
	
	/**
	 * Elimina un estilo de la tabla de estilos por índice.
	 * @param index Indice de la hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(int index)
    {
        stylesheets.remove(index);
    }
	
	/**
	 * Elimina de la tabla de estilos el estilo pasado como parámetro.
	 * @param ss Hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(RtfStyleSheet ss)
    {
        stylesheets.remove(ss.getIndex());
    }
	
	/**
	 * Recupera un estilo de la tabla de estilos por índice.
	 * @param index Indice del estilo a recuperar.
	 * @return Estilo cuyo índice es el pasado como parámetro.
	 */
	public RtfStyleSheet get(int index)
    {
        return stylesheets.get(index);
    }
	
	/**
	 * Número de estilos contenidos en la tabla de estilos.
	 * @return Número de estilos contenidos en la tabla de estilos.
	 */
	public int size()
	{
		return stylesheets.size();
	}
	
	/**
	 * Índice del estilo cuyo nombre es el pasado como parámetro.
	 * @param name Nombre del estilo buscado.
	 * @return Estilo cuyo nombre es el pasado como parámetro.
	 */
	public int indexOf(String name)
    {
        int intIndex = -1;
        Object[] fntIndex = stylesheets.keySet().toArray();

        for(int i=0; i<fntIndex.length; i++)
        {
        	if (stylesheets.get(fntIndex[i]).equals(name))
            {
                intIndex = (Integer)fntIndex[i];
                break;
            }
        }

        return intIndex;
    }
	
	/**
	 * Calcula un nuevo índice para insertar un estilo en la tabla.
	 * @return Índice del próximo estilo a insertar.
	 */
	private int newStyleSheetIndex()
    {
        int intIndex = -1;
        Object[] fntIndex = stylesheets.keySet().toArray();

        for(int i=0; i<fntIndex.length; i++)
        {
        	if ((Integer)fntIndex[i] > intIndex)
                intIndex = (Integer)fntIndex[i];
        }

        return (intIndex + 1);
    }
}
