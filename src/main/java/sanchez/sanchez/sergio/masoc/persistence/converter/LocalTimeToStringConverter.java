package sanchez.sanchez.sergio.masoc.persistence.converter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

/**
 * Local Time to string converter
 * @author sergiosanchezsanchez
 *
 */
public class LocalTimeToStringConverter implements Converter<LocalTime, String>  {

	/**
	 * Date Time Foratter
	 */
	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
	
	/**
	 * Convert
	 */
	@Override
	public String convert(LocalTime source) {
		return source.toString(fmt);
	}

}
