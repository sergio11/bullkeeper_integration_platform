package es.bisite.usal.bulltect.web.rest.converters;

import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.util.Utils;

@Component
public class DaysAgoConverter implements Converter<String, Date> {

	@Override
	public Date convert(String daysAgo) {
		try {
			
			return Utils.getDateNDaysAgo(Integer.parseInt(daysAgo));
		} catch (Exception ex) {
			return new Date();
		}
		
	}

}
