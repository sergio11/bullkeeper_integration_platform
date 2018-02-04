package es.bisite.usal.bulltect.persistence.loader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.entity.AuthorityEntity;
import es.bisite.usal.bulltect.persistence.entity.AuthorityEnum;
import es.bisite.usal.bulltect.persistence.entity.UserSystemEntity;
import es.bisite.usal.bulltect.persistence.repository.AuthorityRepository;
import es.bisite.usal.bulltect.persistence.repository.UserSystemRepository;

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
    	Calendar adminBirthdate = Calendar.getInstance();
    	adminBirthdate.set(1982, 4, 23);
    	
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", adminBirthdate.getTime(), "admin@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
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
        
        final Long authorities = authorityRepository.count();
        if(authorities == 0 || authorities < authoritiesList.size()){
        	authorityRepository.deleteAll();
        	authorityRepository.save(authoritiesList);
        }
        //userSystemRepository.save(admins);
        logger.info("Data Loaded ...");
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(authorityRepository, "AuthorityRepository Can not be null");
    	Assert.notNull(userSystemRepository, "UserSystemRepository Can not be null");
    }

	
}
