package sanchez.sanchez.sergio.bullkeeper.persistence.utils;


import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.ChildrenController;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class CascadingMongoEventListener  extends AbstractMongoEventListener {
	
	private static Logger logger = LoggerFactory.getLogger(CascadingMongoEventListener.class);
    
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(final BeforeConvertEvent event) {
        final Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new CascadeCallback(source, mongoOperations));
       
    }
}