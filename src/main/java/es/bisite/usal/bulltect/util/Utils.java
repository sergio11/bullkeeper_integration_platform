package es.bisite.usal.bulltect.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public final class Utils {
	
	public static String getObfuscatedEmail(String mail){
		int pos = mail.indexOf('@');
		return pos >= 0 ? "...".concat(mail.substring(pos)): mail;
	}
	
	public static String getPhonePrefix(String telephone){
		
		String phonePrefix;

		PhoneNumber numberProto = null;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			numberProto  = phoneUtil.parse(telephone, null);
			phonePrefix =  "+" + numberProto.getCountryCode();
		} catch (NumberParseException e) {
			phonePrefix = null;
		}
		
		return phonePrefix;
		
	}
	
	public static Long getPhoneNumber(String telephone){

		Long phoneNumber;
		PhoneNumber numberProto = null;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			numberProto  = phoneUtil.parse(telephone, null);
			phoneNumber = numberProto.getNationalNumber();
		} catch (NumberParseException e) {
			phoneNumber = null;
		}
		
		return phoneNumber;
	}

}
