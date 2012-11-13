package net.sgoliver.jrtftree.util;

import java.awt.Color;
import java.util.LinkedList;

public class RtfColorTable 
{
	private LinkedList<Color> colors;
	
	/**
	 * Constructor de la clase RtfColorTable.
	 */
	public RtfColorTable()
    {
        colors = new LinkedList<Color>();
    }
	
	/**
	 * Inserta un nuevo color en la tabla de colores.
	 * @param color Nuevo color a insertar.
	 */
	public void addColor(Color color)
    {
        colors.add(color);
    }
	
	/**
	 * Obtiene el color n-ésimo de la tabla de colores.
	 * @param index Indice del color a recuperar.
	 * @return Color n-ésimo de la tabla de colores.
	 */
	public Color get(int index)
	{
		return colors.get(index);
	}
	
	/**
	 * Número de fuentes en la tabla.
	 * @return
	 */
	public int size()
	{
		return colors.size();
	}
	
	/**
	 * Obtiene el índice de un color determinado en la tabla
	 * @param name Color a consultar.
	 * @return Indice del color consultado.
	 */
	public int IndexOf(Color color)
    {
        return colors.indexOf(color);
    }
}
