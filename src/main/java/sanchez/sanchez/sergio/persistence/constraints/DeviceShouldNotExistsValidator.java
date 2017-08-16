package sanchez.sanchez.sergio.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.persistence.repository.DeviceRepository;

public class DeviceShouldNotExistsValidator implements ConstraintValidator<DeviceShouldNotExists, String> {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void initialize(DeviceShouldNotExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext context) {
        return deviceRepository.countByRegistrationToken(token) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
