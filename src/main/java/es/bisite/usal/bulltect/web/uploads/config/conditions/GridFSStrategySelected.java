package es.bisite.usal.bulltect.web.uploads.config.conditions;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.web.uploads.properties.UploadProperties;


@Component
public class GridFSStrategySelected implements Condition {
	
	private final static String STRATEGY_NAME = "gridfs";
	
	@Autowired
	private UploadProperties uploadProperties;

	@Override
	public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
		return uploadProperties.getStrategy().equals(STRATEGY_NAME);
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(uploadProperties, "UploadProperties can not be null");
		Assert.notNull(uploadProperties.getStrategy(), "Upload Strategy can not be null");
		Assert.hasLength(uploadProperties.getStrategy(), "Upload Strategy can not be empty");
	}

}
