/**
 * Gerencia Tecnica - VIPSA 2004.
 * (c) Todos los derechos reservados 2008.
 * Creado: 30/12/2008
 */
package org.tsir.common.utils;

import java.io.File;

/**
 * @author gt-erosas
 */
public class OSPath {

	private static String dir = System.getProperty("user.dir") + "/";
	private static String os = System.getProperty("os.name");
	private static String tmpdir = System.getProperty("java.io.tmpdir") + "/";

	public static String getPath(String key) {
		String path = dir + key;
		return path;
	}

	public static File getFile(String key) {
		return new File(getPath(key));
	}

	public static File getFile(String dir, String fileName) {
		String path = getPath(dir);
		createDirectory(new File(path));
		return new File(getPath(dir) + fileName);
	}

	public static void createDirectory(File directory) {
		if (!directory.exists())
			directory.mkdirs();
	}

	public static String getOSName() {
		return os;
	}

	public static File getTmpFile() {
		return new File(tmpdir + "lane.tmp");
	}

	public static boolean isWindowsOS() {
		return File.separatorChar == '\\';
	}

}
