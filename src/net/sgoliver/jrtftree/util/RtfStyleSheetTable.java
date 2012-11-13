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
	 * A�ade un nuevo estilo a la tabla de estilos. El estilo se a�adir� con un nuevo �ndice no existente en la tabla.
	 * @param ss Nuevo estilo a a�adir a la tabla.
	 */
	public void addStyleSheet(RtfStyleSheet ss)
    {
        ss.setIndex(newStyleSheetIndex());

        stylesheets.put(ss.getIndex(), ss);
    }
	
	/**
	 * A�ade un nuevo estilo a la tabla de estilos. El estilo se a�adir� con el �ndice de estilo pasado como par�metro.
	 * @param index Indice del estilo a a�adir a la tabla.
	 * @param ss Nuevo estilo a a�adir a la tabla.
	 */
	public void addStyleSheet(int index, RtfStyleSheet ss)
    {
        ss.setIndex(index);

        stylesheets.put(index, ss);
    }
	
	/**
	 * Elimina un estilo de la tabla de estilos por �ndice.
	 * @param index Indice de la hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(int index)
    {
        stylesheets.remove(index);
    }
	
	/**
	 * Elimina de la tabla de estilos el estilo pasado como par�metro.
	 * @param ss Hoja de estilos a eliminar.
	 */
	public void removeStyleSheet(RtfStyleSheet ss)
    {
        stylesheets.remove(ss.getIndex());
    }
	
	/**
	 * Recupera un estilo de la tabla de estilos por �ndice.
	 * @param index Indice del estilo a recuperar.
	 * @return Estilo cuyo �ndice es el pasado como par�metro.
	 */
	public RtfStyleSheet get(int index)
    {
        return stylesheets.get(index);
    }
	
	/**
	 * N�mero de estilos contenidos en la tabla de estilos.
	 * @return N�mero de estilos contenidos en la tabla de estilos.
	 */
	public int size()
	{
		return stylesheets.size();
	}
	
	/**
	 * �ndice del estilo cuyo nombre es el pasado como par�metro.
	 * @param name Nombre del estilo buscado.
	 * @return Estilo cuyo nombre es el pasado como par�metro.
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
	 * Calcula un nuevo �ndice para insertar un estilo en la tabla.
	 * @return �ndice del pr�ximo estilo a insertar.
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
