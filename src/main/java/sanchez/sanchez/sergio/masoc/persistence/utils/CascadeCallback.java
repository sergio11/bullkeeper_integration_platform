package sanchez.sanchez.sergio.masoc.persistence.utils;

import java.lang.reflect.Field;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

public class CascadeCallback implements ReflectionUtils.FieldCallback {
	
	private static Logger logger = LoggerFactory.getLogger(CascadeCallback.class);

    private Object source;
    private MongoOperations mongoOperations;

    public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.mongoOperations = mongoOperations;
    }
    
    private void saveFieldValue(Object fieldValue){
    	final FieldCallback callback = new FieldCallback();
		ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
		if (!callback.isIdFound()) {
			throw new MappingException("Cannot perform cascade save on child object without id set");
		}
		mongoOperations.save(fieldValue);
    }

	@Override
	public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.makeAccessible(field);
		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
			final Object fieldValue = field.get(getSource());
			if (fieldValue != null) {

				if (Collection.class.isAssignableFrom(field.getType())) {
					Collection collection = (Collection) fieldValue;
					for (Object element : collection) {
						saveFieldValue(element);
					}
				} else {
					saveFieldValue(fieldValue);
				}

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
