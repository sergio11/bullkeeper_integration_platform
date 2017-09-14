package es.bisite.usal.bullytect.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public final class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, PhoneNumber> {
	
	private static Logger logger = LoggerFactory.getLogger(ValidPhoneNumberValidator.class);

	@Override
	public void initialize(ValidPhoneNumber validPhoneNumber) {}

	@Override
	public boolean isValid(PhoneNumber phoneNumber, ConstraintValidatorContext arg1) {
		logger.debug("Validate Phone : " + phoneNumber);
		return phoneNumber == null ? Boolean.TRUE : PhoneNumberUtil.getInstance().isValidNumber(phoneNumber);
	}

}