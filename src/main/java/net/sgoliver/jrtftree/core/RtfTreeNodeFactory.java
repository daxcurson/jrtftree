package net.sgoliver.jrtftree.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Crea ciertos tipos de nodo.
 * 
 * @author daxcurson
 *
 */
public class RtfTreeNodeFactory {
	public static RtfTreeNode createImageNode(String path, int width, int height) throws IOException,FileNotFoundException {
		FileInputStream fStream = null;
		RtfTreeNode imgGroup = new RtfTreeNode(RtfNodeType.GROUP);
		imgGroup.appendChild(new RtfTreeNode(RtfNodeType.KEYWORD, "pict", false, 0));

		try {
			File fInfo = new File(path);
			int numBytes = (int) fInfo.length();

			fStream = new FileInputStream(path);

			byte[] data = new byte[numBytes];

			fStream.read(data);

			StringBuilder hexdata = new StringBuilder();

			for (int i = 0; i < data.length; i++) {
				hexdata.append(getHexa(data[i]));
			}

			BufferedImage img = ImageIO.read(fInfo);

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
		} finally {
			fStream.close();
		}
		return imgGroup;
	}

	/**
	 * Obtiene el código hexadecimal de un entero.
	 * 
	 * @param code Número entero.
	 * @return Código hexadecimal del entero pasado como parámetro.
	 */
	private static String getHexa(int code) {
		String hexa = Integer.toHexString(code);

		if (hexa.length() == 1) {
			hexa = "0" + hexa;
		}

		return hexa;
	}
}
