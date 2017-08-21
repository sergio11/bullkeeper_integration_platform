package sanchez.sanchez.sergio.persistence.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.persistence.entity.AuthorityEntity;
import sanchez.sanchez.sergio.persistence.entity.AuthorityEnum;
import sanchez.sanchez.sergio.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.persistence.repository.AuthorityRepository;
import sanchez.sanchez.sergio.persistence.repository.DeviceGroupRepository;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.persistence.repository.UserSystemRepository;

/**
 *
 * @author sergio
 */

@Component
@Profile("prod")
public class LoadInitialDataForProductionEnvironment implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataForProductionEnvironment.class);
    
    private final AuthorityRepository authorityRepository;
    private final UserSystemRepository userSystemRepository;
    
    
    private static List<AuthorityEntity> authoritiesList = new ArrayList<>();
    private static List<UserSystemEntity> admins = new ArrayList<>();
   
    static {
    	
    	// Authorities
    	AuthorityEntity adminRole = new AuthorityEntity(AuthorityEnum.ROLE_ADMIN, "Role for Administrators");
    	
    	authoritiesList.add(adminRole);
    	
    	AuthorityEntity parentRole = new AuthorityEntity(AuthorityEnum.ROLE_PARENT, "Role for Parents");
    	
    	authoritiesList.add(parentRole);
    	
    	AuthorityEntity anonymousRole = new AuthorityEntity(AuthorityEnum.ROLE_ANONYMOUS, "Role for Anonymous");
    	authoritiesList.add(anonymousRole);
    	
 
    	// ADMIN
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", 30, "admin@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
    	admins.add(admin);
    	
    }

    
    public LoadInitialDataForProductionEnvironment(AuthorityRepository authorityRepository, UserSystemRepository userSystemRepository) {
		super();
		this.authorityRepository = authorityRepository;
		this.userSystemRepository = userSystemRepository;
	}
    
    
    @Override
    public void run(String...args) throws Exception {
        logger.debug("Load Initial Data ...");
        authorityRepository.save(authoritiesList);
        userSystemRepository.save(admins);
        logger.info("Data Loaded ...");
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(authorityRepository, "AuthorityRepository Can not be null");
    	Assert.notNull(userSystemRepository, "UserSystemRepository Can not be null");
    }

	
}
