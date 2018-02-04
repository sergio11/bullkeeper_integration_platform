package es.bisite.usal.bulltect.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.util.Assert;

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
	
	public static Date getDateNDaysAgo(Integer daysAgo) {
		Assert.notNull(daysAgo, "Days Ago can not be null");
		Assert.isTrue(daysAgo >= 0, "Days Ago should be greater than or equal to zero");
		
		Calendar calendar = Calendar.getInstance(); 
    	calendar.add(Calendar.DATE, -daysAgo); 
    	return calendar.getTime();
	}
	
	public static Date getDateNHoursAgo(Integer hoursAgo) {
		Assert.notNull(hoursAgo, "Hours Ago can not be null");
		Assert.isTrue(hoursAgo >= 0, "Hours Ago should be greater than or equal to zero");
		
		Calendar calendar = Calendar.getInstance(); 
    	calendar.add(Calendar.HOUR, -hoursAgo); 
    	return calendar.getTime();
	}
	
	public static Date getDateNMonthAgo(Integer monthAgo) {
		Assert.notNull(monthAgo, "Month Ago can not be null");
		Assert.isTrue(monthAgo >= 0, "Month Ago should be greater than or equal to zero");
		
		Calendar calendar = Calendar.getInstance(); 
    	calendar.add(Calendar.DATE, -monthAgo); 
    	return calendar.getTime();
	}

}
