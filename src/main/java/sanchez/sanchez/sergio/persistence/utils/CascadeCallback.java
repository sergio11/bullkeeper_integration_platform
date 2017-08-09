package sanchez.sanchez.sergio.persistence.utils;

import java.lang.reflect.Field;
import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.mongoOperations = mongoOperations;
    }

	@Override
	public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
		
			final Object fieldValue = field.get(getSource());
			if (fieldValue != null) {
				if (fieldValue instanceof List) {
					List valueList = (List) fieldValue;
					for(Object value: valueList) {
						final FieldCallback callback = new FieldCallback();
						ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
						mongoOperations.save(value);
					}
						
				} else {
					final FieldCallback callback = new FieldCallback();
					ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
					mongoOperations.save(fieldValue);
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
