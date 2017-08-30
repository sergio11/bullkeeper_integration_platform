package sanchez.sanchez.sergio.service.impl;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.service.ITokenGeneratorService;

/**
 *
 * @author sergio
 */
@Service
public class TokenGeneratorServiceImpl implements ITokenGeneratorService {
    
    protected static SecureRandom random = new SecureRandom();

    @Override
    public synchronized String generateToken(String name) {
        long longToken = Math.abs(random.nextLong());
        String random = Long.toString(longToken, 16);
        return (name + ":" + random);
    }
}
