package sanchez.sanchez.sergio.fcm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;

import sanchez.sanchez.sergio.fcm.exception.FCMOperationException;
import sanchez.sanchez.sergio.rest.handler.BaseErrorHandler;

public class FCMErrorHandler extends BaseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		super.handleError(response);
		String body = IOUtils.toString(response.getBody());
		FCMOperationException operationException = new FCMOperationException();
		Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("code", response.getStatusCode().toString());
        properties.put("body", body);
        properties.put("header", response.getHeaders());
        operationException.setProperties(properties);
        throw operationException;
	}

}
