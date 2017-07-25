package sanchez.sanchez.sergio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;
import sanchez.sanchez.sergio.persistence.repository.UserRepository;

/**
 *
 * @author sergio
 */
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
    
    private final UserRepository userRepository;
    
    private static List<UserEntity> usersTest = new ArrayList<>();
    
    static {
    
        usersTest.add(new UserEntity("Sergio", "Martín", 11, Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("EAACEdEose0cBANvWyUYMhpdxsB7LKrKvw3XLrICc86G9XyIWctYPF0Mk4zZBD1WBYiZBBugd7rCz7RDuK0xO8xk3Cp6K91HuzcJbBNRL7Jiw9aibtPkOdtfwHePKffk5DCXDXZCmoEiHIBfIb1U5p8Q4U0tef6CzBHwLumSa56L7DPPwgNYZCRq4YCtH8R8ZD", SocialMediaTypeEnum.FACEBOOK),
            new SocialMediaEntity("sergio_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM),
            new SocialMediaEntity("sergio_access_token_youtube", SocialMediaTypeEnum.YOUTUBE)
        })));
        
        usersTest.add(new UserEntity("Pedro", "Sánchez", 12, Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("pedro_access_token_facebook", SocialMediaTypeEnum.FACEBOOK),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.YOUTUBE)
        })));
        
        usersTest.add(new UserEntity("Maite", "Pérez", 14, Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("maite_access_token_facebook", SocialMediaTypeEnum.FACEBOOK),
            new SocialMediaEntity("maite_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM),
            new SocialMediaEntity("maite_access_token_youtube", SocialMediaTypeEnum.YOUTUBE)
        })));
        
        usersTest.add(new UserEntity("David", "García", 14, Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("david_access_token_facebook", SocialMediaTypeEnum.FACEBOOK),
            new SocialMediaEntity("david_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM),
            new SocialMediaEntity("david_access_token_youtube", SocialMediaTypeEnum.YOUTUBE)
        })));
        
        usersTest.add(new UserEntity("Elena", "Iglesias", 12, Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("elena_access_token_facebook", SocialMediaTypeEnum.FACEBOOK),
            new SocialMediaEntity("elena_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM),
            new SocialMediaEntity("elena_access_token_youtube", SocialMediaTypeEnum.YOUTUBE)
        })));
    
    }

    public CommandLineAppStartupRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void run(String...args) throws Exception {
        Assert.notNull(userRepository, "UserRepository Can not be null");
        logger.info("Load intial Data to Repository");
        userRepository.deleteAll();
        userRepository.save(usersTest);
        logger.info("Data Loaded ...");
    }
}
