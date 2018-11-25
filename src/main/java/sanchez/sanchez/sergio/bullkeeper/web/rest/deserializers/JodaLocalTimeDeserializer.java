package sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers;

import java.io.IOException;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class JodaLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

	/**
	 * Date Time Formatter
	 */

	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS");
	
	/**
	 * Deserialize
	 */
	@Override
	public LocalTime deserialize(final JsonParser jp, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		String textToParse = jp.getText().trim();
		
		LocalTime localTime = null;
		
		try {
			localTime = LocalTime.parse(textToParse, fmt);
		} catch (final Exception ex) {
			ex.printStackTrace();		
		}
		
		return localTime;
	}

}
