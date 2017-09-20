package es.bisite.usal.bulltect.web.uploads.properties;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class UploadProperties implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Value("${upload.strategy}")
    private String strategy;

	public String getStrategy() {
		return strategy;
	}

}
