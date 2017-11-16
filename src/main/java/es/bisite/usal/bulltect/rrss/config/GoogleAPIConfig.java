package es.bisite.usal.bulltect.rrss.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 *
 * @author sergio
 */
@Configuration
@Profile({"dev", "prod"})
public class GoogleAPIConfig {
    
    @Value("classpath:client_secret.json")
    private Resource clientSecret;
    
    @Value("{youtube.application.name}")
    private String applicationName;
    
    @Bean
    public JsonFactory provideJsonFactory() {
        return new JacksonFactory();
    }
    
    @Bean
    public HttpTransport provideHttpTransport(){
        return new NetHttpTransport();
    }
    
    @Bean 
    public InputStreamReader provideInputStreamReader() throws IOException {
       return new InputStreamReader(clientSecret.getInputStream(), "UTF-8");
    }
    
    @Bean
    public GoogleClientSecrets provideGoogleClientSecrets() throws IOException{
        return GoogleClientSecrets.load(provideJsonFactory() ,provideInputStreamReader());
    }
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(String accessToken, String refreshToken) throws IOException {
    	GoogleCredential credential = new GoogleCredential.Builder()
                .setJsonFactory(provideJsonFactory())
                .setTransport(provideHttpTransport())
                .setClientSecrets(provideGoogleClientSecrets())
                .build();
    	
    	credential.setAccessToken(accessToken);
    	
    	if(refreshToken != null) {
    		credential.setRefreshToken(refreshToken);
    	}
    	
    	return credential;
    }
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public YouTube provideYoutubeApi(String accessToken, String refreshToken) throws IOException{
        return new YouTube.Builder(provideHttpTransport(), provideJsonFactory(), provideGoogleCredential(accessToken, refreshToken))
                .setApplicationName(applicationName)
                .build();
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(clientSecret, "Client Secret cannot be null");
        
    }
}
