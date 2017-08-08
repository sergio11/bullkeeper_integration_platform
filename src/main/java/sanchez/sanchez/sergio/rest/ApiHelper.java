package sanchez.sanchez.sergio.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.IResponseCodeTypes;

@Component
public class ApiHelper {
	
	public static String AUTHENTICATION_ANY_REQUEST;
	
	private static HttpHeaders getCommonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static <T> ResponseEntity<APIResponse<T>> createAndSendResponse(APIResponse<T> response) {
        HttpHeaders headers = getCommonHeaders();
        ResponseEntity<APIResponse<T>> responseEntity = new ResponseEntity<APIResponse<T>>(response, headers, response.getHttpStatusCode());
        return responseEntity;
    }
    
    public static <T> ResponseEntity<APIResponse<T>> createAndSendResponse(IResponseCodeTypes responseCode, T data, HttpStatus httpStatusCode) { 
        HttpHeaders headers = getCommonHeaders();
        ResponseEntity<APIResponse<T>> responseEntity = new ResponseEntity<APIResponse<T>>(new APIResponse<T>(responseCode, data), headers, httpStatusCode);
        return responseEntity;
    }
    
    @Value("#{'${api.base.path}' + '${api.version}' + '${jwt.route.authentication.path}' + '/**'}")
    public void setAuthenticationAnyRequest(String authenticationAnyRequest) {
    	ApiHelper.AUTHENTICATION_ANY_REQUEST = authenticationAnyRequest;
    }
}
