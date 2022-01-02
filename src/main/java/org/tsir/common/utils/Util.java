package org.tsir.common.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author VJT-HROJAS
 * 
 */
public class Util {

	private static Locale locale;

	private static NumberFormat numberFormat;

	private static DateFormat timeFormat;

	private static DateFormat dateFormat;

	private static DateFormat dateTimeFormat;

	private static DateFormat completeFormat;

	private static SimpleDateFormat defaultFormat;

	/**
	 * Format: 'yyyyMMddHHmmss'
	 */
	public final static DateTimeFormatter NUMB_FULL_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	/**
	 * Format: 'yyyyMMddHHmmssSSS'
	 */
	public final static DateTimeFormatter NUMB_FULL_MS_FORMAT = new DateTimeFormatterBuilder()
			.appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();

	/**
	 * Format: 'yyyy-MM-dd'
	 */
	public final static DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

	/**
	 * Format: 'MMM dd 'de' yyyy HH:mm:ss'
	 */
	private final static DateTimeFormatter STRING_FULL_FORMAT = DateTimeFormatter
			.ofPattern("MMM dd 'de' yyyy HH:mm:ss");

	/**
	 * @param b
	 * @return lrc
	 * @description Realiza un lrc de un arreglo de bytes
	 */
	public static char getLRC(byte[] b) {
		int result = unsignedInt(b[0]);
		for (int i = 1; i < b.length; i++) {
			result = (int) (result ^ unsignedInt(b[i]));
		}
		return (char) result;
	}

	/**
	 * @param b
	 * @return unsigned int
	 */
	public static int unsignedInt(byte b) {
		return b & 0xFF;
	}

	/**
	 * @param pass
	 * @return password
	 * @description Convierte a string un arreglo de caracteres
	 */
	public static String getPassword(char[] pass) {
		return new String(pass);
	}

	/**
	 * @param pass
	 * @return String
	 * @description Elimina el �ltimo caracter de un password
	 */
	public static String deleteFromPassword(char[] pass) {
		return deleteFromText(getPassword(pass));
	}

