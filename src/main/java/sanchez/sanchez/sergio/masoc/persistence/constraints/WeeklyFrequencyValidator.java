package sanchez.sanchez.sergio.masoc.persistence.constraints;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ScheduledBlockRepository;

/**
 * Weekly Frequency Validator
 * @author sergiosanchezsanchez
 *
 */
public class WeeklyFrequencyValidator implements ConstraintValidator<WeeklyFrequencyValidate, Object>
{
	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(WeeklyFrequencyValidator.class);
	
	/**
	 * Scheduled Block Repository
	 */
	@Autowired
	protected ScheduledBlockRepository scheduledRepository;
	
	/**
	 * Date Time Formatter
	 */
	protected DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS");
	
    private String startAtFieldName;
    private String weeklyFrequencyFieldName;

    @Override
    public void initialize(final WeeklyFrequencyValidate constraintAnnotation)
    {
    	startAtFieldName = constraintAnnotation.startAtFieldName();
    	weeklyFrequencyFieldName = constraintAnnotation.weeklyFrequency();
    }

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            final String startAtString = BeanUtils.getProperty(value, startAtFieldName);
            final String[] weeklyFrequencyString = BeanUtils.getArrayProperty(value, weeklyFrequencyFieldName);

            // Start At Local Time
            final LocalTime startAtLocalTime = LocalTime.parse(startAtString, fmt);
            
            // Find All 
            final Iterable<ScheduledBlockEntity> scheduledBlocks = 
            		scheduledRepository.findAllByStartAtLessThanEqualAndEndAtGreaterThanEqual(startAtLocalTime);
           
     
            
            return true;
          
        }
        catch (final Exception ignore)
        {
            // ignore
        	ignore.printStackTrace();
        }
        return true;
    }
}
