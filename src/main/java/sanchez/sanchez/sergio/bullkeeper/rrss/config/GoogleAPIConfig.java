package sanchez.sanchez.sergio.bullkeeper.rrss.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import com.google.api.services.oauth2.Oauth2;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * Google API Config
 * @author sergio
 */
@Configuration
@Profile({"dev", "prod"})
public class GoogleAPIConfig {
    
	/**
	 * Client Secrets
	 */
    @Value("classpath:client_secret.json")
    private Resource clientSecret;
    
    /**
     * Application Name
     */
    @Value("{youtube.application.name}")
    private String applicationName;
    
    
    /**
     * Provide Json Factory
     * @return
     */
    @Bean
    public JsonFactory provideJsonFactory() {
        return new JacksonFactory();
    }
    
    /**
     * Provide HTTP Transport
     * @return
     */
    @Bean
    public HttpTransport provideHttpTransport(){
        return new NetHttpTransport();
    }
    
    /**
     * Provide Input Stream Reader
     * @return
     * @throws IOException
     */
    @Bean 
    public InputStreamReader provideInputStreamReader() throws IOException {
       return new InputStreamReader(clientSecret.getInputStream(), "UTF-8");
    }
    
    /**
     * Provide Google Client Secrets
     * @return
     * @throws IOException
     */
    @Bean
    public GoogleClientSecrets provideGoogleClientSecrets() throws IOException{
        return GoogleClientSecrets.load(provideJsonFactory() ,provideInputStreamReader());
    }
    
    /**
     * Provide Google Credentials
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(final String accessToken, final String refreshToken) throws IOException {
    	
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
    
    /**
     * Provide Google Credentials
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(final String accessToken) throws IOException {
    	return new GoogleCredential.Builder()
                .setJsonFactory(provideJsonFactory())
                .setTransport(provideHttpTransport())
                .setClientSecrets(provideGoogleClientSecrets())
                .build()
                .setAccessToken(accessToken);
    }
    
    /**
     * Provide OAuth 2
     * @param accessToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Oauth2 provideOauth2(final String accessToken) throws IOException {
    	return new Oauth2.Builder(provideHttpTransport(), provideJsonFactory(), provideGoogleCredential(accessToken))
    		.setApplicationName(applicationName).build();
    }
    
    /**
     * Provide YouTube API
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws IOException
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public YouTube provideYoutubeApi(final String accessToken, final String refreshToken) throws IOException{
        return new YouTube.Builder(provideHttpTransport(), provideJsonFactory(), provideGoogleCredential(accessToken, refreshToken))
                .setApplicationName(applicationName)
                .build();
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(clientSecret, "Client Secret cannot be null");
        
    }
}
