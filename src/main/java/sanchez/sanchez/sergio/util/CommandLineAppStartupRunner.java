package sanchez.sanchez.sergio.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.persistence.entity.AuthorityEntity;
import sanchez.sanchez.sergio.persistence.entity.AuthorityEnum;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.persistence.repository.AuthorityRepository;
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
@Profile("dev")
public class CommandLineAppStartupRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
    
    
    private final SchoolRepository schoolRepository;
    private final ParentRepository parentRepository;
    private final SonRepository sonRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final AuthorityRepository authorityRepository;
    private final UserSystemRepository userSystemRepository;
    private final AlertRepository alertRepository;
    
    
    private static List<AuthorityEntity> authoritiesList = new ArrayList<>();
    private static List<SchoolEntity> schoolList = new ArrayList<>();
    private static List<ParentEntity> parentList = new ArrayList<>();
    private static List<SonEntity> childrenList = new ArrayList<>();
    private static List<SocialMediaEntity> socialMedias = new ArrayList<>();
    private static List<UserSystemEntity> admins = new ArrayList<>();
    private static List<AlertEntity> alertList = new ArrayList<>();
   
    static {
    	
    	// Authorities
    	AuthorityEntity adminRole = new AuthorityEntity(AuthorityEnum.ROLE_ADMIN, "Role for Administrators");
    	
    	authoritiesList.add(adminRole);
    	
    	AuthorityEntity parentRole = new AuthorityEntity(AuthorityEnum.ROLE_PARENT, "Role for Parents");
    	
    	authoritiesList.add(parentRole);
    	
    	AuthorityEntity anonymousRole = new AuthorityEntity(AuthorityEnum.ROLE_ANONYMOUS, "Role for Anonymous");
    	
    	authoritiesList.add(anonymousRole);
    	
    	
    	// SCHOOL
    	SchoolEntity school1 = new SchoolEntity("C.E.I.P. Fernando Gavilán", "Avda. Herrera Oria, s/n", "Ubrique", "Cádiz", 956128801, "11008033.edu@juntadeandalucia.es");
    	
    	schoolList.add(school1);
    	
    	SchoolEntity school2 = new SchoolEntity("C.E.I.P. San Juan de Ávila", "C/ Erillas, s/n", "Iznalloz", "Granada", 958399591, "18005475.edu@juntadeandalucia.es");
    	
    	schoolList.add(school2);
    	
    	
    	// PARENTS
    	
    	ParentEntity federico = new ParentEntity("Federico", "Martín", 36, "federico@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(federico);
    	
    	ParentEntity fernando = new ParentEntity("Fernando", "Muñoz", 40, "fernando@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(fernando);
    	
    	ParentEntity jaime = new ParentEntity("Jaime", "Gómez", 29, "jaime@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(jaime);
    	
    	// ADMIN
    	
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", 30, "admin@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
    	
    	admins.add(admin);
    	
    	// CHILDREN
    	
    	SonEntity sergio = new SonEntity("Sergio", "Martín", 11, school1, federico);
  
    	childrenList.add(sergio);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("EAACEdEose0cBAJtM1K82PZC7dWx4X1pnh0SMeCkmfrik8awudYktG6eg8h7OVZC3VeAQbHZCh6ULfwG9ZBLVHBsuT7df2cQCjIvZA1sIN23HtZAh6pDZBnSET8zs3HjndoVsBZCZBgmcCJr8l8p6ygyjuz8TQTEwvgVrFGZBU8u7OgZClriwJmEmX1MQiZBLhJlZB69gZD", SocialMediaTypeEnum.FACEBOOK, sergio),
            new SocialMediaEntity("3303539559.5d2b345.6fb7b3f97e5142fd93973592ccc4c07d", SocialMediaTypeEnum.INSTAGRAM, sergio),
            new SocialMediaEntity("ya29.GmaiBCC-Gpm_NO9Z-Au4imXzsb9gFjBNYoO2QMEtfJlzYksM93pSZoWsr1yxFvRvI8wuNrLimC4KRf364TcE-ZGr3uyVdery8sQtw3ZKOUxyF1bPicG7lAVBmL113Ji3sK3hrnEpa0Y", SocialMediaTypeEnum.YOUTUBE, sergio)
        }));
        
        SonEntity pedro = new SonEntity("Pedro", "Sánchez", 12, school1, federico);
        
        childrenList.add(pedro);
          
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("pedro_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.YOUTUBE, pedro)
        }));
        
        SonEntity maite =  new SonEntity("Maite", "Pérez", 14, school1, federico);
        
        childrenList.add(maite);

        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("maite_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, maite),
            new SocialMediaEntity("maite_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, maite),
            new SocialMediaEntity("maite_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, maite)
        }));
        
        SonEntity david =  new SonEntity("David", "García", 14, school2, fernando);
       
        childrenList.add(david);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("david_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, david),
            new SocialMediaEntity("david_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, david),
            new SocialMediaEntity("david_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, david)
        }));
        
        SonEntity elena = new SonEntity("Elena", "Iglesias", 12, school2, fernando);
        
        childrenList.add(elena);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("elena_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, elena),
            new SocialMediaEntity("elena_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, elena),
            new SocialMediaEntity("elena_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, elena)
        }));
        
        // Alerts
        
        AlertEntity alertInfo = new AlertEntity("Alerta de Prueba", sergio);
        
        alertList.add(alertInfo);
    
    }


    
    public CommandLineAppStartupRunner(SchoolRepository schoolRepository, ParentRepository parentRepository,
			SonRepository sonRepository, SocialMediaRepository socialMediaRepository, AuthorityRepository authorityRepository, 
			UserSystemRepository userSystemRepository, AlertRepository alertRepository) {
		super();
		this.schoolRepository = schoolRepository;
		this.parentRepository = parentRepository;
		this.sonRepository = sonRepository;
		this.socialMediaRepository = socialMediaRepository;
		this.authorityRepository = authorityRepository;
		this.userSystemRepository = userSystemRepository;
		this.alertRepository = alertRepository;
	}
    
    
    @Override
    public void run(String...args) throws Exception {
        logger.debug("Delete all data");
        sonRepository.deleteAll();
        parentRepository.deleteAll();
        schoolRepository.deleteAll();
        socialMediaRepository.deleteAll();
        authorityRepository.deleteAll();
        userSystemRepository.deleteAll();
        alertRepository.deleteAll();
        logger.debug("Load Initial Data ...");
        authorityRepository.save(authoritiesList);
        schoolRepository.save(schoolList);
        parentRepository.save(parentList);
        sonRepository.save(childrenList);
        socialMediaRepository.save(socialMedias);
        userSystemRepository.save(admins);
        alertRepository.save(alertList);
        logger.info("Data Loaded ...");
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(schoolRepository, "SchoolRepository Can not be null");
    	Assert.notNull(parentRepository, "ParentRepository Can not be null");
    	Assert.notNull(sonRepository, "SonRepository Can not be null");
    	Assert.notNull(socialMediaRepository, "SocialMediaRepository Can not be null");
    	Assert.notNull(authorityRepository, "AuthorityRepository Can not be null");
    	Assert.notNull(userSystemRepository, "UserSystemRepository Can not be null");
    	Assert.notNull(alertRepository, "alertRepository Can not be null");
    }

	
}
