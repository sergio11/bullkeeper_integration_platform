package sanchez.sanchez.sergio.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author sergio
 */
@Configuration
public class GoogleAPIConfig {
    
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
       Resource resource = new ClassPathResource("/client_secrets.json");
       return new InputStreamReader(resource.getInputStream(), "UTF-8");
    }
    
    @Bean
    public GoogleClientSecrets provideGoogleClientSecrets() throws IOException{
        return GoogleClientSecrets.load(provideJsonFactory() ,provideInputStreamReader());
    }
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GoogleCredential provideGoogleCredential(String refreshToken) throws IOException {
        return new GoogleCredential.Builder()
                .setJsonFactory(provideJsonFactory())
                .setTransport(provideHttpTransport())
                .setClientSecrets(provideGoogleClientSecrets())
                .build()
                .setRefreshToken(refreshToken);
    }
    
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public YouTube provideYoutubeApi(String refreshToken) throws IOException{
        return new YouTube.Builder(provideHttpTransport(), provideJsonFactory(), provideGoogleCredential(refreshToken)).build();
    }
}
