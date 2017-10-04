package es.bisite.usal.bulltect.web.rest.controller.error;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.ImageResponseCode;
import es.bisite.usal.bulltect.web.uploads.exceptions.UploadFailException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageErrorController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ImageErrorController.class);
	
	@ExceptionHandler(UploadFailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleUploadFailException(UploadFailException uploadFailException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ImageResponseCode.FAILED_TO_UPLOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR,
        		messageSourceResolver.resolver("image.upload.failed"));
    }
	
	@ExceptionHandler(MultipartException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleMultipartException(MultipartException multipartException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ImageResponseCode.FAILED_TO_UPLOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR,
        		messageSourceResolver.resolver("image.upload.failed"));
    }
	
	
	
}
