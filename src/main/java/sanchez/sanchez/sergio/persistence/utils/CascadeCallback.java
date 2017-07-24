package sanchez.sanchez.sergio.persistence.utils;

import java.lang.reflect.Field;
import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;

public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private SocialMediaRepository repository;

    public CascadeCallback(final Object source, final SocialMediaRepository repository) {
        this.source = source;
        this.repository = repository;
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());

            if (fieldValue != null) {
                final FieldCallback callback = new FieldCallback();

                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                repository.save((List)fieldValue);
            }
        }

    }

    private Object getSource() {
        return source;
    }

    public void setSource(final Object source) {
        this.source = source;
    }
}
