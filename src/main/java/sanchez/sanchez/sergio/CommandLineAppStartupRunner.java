package sanchez.sanchez.sergio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.persistence.repository.UserRepository;

/**
 *
 * @author sergio
 */

@Component
@Profile("dev")
public class CommandLineAppStartupRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
    
    private final UserRepository userRepository;
    private final SocialMediaRepository socialMediaRepository;
    
    private static List<UserEntity> usersTest = new ArrayList<>();
    private static List<SocialMediaEntity> socialMedias = new ArrayList<>();
    
    static {
        
        UserEntity sergio = new UserEntity("Sergio", "Martín", 11);
        
        usersTest.add(sergio);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("EAACEdEose0cBAP9Jloqys8ZCmFbdmVkTlKgNhwzAEMvmvWNGi8jCLZCoEyRyGthJxG1jz1YZCBWXErTtJMyAH6rMvpFTo39z3tWEgQ0W6ACh0fZAREwNDRZAEl7sIoZAViLNc88dFpoAZCENH2NFqQ9SL5fPKkQZBMqxpjBwTCLafGLrcaKRhVW1Fiya9FZCAyUgZD", SocialMediaTypeEnum.FACEBOOK, sergio),
            new SocialMediaEntity("3303539559.5d2b345.6fb7b3f97e5142fd93973592ccc4c07d", SocialMediaTypeEnum.INSTAGRAM, sergio),
            new SocialMediaEntity("sergio_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, sergio)
        }));
        
        UserEntity pedro = new UserEntity("Pedro", "Sánchez", 12);
        
        usersTest.add(pedro);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("pedro_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.YOUTUBE, pedro)
        }));
        
        UserEntity maite =  new UserEntity("Maite", "Pérez", 14);
        
        usersTest.add(maite);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("maite_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, maite),
            new SocialMediaEntity("maite_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, maite),
            new SocialMediaEntity("maite_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, maite)
        }));
        
        UserEntity david = new UserEntity("David", "García", 14);
        
        usersTest.add(david);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("david_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, david),
            new SocialMediaEntity("david_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, david),
            new SocialMediaEntity("david_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, david)
        }));
        
        UserEntity elena = new UserEntity("Elena", "Iglesias", 12);
        
        usersTest.add(elena);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("elena_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, elena),
            new SocialMediaEntity("elena_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, elena),
            new SocialMediaEntity("elena_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, elena)
        }));
    
    }

    public CommandLineAppStartupRunner(UserRepository userRepository, SocialMediaRepository socialMediaRepository) {
        this.userRepository = userRepository;
        this.socialMediaRepository = socialMediaRepository;
    }
    
    @Override
    public void run(String...args) throws Exception {
        Assert.notNull(userRepository, "UserRepository Can not be null");
        logger.info("Load intial Data to Repository");
        socialMediaRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.save(usersTest);
        socialMediaRepository.save(socialMedias);
        logger.info("Data Loaded ...");
    }
}
