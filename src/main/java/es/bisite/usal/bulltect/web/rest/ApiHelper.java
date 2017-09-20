package es.bisite.usal.bulltect.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.IResponseCodeTypes;
import es.bisite.usal.bulltect.web.rest.response.ResponseStatus;

@Component
public class ApiHelper {
	
	private final static String RESPONSE_CODE_HEADER_NAME = "X-RESPONSE-CODE";
	private final static String RESPONSE_NAME_HEADER_NAME = "X-RESPONSE-NAME";
	
	public static String BASE_API_ANY_REQUEST;
	public static String AUTHENTICATION_ANY_REQUEST;
	
	
	private static HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
	
	private static HttpHeaders buildHeaders(Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);
        for(Map.Entry<String, String> headerEntry : headersMap.entrySet()) {
        	headers.set(headerEntry.getKey(), headerEntry.getValue());
        	
        }
        return headers;
    }

    public static <T> ResponseEntity<APIResponse<T>> createAndSendResponse(APIResponse<T> response) {
        HttpHeaders headers = buildHeaders();
        ResponseEntity<APIResponse<T>> responseEntity = new ResponseEntity<APIResponse<T>>(response, headers, response.getHttpStatusCode());
        return responseEntity;
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendResponse(APIResponse<T> response, Map<String, String> headersMap) {
        HttpHeaders headers = buildHeaders(headersMap);
        ResponseEntity<APIResponse<T>> responseEntity = new ResponseEntity<APIResponse<T>>(response, headers, response.getHttpStatusCode());
        return responseEntity;
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendResponse(IResponseCodeTypes responseCode, HttpStatus httpStatusCode, T data) { 
        APIResponse<T> restApiError = new APIResponse<T>(responseCode, ResponseStatus.SUCCESS, httpStatusCode, getInfoUrl(responseCode), data);
        return createAndSendResponse(restApiError);
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendErrorResponse(IResponseCodeTypes responseCode, HttpStatus httpStatusCode, T data) { 
        APIResponse<T> restApiError = new APIResponse<T>(responseCode, ResponseStatus.ERROR, httpStatusCode, getInfoUrl(responseCode), data);
        return createAndSendResponse(restApiError);
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendErrorResponse(IResponseCodeTypes responseCode, HttpStatus httpStatusCode, T data, 
    		Map<String, String> headers) { 
        APIResponse<T> restApiError = new APIResponse<T>(responseCode, ResponseStatus.ERROR, httpStatusCode, getInfoUrl(responseCode), data);
        return createAndSendResponse(restApiError, headers);
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendErrorResponseWithHeader(IResponseCodeTypes responseCode, HttpStatus httpStatusCode, T data) { 
        APIResponse<T> restApiError = new APIResponse<T>(responseCode, ResponseStatus.ERROR, httpStatusCode, getInfoUrl(responseCode), data);
        Map<String, String> headers = new HashMap<>();
        headers.put(RESPONSE_CODE_HEADER_NAME, responseCode.getResponseCode().toString());
        headers.put(RESPONSE_NAME_HEADER_NAME, responseCode.toString());
        return createAndSendResponse(restApiError, headers);
    }
    
    protected static String getInfoUrl(IResponseCodeTypes responseCode){
        return "http://yourAppUrlToDocumentedApiCodes.com/api/support/" + responseCode.getResponseCode();
    }
    
    @Value("#{'${api.base.path}' + '${api.version}' + '${jwt.route.authentication.path}' + '/**'}")
    public void setAuthenticationAnyRequest(String authenticationAnyRequest) {
    	ApiHelper.AUTHENTICATION_ANY_REQUEST = authenticationAnyRequest;
    }
    
    @Value("#{'${api.base.path}' + '${api.version}' + '/**'}")
    public void setBaseApi(String baseApiAnyRequest) {
    	ApiHelper.BASE_API_ANY_REQUEST = baseApiAnyRequest;
    }
}
