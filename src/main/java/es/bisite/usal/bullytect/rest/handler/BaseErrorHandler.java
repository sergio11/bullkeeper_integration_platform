package es.bisite.usal.bullytect.rest.handler;


import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;


public abstract class BaseErrorHandler extends DefaultResponseErrorHandler {

	final static Logger log = LoggerFactory.getLogger(BaseErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String body = IOUtils.toString(response.getBody());
		log.debug("============================response begin==========================================");
		log.debug("Status code  : {}", response.getStatusCode());
		log.debug("Status text  : {}", response.getStatusText());
		log.debug("Headers      : {}", response.getHeaders());
		log.debug("Response body: {}", body);
		log.debug("=======================response end=================================================");
	}
}
