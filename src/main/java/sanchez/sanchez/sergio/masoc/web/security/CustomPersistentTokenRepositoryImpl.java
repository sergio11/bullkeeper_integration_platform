package sanchez.sanchez.sergio.masoc.web.security;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import sanchez.sanchez.sergio.masoc.persistence.entity.RememberMeTokenEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.RememberMeTokenRepository;

public class CustomPersistentTokenRepositoryImpl implements PersistentTokenRepository {

private static Logger logger = LoggerFactory.getLogger(CustomPersistentTokenRepositoryImpl.class);
    
    private RememberMeTokenRepository rememberMeTokenRepository;

    public CustomPersistentTokenRepositoryImpl(RememberMeTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }
    
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeTokenEntity newToken = new RememberMeTokenEntity();
        newToken.setSeries(token.getSeries());
        newToken.setUsername(token.getUsername());
        newToken.setTokenValue(token.getTokenValue());
        newToken.setDate(token.getDate());
        logger.info("Create new Token ..." + newToken.toString());
        this.rememberMeTokenRepository.save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMeTokenEntity token = rememberMeTokenRepository.findBySeries(series);
        logger.info("update Token ..." + token.toString());
        if (token != null) {
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            this.rememberMeTokenRepository.save(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
    	PersistentRememberMeToken persistenceRememberToken = null;
    	RememberMeTokenEntity token = rememberMeTokenRepository.findBySeries(seriesId);
    	if(token != null) {
    		logger.debug("getTokenForSeries ..." + token.toString());
    		persistenceRememberToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    	}
        return persistenceRememberToken;
    }

    @Override
    public void removeUserTokens(String username) {
        Iterable<RememberMeTokenEntity> tokens = rememberMeTokenRepository.findByUsername(username);
        logger.info("removeUserTokens");
        rememberMeTokenRepository.delete(tokens);
    }

}
