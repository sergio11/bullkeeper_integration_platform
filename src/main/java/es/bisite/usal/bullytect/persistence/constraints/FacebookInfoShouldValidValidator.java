package es.bisite.usal.bullytect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

public class FacebookInfoShouldValidValidator implements ConstraintValidator<FacebookInfoShouldValid, Object>
{
	
	private String fbIdFieldName;
    private String fbAccessTokenFieldName;

    @Override
    public void initialize(final FacebookInfoShouldValid constraintAnnotation)
    {
    	fbIdFieldName = constraintAnnotation.fbId();
    	fbAccessTokenFieldName = constraintAnnotation.fbAccessToken();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
    	Boolean isValid = Boolean.TRUE;
    	
        try
        {
        	final String fbId = BeanUtils.getProperty(value, fbIdFieldName);
            final String fbAccessToken = BeanUtils.getProperty(value, fbAccessTokenFieldName);
        	
        	
        	FacebookClient facebookClient = new DefaultFacebookClient(fbAccessToken, Version.VERSION_2_8);
        	
        	User user = facebookClient.fetchObject("me", User.class);
            if(!user.getId().equals(fbId)) {
            	isValid = Boolean.FALSE;
            }
        }
        catch (final Exception ignore)
        {
        	isValid = Boolean.FALSE;
        }
        
        
        return isValid;
    }
}
