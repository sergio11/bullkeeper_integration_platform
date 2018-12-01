package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;

/**
 * Valid Guardian Roles Type
 * @author sergiosanchezsanchez
 *
 */
public class ValidGuardianRolesTypeValidator implements ConstraintValidator<ValidGuardianRolesType, String> {
    
    @Override
    public void initialize(ValidGuardianRolesType constraintAnnotation) {}

    /**
     * Is Valid
     */
    @Override
    public boolean isValid(String guardianRoleType, ConstraintValidatorContext context) {
    	return EnumUtils.getEnum(GuardianRolesEnum.class, guardianRoleType) != null;
    }
}
