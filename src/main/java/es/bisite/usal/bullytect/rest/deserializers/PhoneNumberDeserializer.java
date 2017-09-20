package es.bisite.usal.bullytect.rest.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberDeserializer extends  JsonDeserializer<PhoneNumber>  {

	@Override
	public PhoneNumber deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
			throws IOException, JsonProcessingException {
		
		
		PhoneNumber numberProto = null;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			numberProto  = phoneUtil.parse(jsonparser.getText(), null);
		} catch (NumberParseException e) {
		}
		
		return numberProto;
	}

}
