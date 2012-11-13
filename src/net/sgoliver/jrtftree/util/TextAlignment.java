package net.sgoliver.jrtftree.util;

/**
 * Tipos de hojas de estilo de un documento RTF.
 */
public class TextAlignment
{
	/**
	 * Texto centrado.
	 */
	public static final int LEFT = 0;
	
	/**
	 * Texto justificado.
	 */
	public static final int RIGHT = 1;
	
	/**
	 * Texto alineado a la izquierda.
	 */
	public static final int CENTERED = 2;
	
	/**
	 * Texto justificado a la derecha.
	 */
	public static final int JUSTIFIED = 3;
	
	/**
	 * Convierte a cadena el tipo de alineación de texto.
	 * @param ta Tipo de alineación de texto.
	 * @return Representación textual del tipo de alineación de texto.
	 */
	public static String toString(int ta)
	{
		String res = "";
		
		switch(ta)
		{
			case CENTERED:
				res = "CENTERED";
				break;	
			case JUSTIFIED:
				res =  "JUSTIFIED";
				break;
			case LEFT:
				res = "LEFT";
				break;
			case RIGHT:
				res = "RIGHT";
				break;
		}
		
		return res;
	}
	
	
}
