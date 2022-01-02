/**
 * 
 */
package org.tsir.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * @author VJT-HROJAS. Clase que se encarga de escribir una foto a un archivo
 */
public class PrintWriterPicture {

	public static final String TRAN = "tran/";

	public static final String CAB = "cab/";

	public static final String PLATE = "plate/";

	private String nameFormat;

	private File directory;

	private String directoryPath;

	private String root;

	private Pos[] pos;

	private String type;

	/**
	 * 
	 */
	public PrintWriterPicture(String type) {
		initialize("", LocalDateTime.now(), null, type);
	}

	/**
	 * @param nameFormat
	 */
	public PrintWriterPicture(String nameFormat, String type) {
		initialize(nameFormat, null, null, type);
	}

	/**
	 * @param nameFormat
	 * @param useDefaultPos
	 */
	public PrintWriterPicture(String nameFormat, boolean useDefaultPos, String type) {
		initialize(nameFormat, null, useDefaultPos ? getDefaultPos() : null, type);
	}

	/**
	 * @param nameFormat
	 * @param pos
	 */
	public PrintWriterPicture(String nameFormat, Pos[] pos, String type) {
		initialize(nameFormat, null, pos, type);
	}

	/**
	 * @param name
	 * @param timeMillis
	 * @param useDefaultPos
	 */
	public PrintWriterPicture(String name, long timeMillis, boolean useDefaultPos, String type) {
		initialize(name, Util.fromMillisDefault(timeMillis), useDefaultPos ? getDefaultPos() : null, type);
	}

	/**
	 * @param name
	 * @param timeMillis
	 * @param pos
	 */
	public PrintWriterPicture(String name, long timeMillis, Pos[] pos, String type) {
		initialize(name, Util.fromMillisDefault(timeMillis), pos, type);
	}

	/**
	 * @param name
	 * @param date
	 * @param useDefaultPos
	 */
	public PrintWriterPicture(String name, LocalDateTime date, boolean useDefaultPos, String type) {
		initialize(name, date, useDefaultPos ? getDefaultPos() : null, type);
	}

	/**
	 * @param name
	 * @param date
	 * @param pos
	 */
	public PrintWriterPicture(String name, LocalDateTime date, Pos[] pos, String type) {
		initialize(name, date, pos, type);
	}

	/**
	 * @param tollCode
	 * @param laneCode
	 * @param date
	 */
	public PrintWriterPicture(String tollCode, String laneCode, LocalDateTime date, String type) {
		initialize(Util.sizeFormat(tollCode, 3, '0').concat(Util.sizeFormat(laneCode, 2, '0')), date, getDefaultPos(),
				type);
	}

	public PrintWriterPicture(String root, String tollCode, String laneCode, LocalDateTime date, String type) {
		initialize(Util.sizeFormat(tollCode, 3, '0').concat(Util.sizeFormat(laneCode, 2, '0')), date, getDefaultPos(),
				type);
		this.root = root;
	}

	/**
	 * @param s
	 * @param date
	 * @param pos
	 */
	private void initialize(String s, LocalDateTime date, Pos[] pos, String type) {
		this.nameFormat = s.concat(date == null ? "" : date.format(Util.NUMB_FULL_MS_FORMAT));
		this.pos = pos;
		this.directory = null;
		this.directoryPath = null;
		this.root = OSPath.isWindowsOS() ? "C:/properties" : "/var/www/properties";
		this.type = type;
	}

	/**
	 * @return
	 */
	private Pos[] getDefaultPos() {
		int[][] p = { { 0, 3 }, { 3, 5 }, { 5, 13 }, { 13, 19 } };
		Pos[] position = new Pos[4];
		for (int i = 0; i < position.length; i++) {
			position[i] = new Pos(p[i][0], p[i][1]);
		}
		return position;
	}

