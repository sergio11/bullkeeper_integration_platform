package sanchez.sanchez.sergio.masoc.web.rest.controller.error;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ImageResponseCode;
import sanchez.sanchez.sergio.masoc.web.uploads.exceptions.UploadFailException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageErrorController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ImageErrorController.class);
	
	@ExceptionHandler(UploadFailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleUploadFailException(UploadFailException uploadFailException, HttpServletRequest request) {
		logger.error("handle UploadFailException");
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ImageResponseCode.FAILED_TO_UPLOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR,
        		messageSourceResolver.resolver("image.upload.failed"));
    }
	
	@ExceptionHandler(MultipartException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleMultipartException(MultipartException multipartException, HttpServletRequest request) {
		
		ResponseEntity<APIResponse<String>> response;
		String maxFileSize = getMaxUploadFileSize(multipartException);
	    if (maxFileSize != null) {
	    	response = ApiHelper.<String>createAndSendErrorResponseWithHeader(ImageResponseCode.UPLOAD_FILE_IS_TOO_LARGE, HttpStatus.BAD_REQUEST,
	        		messageSourceResolver.resolver("upload.file.is.too.large", new Object[] { maxFileSize }));
	    }
	    else {
	      response = ApiHelper.<String>createAndSendErrorResponseWithHeader(ImageResponseCode.FAILED_TO_UPLOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR,
	        		messageSourceResolver.resolver("image.upload.failed"));
	    }
        return response;
    }
	
	
	private String getMaxUploadFileSize(MultipartException ex) {
		if (ex instanceof MaxUploadSizeExceededException) {
			return asReadableFileSize(((MaxUploadSizeExceededException) ex).getMaxUploadSize());
		}
		String msg = ex.getMessage();
		if (msg.contains("SizeLimitExceededException")) {
			String maxFileSize = msg.substring(msg.indexOf("maximum")).replaceAll("\\D+", "");
			if (StringUtils.isNumeric(maxFileSize)) {
				return asReadableFileSize(Long.valueOf(maxFileSize));
			}
		}

		return null;
	}

	private static String asReadableFileSize(long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
}
