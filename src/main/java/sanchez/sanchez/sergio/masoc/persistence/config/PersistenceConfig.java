package sanchez.sanchez.sergio.masoc.persistence.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sanchez.sanchez.sergio.masoc.persistence.converter.LocalTimeToStringConverter;
import sanchez.sanchez.sergio.masoc.persistence.converter.StringToLocalTimeConverter;


/**
 * Persistence Config
 * @author sergiosanchezsanchez
 *
 */
@Configuration
@EnableMongoRepositories( value = { "sanchez.sanchez.sergio.masoc.persistence.repository" } )
public class PersistenceConfig {
	
	/**
	 * Custom Conversions
	 * @return
	 */
	 @Bean
	 public CustomConversions customConversions(){
	     return new CustomConversions(Arrays.asList(
	    		 new LocalTimeToStringConverter(),
	    		 new StringToLocalTimeConverter()
	     ));
	 }
}
