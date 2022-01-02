package org.tsir.common.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

	private DateTimeUtils() {
	}

	public static Timestamp now() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

	public static Timestamp getTimestamp(OffsetDateTime from) {
		return new Timestamp(from.toInstant().toEpochMilli());
	}

	public static OffsetDateTime getOffsetDateTime(Timestamp from) {
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(from.getTime()), ZoneId.of("UTC"));
	}

	/**
	 * @author Cristhian Murillo
	 * @return
	 */
	public static Date getCurrentDate() {
		// TODO Modificar esto para que la hora la consulte directamente en la BD en un
		// SDF parametrizado
		return new Date();
	}

}
