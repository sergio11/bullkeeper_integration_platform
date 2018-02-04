package es.bisite.usal.bulltect.fcm.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import es.bisite.usal.bulltect.fcm.exception.FCMOperationException;
import es.bisite.usal.bulltect.web.rest.handler.BaseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class FCMErrorHandler extends BaseErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(FCMErrorHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        super.handleError(response);
        logger.debug("FCMErrorHandler called ... ");
        logger.debug("response -> " + response.toString());
        String body = IOUtils.toString(response.getBody());
        logger.debug("Error Body -> " + body);
        Map<String, String> bodyMap = new HashMap<String, String>();

        bodyMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {
        });
        logger.debug("bodyMap -> " + bodyMap.toString());

        FCMOperationException operationException = new FCMOperationException();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("code", response.getStatusCode().toString());
        properties.put("body", body);
        properties.put("header", response.getHeaders());
        operationException.setProperties(properties);
        throw operationException;
    }

}
