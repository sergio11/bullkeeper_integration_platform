package es.bisite.usal.bulltect.fcm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import es.bisite.usal.bulltect.fcm.exception.FCMOperationException;
import es.bisite.usal.bulltect.web.rest.handler.BaseErrorHandler;

public class FCMErrorHandler extends BaseErrorHandler {
	
	private static Logger logger = LoggerFactory.getLogger(FCMErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		super.handleError(response);
		logger.debug("FCMErrorHandler called ... ");
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
