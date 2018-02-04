package es.bisite.usal.bulltect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.repository.DeviceGroupRepository;

public class DeviceGroupShouldExistsValidator implements ConstraintValidator<DeviceGroupShouldExists, String> {
    
    @Autowired
    private DeviceGroupRepository deviceGroupRepository;
    
    @Override
    public void initialize(DeviceGroupShouldExists constraintAnnotation) {}

    @Override
    public boolean isValid(String groupName, ConstraintValidatorContext context) {
    	return deviceGroupRepository.countByNotificationKeyName(groupName) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
