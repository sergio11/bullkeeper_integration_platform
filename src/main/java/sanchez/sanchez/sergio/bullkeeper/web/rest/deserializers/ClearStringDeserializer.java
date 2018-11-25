package sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
/**
 * Clear String Deserializer
 * @author sergiosanchezsanchez
 *
 */
public class ClearStringDeserializer extends JsonDeserializer<String>{
	
	/**
	 * 
	 */
	@Override
    public String deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		String text = jp.getText().trim().replaceAll("\\s+"," ");
		return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
