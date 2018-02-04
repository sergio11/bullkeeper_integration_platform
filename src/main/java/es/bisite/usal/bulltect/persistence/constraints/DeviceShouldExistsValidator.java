package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceShouldExistsValidator implements ConstraintValidator<DeviceShouldExists, String> {
    
    private static Logger logger = LoggerFactory.getLogger(DeviceShouldExistsValidator.class);
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Override
    public void initialize(DeviceShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String deviceId, ConstraintValidatorContext context) {
        logger.debug("check device id -> " + deviceId);
    	return deviceRepository.countByDeviceId(deviceId) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
