package sanchez.sanchez.sergio.masoc.persistence.constraints;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local Time Compare Validator
 * @author sergiosanchezsanchez
 *
 */
public class LocalTimeCompareValidator implements ConstraintValidator<LocalTimeCompare, Object>
{
	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(LocalTimeCompareValidator.class);
	
	/**
	 * Date Time Foratter
	 */
	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS");
	
    private String firstLocalTimeFieldName;
    private String secondLocalTimeFieldName;

    @Override
    public void initialize(final LocalTimeCompare constraintAnnotation)
    {
        firstLocalTimeFieldName = constraintAnnotation.first();
        secondLocalTimeFieldName = constraintAnnotation.second();
    }

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            final String firstLocalTimeString = BeanUtils.getProperty(value, firstLocalTimeFieldName);
            final String secondLocalTimeString = BeanUtils.getProperty(value, secondLocalTimeFieldName);

            // Parse First Local Time
            final LocalTime firstLocalTime = LocalTime.parse(firstLocalTimeString, fmt);
            // Parse Second Local Time
            final LocalTime secondLocalTime = LocalTime.parse(secondLocalTimeString, fmt);
           
            
            return firstLocalTime.isBefore(secondLocalTime);
          
        }
        catch (final Exception ignore)
        {
            // ignore
        	ignore.printStackTrace();
        }
        return true;
    }
}
