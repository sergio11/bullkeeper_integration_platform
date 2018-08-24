package sanchez.sanchez.sergio.masoc.web.rest.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ClearStringDeserializer extends JsonDeserializer<String>{
	
	@Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		String text = jp.getText().trim().replaceAll("\\s+"," ");
		return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
