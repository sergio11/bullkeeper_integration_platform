package sanchez.sanchez.sergio.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.IResponseCodeTypes;
import sanchez.sanchez.sergio.rest.response.ResponseStatus;

public abstract class BaseErrorRestController {
	
	protected ResponseEntity<APIResponse<String>> createAndSendResponse(HttpStatus status, IResponseCodeTypes responseCode, String message){
        APIResponse<String> restApiError = new APIResponse<>(responseCode, ResponseStatus.ERROR, status, this.getInfoUrl(responseCode), message);
        return ApiHelper.<String>createAndSendResponse(restApiError);
    }
    
    protected String getInfoUrl(IResponseCodeTypes responseCode){
        return "http://yourAppUrlToDocumentedApiCodes.com/api/support/" + responseCode.getResponseCode();
    }

}