	/**
	 * @param text
	 * @return String
	 * @description Elimina el �ltimo caracter del texto
	 */
	public static String deleteFromText(String text) {
		try {
			return text.substring(0, text.length() - 1);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @return Locale para Colombia
	 */
	public static Locale getLocale() {
		if (locale == null) {
			for (Locale l : Locale.getAvailableLocales()) {
				if (l.getCountry().equals("CO")) {
					locale = l;
					break;
				}
			}
		}
		return locale;
	}

	/**
	 * @return Calendar actual instanciado para la zona horaria de Colombia
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance(getLocale());
	}

	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getTimestamp(long timeMillis) {
		return new Timestamp(timeMillis);
	}

	public static LocalDateTime fromMillisDefault(long millis) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
	}

	/**
	 * @param timeMillis
	 * @return Calendar instanciado para la zona horaria de Colombia
	 */
	public static Calendar getCalendar(long timeMillis) {
		Calendar calendar = getCalendar();
		calendar.setTimeInMillis(timeMillis);
		return calendar;
	}

	/**
	 * @return NumberFormat para adicionar o quitar puntos a cada mil en un n�mero
	 */
	public static NumberFormat getNumberFormat() {
		if (numberFormat == null) {
			numberFormat = NumberFormat.getInstance();
			numberFormat.setCurrency(Currency.getInstance(Locale.getDefault()));
		}
		return numberFormat;
	}

	/**
	 * @param string
	 * @return par�metro string sin puntos
	 */
	public static Number parseNumber(String string) {
		try {
			return getNumberFormat().parse(string);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param number
	 * @return par�metro string formateado con puntos
	 */
	public static String numberFormat(String number) {
		if (number == null)
			return null;
		if (number.length() > 0)
			return numberFormat(parseNumber(number));
		return "";
	}

	/**
	 * @param number
	 * @return par�metro number formateado con puntos
	 */
	public static String numberFormat(Number number) {
		if (number == null)
			return null;
		return getNumberFormat().format(number);
	}

	/**
	 * @param list
	 * @return String con la informaci�n de la lista
	 */
	public static String toString(List<?> list) {
		if (list != null && list.size() > 0) {
			String s = "";
			int i = 0;
			for (Object o : list) {
				if (i == 0) {
					s = s + o.toString();
					i++;
				} else
					s = s + "*" + o.toString();
			}
			return s;
		}
		return "null";
	}

	/**
	 * @param calendar
	 * @return Fecha formateada
	 */
	public static String dateFormat(Calendar calendar) {
		return getFormat(getDateFormat(), calendar.getTime()).toUpperCase();
	}

	private static DateFormat getDateFormat() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat("MMMM dd 'de' yyyy");
		}
		return dateFormat;
	}

	/**
	 * @param calendar
	 * @return Hora formateada
	 */
	public static String timeFormat(Calendar calendar) {
		return getFormat(getTimeFormat(), calendar.getTime());
	}

	private static DateFormat getTimeFormat() {
		if (timeFormat == null) {
			timeFormat = new SimpleDateFormat("HH:mm:ss");
		}
		return timeFormat;
	}

	/**
	 * @param calendar
	 * @return Fecha y hora formateada
	 */
	public static String dateTimeFormat(Calendar calendar) {
		return getFormat(getDateTimeFormat(), calendar.getTime()).toUpperCase();
	}

	public static String dateTimeFormat(LocalDateTime calendar) {
		// getDateTimeFormat(),
		return calendar.format(STRING_FULL_FORMAT).toUpperCase();
	}

	private static DateFormat getDateTimeFormat() {
		if (dateTimeFormat == null) {
			dateTimeFormat = new SimpleDateFormat("MMM dd 'de' yyyy HH:mm:ss");
		}
		return dateTimeFormat;
	}

	public static String nowFullFormat() {
		return LocalDateTime.now().format(NUMB_FULL_MS_FORMAT);
	}

	public static String completeFormat(Calendar calendar) {
		return getFormat(getCompleteFormat(), calendar.getTime());
	}

	public static String completeFormat(Date date) {
		return getFormat(getCompleteFormat(), date);
	}

	private static DateFormat getCompleteFormat() {
		if (completeFormat == null) {
			completeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		}
		return completeFormat;
	}

	public static String defaultFormat(Calendar calendar) {
		return getFormat(getDefaultFormat(), calendar.getTime());
	}

	private static DateFormat getDefaultFormat() {
		if (defaultFormat == null) {
			defaultFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		return defaultFormat;
	}

	private static String getFormat(DateFormat format, Date date) {
		return format.format(date);
	}

	public static String toHexStringFormat(String number) throws StringIndexOutOfBoundsException, NullPointerException {
		return (sizeFormat("", 8, '0').substring(0, 8 - number.length()) + number).toUpperCase();
	}

	public static String toHexStringFormat(int i) throws StringIndexOutOfBoundsException, NullPointerException {
		return toHexStringFormat(Integer.toHexString(i));
	}

	public static String toHexStringFormat(String number, int length)
			throws StringIndexOutOfBoundsException, NullPointerException {
		return (sizeFormat("", 8, '0').substring(8 - length).substring(0, length - number.length()) + number)
				.toUpperCase();
	}

	public static String toHexStringFormat(int i, int length)
			throws StringIndexOutOfBoundsException, NullPointerException {
		return toHexStringFormat(Integer.toHexString(i), length);
	}

	public static String toBinaryString(int i) {
		return sizeFormat(Integer.toBinaryString(i), 8, '0');
	}

	public static String toBinaryString(int i, int size) {
		return sizeFormat(Integer.toBinaryString(i), size, '0');
	}

	public static String sizeFormat(String text, int size, char c) {
		if (size < text.length())
			return text.substring(text.length() - size);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++)
			sb.append(c);
		return sb.substring(0, size - text.length()) + text;
	}

	public static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	/**
	 * Calculates the verification string with algorithm CRC-CCITT (0xFFFF).
	 * 
	 * @param bytes
	 *            Byte array without STX and ETX.
	 * @return
	 */
	public static String getCRC(byte[] bytes) {
		int crc = 0xFFFF; // initial value
		int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)
		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		System.out.println(String.format("%04X", crc));
		return String.format("%04X", crc);
	}

	/**
	 * Calculates the string verification encoding the string to ASCII and using
	 * {@link Utils#getCRC(byte[])} method.
	 * 
	 * @see Utils#getCRC(byte[])
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getCRC(String data) throws UnsupportedEncodingException {
		return getCRC(data.getBytes("ASCII"));
	}

	/**
	 * Formats a number to String padding to the left with character '0' (zero) to
	 * the specific length.
	 * 
	 * @param <T>
	 *            object extended from <code>Number</code>
	 * @param num
	 *            Number to be formatted.
	 * @param lenght
	 *            required size.
	 * @return String formatted.
	 */
	public static <T extends Number> String padNumber(T num, int lenght) {
		return String.format("%0" + lenght + "d", num.longValue());
	}

	/**
	 * Formats the String padding to the left with the character to the specific
	 * length.
	 * 
	 * @param str
	 *            String to format.
	 * @param fill
	 *            Character to fill.
	 * @param lenght
	 *            required size.
	 * 
	 * @return String formatted.
	 */
	public static String padString(String str, char fill, int length) {
		return String.format("%" + length + "s", str).replace(' ', fill);
	}

	public static String padAlignString(String str, char fill, int length, int align) {
		if (str != null && str.length() > 0) {
			int size = str.length();
			if (size == length) {
				return str;
			} else if (size > length) {
				for (int i = 0; str.length() > length; i++) {
					switch (align) {
					case 0:
						str = str.substring(0, str.length() - 1);
						break;
					case 1:
						if (i % 2 == 0) {
							str = str.substring(0, str.length() - 1);
						} else {
							str = str.substring(1, str.length());
						}
						break;
					case 2:
						str = str.substring(1, str.length());
						break;
					default:
						str = str.substring(0, str.length() - 1);
						break;
					}
				}
				return str;
			} else {
				for (int i = 0; str.length() < length; i++) {
					switch (align) {
					case 0:
						str = str.concat(String.valueOf(fill));
						break;
					case 1:
						if (i % 2 == 0) {
							str = str.concat(String.valueOf(fill));
						} else {
							str = String.valueOf(fill).concat(str);
						}
						break;
					case 2:
						str = String.valueOf(fill).concat(str);
						break;
					default:
						str = str.concat(String.valueOf(fill));
						break;
					}
				}
				return str;
			}
		} else {
			return padString(" ", ' ', length);
		}
	}

	/**
	 * Gets round to the next hundred value.
	 * 
	 * @param value
	 *            value to round
	 * @return value rounded
	 */
	public static int getRoundValue(int value) {
		return (value / 50) * 50;
	}

	/**
	 * Gets the decimal value from a byte hex list. No handles Exceptions.
	 * 
	 * @param data
	 *            list of bytes to process.
	 * @return the integer value.
	 */
	public static int getDecimalValue(List<Byte> data) {
		String str = "";
		Iterator<Byte> it = data.iterator();
		while (it.hasNext()) {
			str = str.concat(String.format("%02X", it.next().byteValue()));
		}
		return Integer.valueOf(str, 16);
	}

	/**
	 * Converts an integer value in hex string formatted by four bytes.
	 * 
	 * @param value
	 *            integer value.
	 * @return hex String
	 */
	public static String formatValueToSend(int value) {
		String hexFormt = Integer.toHexString(value);
		while (hexFormt.length() < 8) {
			hexFormt = "0".concat(hexFormt);
		}
		StringBuilder valFormt = new StringBuilder(4);
		for (int i = 0; i < hexFormt.length(); i += 2) {
			valFormt.append((char) Integer.parseInt(hexFormt.substring(i, i + 2), 16));
		}
		return valFormt.toString();
	}

	/**
	 * Converts a byte list to a text string, getting the character equivalent for
	 * each byte in the list. Concatenates all characters to complete .
	 * 
	 * @param data
	 *            list of bytes to process.
	 * @return
	 */
	public static String getText(List<Byte> data) {
		if (data.size() > 0) {
			String str = "";
			Iterator<Byte> it = data.iterator();
			while (it.hasNext()) {
				str = str.concat("" + (char) it.next().byteValue());
			}
			return str;
		}
		return null;
	}

	/**
	 * Obtains a string representation of the byte list in hex base.
	 * 
	 * @param data
	 *            list of bytes to process.
	 * @return The hex string representation in upper case mode. Return empty string
	 *         if the list is empty.
	 */
	public static String getValue(List<Byte> data) {
		String str = "";
		Iterator<Byte> it = data.iterator();
		while (it.hasNext()) {
			str = str.concat(String.format("%02X", it.next().byteValue()));
		}
		return str;
	}

	public static int matchDiff(String dev, String lpr) {
		try {
			int diff = 0;
			for (int i = 0; i < dev.length(); i++) {
				char ch = dev.charAt(i);
				int idx = lpr.indexOf(ch, (i == 0 ? i : i - 1));
				if (idx == -1 || Math.abs(idx - i) != 0) {
					diff++;
				}
			}
			return diff;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