	/**
	 * @param pictureFromVideo . Arreglo de bytes de la imagen
	 * @param number           . N�mero de foto perteneciente a la transacci�n
	 * @description Escribe el arreglo de bytes en un archivo
	 */
	public boolean writeFile(byte[] pictureFromVideo, long number) {
		createDirectory(getDirectory());
		File file = getFile(number);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(pictureFromVideo);
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param pictureFromVideo . Arreglo de bytes de la imagen
	 * @param number           . N�mero de foto perteneciente a la transacci�n
	 * @description Escribe el arreglo de bytes en un archivo
	 */
	public String writeFile(byte[] pictureFromVideo, int number, String sufix) {
		createDirectory(getDirectory());
		File file = getFile(number, sufix);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(pictureFromVideo);
			fos.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param directory
	 * @description Crea el directorio en donde se va a escribir el archivo
	 */
	private void createDirectory(File directory) {
		if (!directory.exists())
			directory.mkdirs();
	}

	/**
	 * @param number par�metro que identifica el n�mero de foto de la transacci�n
	 * @return formatName + number
	 */
	public String getFileName(long number) {
		if (number != 0)
			return nameFormat.concat(String.format("%02d", number));
		return nameFormat;
	}

	/**
	 * @param number par�metro que identifica el n�mero de foto de la transacci�n
	 * @return formatName + number
	 */
	public String getFileName(int number, String sufix) {
		if (number != 0)
			return nameFormat.concat(String.format("%02d", number)).concat(sufix);
		return nameFormat;
	}

	/**
	 * @param fileName
	 * @return nombre del archivo a crear
	 * @throws StringIndexOutOfBoundsException
	 * @description crea la ruta en donde se va a guardar el archivo con ext jpg
	 */
	public File getFile(long number) {
		StringBuilder file = new StringBuilder("");
		file.append(getDirectoryPath());
		file.append(getFileName(number));
		file.append(".jpg");
		return new File(file.toString());
	}

	/**
	 * @param fileName
	 * @return nombre del archivo a crear
	 * @throws StringIndexOutOfBoundsException
	 * @description crea la ruta en donde se va a guardar el archivo con ext jpg
	 */
	public File getFile(int number, String sufix) {
		if (sufix == null) {
			sufix = "";
		}
		sufix = sufix.trim();
		StringBuilder file = new StringBuilder("");
		file.append(getDirectoryPath());
		file.append(getFileName(number, sufix));
		file.append(".jpg");
		return new File(file.toString());
	}

	/**
	 * @return
	 */
	public File getDirectory() {
		if (directory == null) {
			directory = new File(getDirectoryPath());
		}
		return directory;
	}

	public File[] listFiles() {
		File file = getDirectory();
		if (file.isDirectory()) {
			return file.listFiles();
		}
		return new File[] {};
	}

	/**
	 * @return
	 */
	public String getDirectoryPath() {
		if (directoryPath == null) {
			StringBuilder dir = new StringBuilder(root);
			dir.append(getWebDirectoryPath());
			directoryPath = dir.toString();
		}
		return directoryPath;
	}

	public String getWebDirectoryPath() {
		StringBuilder dir = new StringBuilder("");
		dir.append("/pictures/");
		dir.append(type);
		if (pos != null) {
			for (Pos p : pos) {
				dir.append(nameFormat.substring(p.getX(), p.getY()));
				dir.append("/");
			}
		}
		return dir.toString();
	}

	private static final byte[] EMPTY_CONTENT = {};

	public byte[] getBytes(long number) {
		File file = getFile(number);
		if (!file.exists()) {
			return EMPTY_CONTENT;
		}
		try (InputStream is = new FileInputStream(file)) {
			byte[] bytes = new byte[(int) file.length()];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY_CONTENT;
	}

	public boolean decodeBuffer(String encoded, int number) {
		try {
			return writeFile(Base64.getDecoder().decode(encoded), number);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String encode(int number) {
		byte[] bytes = getBytes(number);
		if (bytes == null) {
			return null;
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	public File[] listFilesBefore() {
		File before = getBeforeDirectory(getDirectory(), 1);
		if (before != null && before.isDirectory()) {
			return before.listFiles();
		}
		return new File[] {};
	}

	private File getBeforeDirectory(File file, int count) {
		File parent = file.getParentFile();
		int n = 0;
		for (File f : parent.listFiles()) {
			if (f.getName().equals(file.getName())) {
				if (n == 0) {
					if (count == 1) {
						File f1 = getBeforeDirectory(parent, 2);
						if (f1 != null) {
							return f1.listFiles()[f1.listFiles().length - 1];
						}
					}
					return null;
				}
				return parent.listFiles()[n - 1];
			}
			n++;
		}
		return null;
	}

	public void setType(String type) {
		this.type = type;
		this.directory = null;
		this.directoryPath = null;
	}

	public void setRoot(String root) {
		if (root != null) {
			this.root = root;
			this.directory = null;
			this.directoryPath = null;
		}
	}

}
