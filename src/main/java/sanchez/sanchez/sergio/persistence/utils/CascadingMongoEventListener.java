package sanchez.sanchez.sergio.persistence.utils;


import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;

@Component
public class CascadingMongoEventListener  extends AbstractMongoEventListener {
    
    @Autowired
    private SocialMediaRepository socialMedia;

    @Override
    public void onBeforeConvert(final BeforeConvertEvent event) {
        final Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new CascadeCallback(source, socialMedia));
    }
}