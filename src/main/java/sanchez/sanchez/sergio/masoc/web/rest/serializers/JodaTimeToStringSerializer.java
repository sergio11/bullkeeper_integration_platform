package sanchez.sanchez.sergio.masoc.web.rest.serializers;

import java.io.IOException;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Joda Time To String Serializer
 * @author sergiosanchezsanchez
 *
 */
public class JodaTimeToStringSerializer extends JsonSerializer<LocalTime> {
	
	/**
	 * Date Time Formatter
	 */
	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
	

	/**
	 * Serialize
	 */
	@Override
	public void serialize(final LocalTime value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		gen.writeString(value.toString(fmt));	
	}

	

}
