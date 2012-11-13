package net.sgoliver.jrtftree.util;

/**
 * Tipos de hojas de estilo de un documento RTF.
 */
public class RtfStyleSheetType
{
	/**
	 * Hoja de estilo sin definir.
	 */
	public static final int NONE = 0;
	
	/**
	 * Hoja de estilo de caracter.
	 */
	public static final int CHARACTER = 1;
	
	/**
	 * Hoja de estilo de párrafo.
	 */
	public static final int PARAGRAPH = 2;
	
	/**
	 * Hoja de estilo de sección.
	 */
	public static final int SECTION = 3;
	
	/**
	 * Hoja de estilo de tabla.
	 */
	public static final int TABLE = 4;
	
	/**
	 * Convierte a cadena el tipo de hoja de estilos.
	 * @param ssType Tipo de hoja de estilos.
	 * @return Representación textual del tipo de hoja de estilos.
	 */
	public static String toString(int ssType)
	{
		String res = "";
		
		switch(ssType)
		{
			case NONE:
				res = "NONE";
				break;	
			case CHARACTER:
				res =  "CHARACTER";
				break;
			case PARAGRAPH:
				res = "PARAGRAPH";
				break;
			case SECTION:
				res = "SECTION";
				break;
			case TABLE:
				res = "TABLE";
				break;
		}
		
		return res;
	}
	
	
}
