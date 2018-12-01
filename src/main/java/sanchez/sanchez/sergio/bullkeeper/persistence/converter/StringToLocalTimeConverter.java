package sanchez.sanchez.sergio.bullkeeper.persistence.converter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

/**
 * String to local timer converter
 * @author sergiosanchezsanchez
 *
 */
public class StringToLocalTimeConverter implements Converter<String, LocalTime>  {

	/**
	 * Date Time Formatter
	 */
	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
	
	/**
	 * Convert
	 * @param source
	 * @return
	 */
	@Override
	public LocalTime convert(final String source) {
		return LocalTime.parse(source, fmt);
	}
}
