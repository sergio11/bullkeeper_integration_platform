package sanchez.sanchez.sergio.bullkeeper.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;

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
	
	/**
	 * Within The Same Week
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean withinTheSameWeek(final Date firstDate, final Date secondDate) {
		
		final Calendar calendarFirstDate = Calendar.getInstance();
		calendarFirstDate.setTime(firstDate);
		final int year1 = calendarFirstDate.get(Calendar.YEAR);
		final int week1 = calendarFirstDate.get(Calendar.WEEK_OF_YEAR);

		final Calendar calendarSecondDate = Calendar.getInstance();
		calendarSecondDate.setTime(firstDate);
		final int year2 = calendarSecondDate.get(Calendar.YEAR);
		final int week2 = calendarSecondDate.get(Calendar.WEEK_OF_YEAR);

		return year1 == year2 && week1 == week2;
		
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String generateRandomUUID() {
		MessageDigest salt;
		try {
			salt = MessageDigest.getInstance("SHA-256");
			salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
			return bytesToHex(salt.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return null;
		}	
	}
	
	
	/**
	 * 
	 * @param responseEntity
	 * @param servletResponse
	 * @throws IOException
	 */
	public static void populateResponse(ResponseEntity<APIResponse<String>> responseEntity, HttpServletResponse servletResponse)
	        throws IOException {
	    for (Map.Entry<String, List<String>> header : responseEntity.getHeaders().entrySet()) {
	        String chave = header.getKey();
	        for (String valor : header.getValue()) {
	            servletResponse.addHeader(chave, valor);                
	        }
	    }

	    servletResponse.setStatus(responseEntity.getStatusCodeValue());
	    String json = new ObjectMapper().writeValueAsString(responseEntity.getBody());
	    servletResponse.getWriter().write(json);
	}

}
